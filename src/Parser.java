/*
	Parser.java
        Convierte expresiones en notacion infija a posfija
	Alberto Pacheco. apacheco@itch.edu.mx. Oct-Dic'98

Basado en:
	* posfija.hpp/cpp, C++, apacheco, Jul'94.
        * "Invitation to Forth", H. Katzan Jr, PBI, 1981.

Bitácora:
 98-10-01: Migracion C++. Bug: Cambio precedencia '('(mayor)->'['(menor)
 98-10-05: Class operPrior. Composicion vs. Herencia para la Integracion
 98-10-07: Bug: !!P->!P!->java.util.EmptyStackException.
 98-10-10: Bug: Ignora espacios
 98-12-17: Clase Expresion [8]. Metodo analizar()
 98-12-26: abstract Parser. esOper../esSimbolo. analiza. LogiParser.
 99-11-15: Correcciones al pseudo-codigo. Elimina interfaz CteExpr.
	   Java 1.2: Character.isSpace->isSpaceChar
 00-04-05: +Legible: isSpace(), priorMayor(), toString(), notEmpty()

Optimizaciones:
	* CharQueue, CharPila -> arrays
	* Hastable  -> asoc. array (Par)
	* Hashtable private:<byte,byte>, public:<char,byte>
	* Exceptions: ExpresionIncorrecta, FaltanParentesis, FaltaOperador..

Limitaciones:
	* token=una-letra, donde token=operador|operando
	* La asociatividad es siempre de izquierda a derecha
	* Prioridad operadores 1..99
        * No verifica lexico, trata como operandos los operadores invalidos
        * No verifica minusculas/mayuscula en operandos
        * No verifica duplicados en la definicion tabla operadores/precedencia

Pendientes:
        * Analizador Lexico basado en AFD, Expr. Reg. o BNF

Algoritmo:

	 "A*(B+C)"                       "ABC+*"
            ||                              /\
            \/                              ||
        -----------                      ----------
          )C+B(*A   ___________________\   *+CBA   
                                       /           
        -----------        || /\         ----------
         Expresion         \/ ||         Expresion
          Infija          |     |         Posfija
       (fila-fuente)      |  +  |      (fila-destino)
                          |  [  |
                          |  *  |
                           -----
                     Pila de Operadores
                        (pila-oper)

    0. Crear fila-fuente = cadena-entrada
       Crear fila-destino vacia.
       Crear pila-oper vacia.
       Nota: fila-fuente y fila-destino
             reciben  datos lado izquierdo
             entregan datos lado derecho.

    1. Si fila-fuente esta vacia, ir a 5.
       Si no, token = sig. caracter de fila-fuente.

    2. Si token = ')', ir a 2.1, si no ir a 3.
       2.1 Si pila-oper esta vacia, ir a 1
       2.2 operador = pila-oper.pop
       2.3 Si operador = '[', ir a 1.
       2.4 fila-destino.add(operador). Ir a 2.1.
       Nota: Los paréntesis no deben aparecer en fila-destino.

    3. Si token es operador, ir a 3.1, si no ir a 4.
           3.1 Si pila-oper esta vacia, ir a 3.4.
	   3.2 Si prioridad(pila-oper.peek) > prioridad(token), ir a 3.4.
	   3.3 fila-destino.add(pila-oper.pop), ir a 3.1.
	   3.4 Si token = '(', pila-oper.push('['). Ir a 1.
	   3.5 pila-oper.push(token). Ir a 1.
	   Nota: '(' se convierte a '[' (mayor->menor prioridad)

    4. fila-destino.add(token). Ir a 1. (Nota: token es un operando).

    5. Mover todos los operadores de pila-oper a fila-destino.
       Regresar resultado convirtiendo fila-destino a cadena.

NOTA: Sinonimos pseudo-codigo -> codigo Java
        fila-fuente  -->  fuente 
        fila-destino -->  destino
        pila-oper    -->  pila

CORRIDA-PRUEBA: Para verificar algoritmo corra: "java NumParser"

EJEMPLO:
	Prioridad: <'*',1>, <'+',2>
        "A+B*C"    -->   "ABC*+"
        "(A+B)*C"  -->   "AB+C*"
	"A*(B+C)"  -->   "ABC+*"

	Prioridad: <'!',1>, <'&',2>, <'V',3>, <'+',3>, <'>',4>, <'=',4>
	"(p&(p>q))>q" --> "ppq>&q>"
	"!pVp&r>r=r"  --> "p!pr&Vr>r="
	"pVq&!r"      --> "pqr!&V"
	"p&(qVr)"     --> "pqrV&"

*/

