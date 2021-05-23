/*
	LogiInter.java
	Evalua expresiones lógicas en notación posfija
	Alberto Pacheco (apacheco@itch.edu.mx).	Oct-Dic'98

Basado en:
	* calc.hpp/cpp, C++, apacheco, Jul'94.
	* "Invitation to Forth", H. Katzan Jr., PBI, 1981.

Bitácora:
[1] 98-10-03: Versión Inicial. Migración C++. Herencia Parser->InterLogic
[2] 98-10-05: InterLogic is-NOT-a Parser. OperPrior,BoolPila,CteExpr. 'V'->'|'
[3] 98-10-06: Exception p/Operadores-Inválidos
[4] 98-10-06: Bug:!!P ->java.util.EmptyStackException
[8] 98-12-16: Superclase Interprete
[9] 98-12-28: Error para operadores unarios en exceso e.g. P!&Q

Optimizaciones:
	* CharPila -> arrays

Limitaciones:
	* token=una-letra, donde token=operador|operando
	* La asociatividad es siempre de izquierda a derecha
	* Prioridad operadores 1..99
	* No verifica léxico, trata como operandos los operadores inválidos
	* No verifica minúsculas/mayúscula en operandos
	* No verifica duplicados en def. operadores/precedencia

Algoritmo:
	1. DETECTA Y VALIDA VARIABLES Y EXPRESION
	2. SUSTITUYE VARIABLES POR VALOR LOGICO (0/1)
	3. CONVIERTE EXPR. INFIJA A POSFIJA
	4. EVALUA EXPRESION POSFIJA (USANDO PILA DE OPERANDOS)
		a) RECORRE CADENA CON EXPRESION DE IZQ -> DER
		b) SI OPERANDO -> PILA
		c) SI OPERADOR:
			PILA -> OPERANDOS NECESARIOS
			OPERACION -> RESULTADO
			RESULTADO -> PILA
		d) SI EXPRESION NO TERMINA -> (a)

EJEMPLO:
	Prioridad: <'*',1>, <'-',2>
	"A*(X-B)"  -->   "AXB-*"

	Prioridad: <'!',1>, <'&',2>, <'|',3>, <'+',3>, <'>',4>, <'=',4>
	"(p&(p>q))>q" --> "ppq>&q>"
	"!p|p&r>r=r"  --> "p!pr&|r>r="
	"p|q&!r"      --> "pqr!&|"
	"p&(q|r)"     --> "pqr|&"
*/

//exported to LogiTrad

import Interprete; // is-a
import CharFila;   // uses
import BoolPila;   // uses
import Expresion;  // uses

public final class LogiInter extends Interprete {

 public Object traducir( Expresion expr ) throws Exception {

   super.traducir(expr); // Validación

   CharFila fuente=new CharFila(expr.toString()); // Expresión-posfija-entrada
   BoolPila pila=new BoolPila(); // Pila Auxiliar de Operandos

   while ( false == fuente.isEmpty() ) {
      boolean op1, op2;
      char    c=fuente.char_next();
      switch ( c ) {
         case '!': // not
            pila.bool_push(!pila.bool_pop()); break;
         case '&': // and
            op2=pila.bool_pop();
            op1=pila.bool_pop();
            pila.bool_push(op1&&op2);
            break;
         case '|': // or
            op2=pila.bool_pop();
            op1=pila.bool_pop();
            pila.bool_push(op1||op2);
            break;
         case '+': // xor
            op2=pila.bool_pop();
            op1=pila.bool_pop();
            pila.bool_push(op1^op2);
            break;
         case '>': // if
            op2=pila.bool_pop();
            op1=pila.bool_pop();
            pila.bool_push(!op1||op2);
            break;
         case '=': // equ
            op2=pila.bool_pop();
            op1=pila.bool_pop();
            pila.bool_push(!(op1^op2));
            break;

         /* AGREGAR NUEVOS OPERADORES AQUI */

         case '0': case '1': // false/true
            pila.char_push(c);
            break;

         default: // error
            if ( Character.isLetterOrDigit(c) ) // [3]
               throw new Exception("Operando "+c+" inválido, debe ser 0 ó 1");
            else
               throw new Exception("Operador "+c+" inválido");
            }
   }
   Boolean r=new Boolean(pila.bool_pop()); // resultado
   if ( false == pila.empty() ) //[9] error si la pila no esta vacía!!
      throw new Exception("Sobran/Faltan elementos");
   return r;
 } // traducir

} // LogiInter