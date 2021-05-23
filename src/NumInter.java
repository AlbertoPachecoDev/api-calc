/*
        NumInter.java: Evalua expresiones numericas en notacion posfija
	Alberto Pacheco. apacheco@itch.edu.mx. Dic'98, Abr'99

Bitacora:
[9] 98-12-17: Version Inicial
[10]99-01-11: Bug: Division entre cero. Stack con basura -> error.
[11]99-04-25: Documentacion y metodo main()

Algoritmo:

0. 

*/

import Interprete;          // is-a
import java.util.Hashtable; // has-a
import CharFila;            // uses
import NumPila;             // uses
import Expresion;           // uses

public final class NumInter extends Interprete {

	Hashtable numeros; // <var,valor> ::= <Character-Letter,Float>

 public void setNum( Hashtable nums ) { numeros=nums; }

 public Object traducir( Expresion expr ) throws Exception {

   super.traducir(expr); // Valida en clase Interprete que la expresión exista y no este vacía

   String str_expr=expr.toString(); //[11]

   if ( null == numeros ) { //[11]
      // p("Bug: Llamar setNum() antes de traducir()");
      // [11] Debugging: usando operandos de un dígito
      numeros=new Hashtable();
      for ( int i=0; i<str_expr.length(); i++ ) {
	char c=str_expr.charAt(i);
	if ( Character.isDigit(c) )
		numeros.put(new Character(c),new Float(String.valueOf(c)));
      }
   }

   CharFila fuente=new CharFila(str_expr); // Expresión-posfija-entrada
   NumPila  pila=new NumPila(); // Pila Auxiliar de Operandos

   while ( fuente.notEmpty() ) {
      float op1, op2;
      char  c=fuente.char_next();
      switch ( c ) {
         case '+':
            op2=pila.num_pop();
            op1=pila.num_pop();
            pila.num_push(op1+op2);
            break;
         case '-':
            op2=pila.num_pop();
            op1=pila.num_pop();
            pila.num_push(op1-op2);
            break;
         case '*':
            op2=pila.num_pop();
            op1=pila.num_pop();
            pila.num_push(op1*op2);
            break;
         case '/':
            op2=pila.num_pop();
            if ( 0 == op2 ) // [9]
		throw new Exception("División entre cero");
            op1=pila.num_pop();
            pila.num_push(op1/op2);
            break;

         /* AGREGAR NUEVOS OPERADORES AQUI */

         default:
            if ( !Character.isLetter(c) )
               throw new Exception("Símbolo "+c+" inválido");
            Character ch=new Character(c);
            if ( false == numeros.containsKey(ch) )
               throw new Exception("Bug:InterNum.traducir no encontró "+c);
            pila.num_push( (Float)numeros.get(ch) );
      } // switch
   } // while

   Float r=new Float(pila.num_pop());
   if ( pila.notEmpty() ) // [9]
      throw new Exception("Expresión incorrecta");
   return r;

 } // traducir

public static void main( String [] args ) { // [11]

// LIMITACION: OPERANDOS NUMERICOS DE UN SOLO DIGITO!!

 	char[] o_p={ // Operadores/Precedencia
		'*', (char)4, // multiplicacion
		'/', (char)4, // division
		'+', (char)8, // suma
		'-', (char)8  // resta
		};
	NumParser parser=new NumParser(o_p);
	NumInter  interp=new NumInter();
        Console cio=new Console();
	while ( true ) try {
			Expresion expr_subfija= new Expresion("1+2*3");

                        // 1. Analizador de Lexico
			parser.analiza(expr_subfija);

                        // 2. Parser: Expr en notacion posfija
			Expresion expr_posfija=parser.traducir(expr_subfija);

                        cio.p("Entrada: Expresion subfija -> "+expr_subfija);
                        cio.p(" Salida: Expresion posfija -> "+expr_posfija);

			// 3. Evalua expr p/c/valor posible
			Object resultado=interp.traducir(expr_posfija);
                        cio.p("Resultado = "+resultado);
	} catch ( Exception e ) { e.printStackTrace(); }
 } // main

} // NumInter