import OperPrior; // has-a
import CharFila;  // uses
import CharPila;  // uses
import Expresion; // uses
import Console;   // cio


public abstract class Parser {

 // -- ATRIBUTOS -- \\

 protected OperPrior operPrior;	// Operador/Precedencia
 private   String    ops;       //[6b,8] Lista de operadores

 // -- UTILERIAS -- \\

private void setOper( char [] o_p ) { //[9]
	ops = "()"; //[6b,8,9] Bug: Faltan paréntesis
	for ( int i=0; i < o_p.length; i+=2 ) //[6b]
		ops += o_p[i];
 }

static protected boolean isSpace(char c ) { //[6c,13]
	return Character.isSpaceChar(c);    // JDK1.0: isSpace
 }

protected boolean priorMayor( char x, char y ) { //[13]
	return operPrior.esMayor(x,y);
 }


 // -- INTERFAZ -- \\

Parser( char [] o_p ) { //[8,9]
	operPrior=new OperPrior(o_p); // Tabla de prioridad-operadores
	setOper(o_p); // Inicializa lista de operadores
 }

public String operadores() { return ops; } //[9]

public boolean esOperador(char c) { return ops.indexOf(c)>=0; } //[9]

public abstract boolean esOperando(char c); //[9] DEFINE SUBCLASE!!

public boolean esSimbolo(char c) {
	return esOperando(c) || esOperador(c); // [9]
}

public void analiza( Expresion expresion ) throws Exception { //[6b,8,9]
   // Analizador Lexicografico: No soporta Expr. Regulares
   String expr=expresion.toString();
   int np=0; // Numero de parejas de parentesis
   for ( int i=0; i < expr.length(); i++ ) {
      char c=expr.charAt(i);
      switch (c) {
         case '(':
            ++np; break;
         case ')':
            --np; break;
         default:
            if ( !esSimbolo(c) )
               throw new Exception("Simbolo invalido: "+c);
      }
   }
   if ( np != 0 ) // Parentesis=(Abren-Cierran)=0
      throw new Exception("Error de parentesis");
 }

 public Expresion traducir( Expresion expr ) throws Exception {

   String   entrada = expr.toString(); //[8]   // (0) Expresion de entrada
   CharFila fuente  = new CharFila(entrada);   // (0) Entrada: expr-infija
   CharFila destino = new CharFila();          // (0) Salida:  expr-subfija
   CharPila pila    = new CharPila();          // (0) Pila-de-Operadores

   while ( fuente.notEmpty() ) {                // (1)   WHILE-1
      char token=fuente.char_next();            // (1)   .
      if ( isSpace(token) )                     // (1)   .{Ignora Espacios}
         continue;
      if ( token == ')' ) {                     // (2)   .IF-1
         while ( pila.notEmpty() ) {            // (2.1) ..WHILE-2
            char operador=pila.char_pop();      // (2.2) ...
            if ( operador == '[' )              // (2.3)*...
               break;                           // (2.3) ...EXIT-WHILE-2
            destino.char_add( operador );       // (2.4) ...
         } // END-WHILE-2                       // (2.4) ..END-WHILE-2
      } // END-IF-1                             // (2.4) .END-IF-1
      else if ( operPrior.esOperador(token) ) { // (3)   .ELSE-1 = IF-2
         while ( pila.notEmpty() ) {            // (3.1) ..WHILE-3
            if(priorMayor(pila.char_peek(),token))// (3.2) ...
               break;                           // (3.2) ...EXIT-WHILE-3
            destino.char_add(pila.char_pop());  // (3.3) ...
         } // END-WHILE-3                       // (3.3) ..END-WHILE-3
         if ( token == '(' ) {                  // (3.4) ..IF-3
            pila.char_push( '[' );              // (3.4) ...
         } else {                               // (3.5) ..ELSE-3
            pila.char_push( token );            // (3.5) ...
         }                                      // (3.5) ..END-IF-3
      } else {                                  // (4)   .ELSE-2
         destino.char_add( token );             // (4)   ..
      } // END-IF-1-2                           // (4)   .END-IF-1-2
   } // FIN-WHILE-1                             // (4)   FIN-WHILE-1
   while ( pila.notEmpty() ) {                  // (5)   WHILE-4
      if ( pila.char_peek() == '[' )            // (5)*  .VERIF
         throw new Exception("Falta cerrar un parentesis");
      destino.char_add( pila.char_pop() );      // (5)   .
   } // END-WHILE-4                             // (5)   END-WHILE-4
   return new Expresion(destino.toString()); //[8] (5) RETURN
 }

} // Parser
