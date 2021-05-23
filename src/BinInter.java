/*
        BinInter.java: Evalua expresiones binarias en notacion posfija
        Alberto Pacheco. alberto@acm.org. Nov'99

Bitacora:
[12] 99-11-18: Version Inicial
[13] 00-04-20: Retoques: Console, notEmpty

Pdte:

Recortar num. digitos operando mayor

HexCalc

*/

import Interprete;          // is-a
import java.util.Hashtable; // has-a
import CharFila;            // uses
import BinPila;             // uses
import Expresion;           // uses

public final class BinInter extends Interprete {

	Hashtable numeros; // <var,valor> ::= <Character-Letter,Integer>

 public void setNum( Hashtable nums ) { numeros=nums; }

 public Object traducir( Expresion expr ) throws Exception {

   super.traducir(expr); // Valida en Interprete que la expresión exista y no este vacía

   String str_expr=expr.toString(); //[11]

   if ( null == numeros ) { //[11]
      // p("Bug: Llamar setNum() antes de traducir()");
      // [11] Debugging: usando operandos de un digito
      numeros=new Hashtable();
      for ( int i=0; i<str_expr.length(); i++ ) {
	char c=str_expr.charAt(i);
	if ( Character.isDigit(c) )
		numeros.put(new Character(c),new Integer(String.valueOf(c)));
      }
   }

   CharFila fuente=new CharFila(str_expr); // Expresion-posfija-entrada
   BinPila  pila=new BinPila(); // Pila Auxiliar de Operandos

   while ( fuente.notEmpty() ) {
      int  op1, op2;
      char c=fuente.char_next();
      switch ( c ) {
         case '+':
            op2=pila.num_pop();
            op1=pila.num_pop();
            pila.num_push(op1+op2);
            continue;
         case '-':
            op2=pila.num_pop();
            op1=pila.num_pop();
            pila.num_push(op1-op2);
            continue;
         case '*':
            op2=pila.num_pop();
            op1=pila.num_pop();
            pila.num_push(op1*op2);
            break;
         case '/':
            op2=pila.num_pop();
            if ( 0 == op2 ) // [9]
                throw new Exception("Division entre cero");
            op1=pila.num_pop();
            pila.num_push(op1/op2);
            break;
         case '&':
            op2=pila.num_pop();
            op1=pila.num_pop();
            pila.num_push(op1 & op2);
            break;
         case '|':
            op2=pila.num_pop();
            op1=pila.num_pop();
            pila.num_push(op1 | op2);
            break;

         /* AGREGAR NUEVOS OPERADORES AQUI */

         default:
            if ( !Character.isLetter(c) )
               throw new Exception("Simbolo "+c+" invalido");
            Character ch=new Character(c);
            if ( false == numeros.containsKey(ch) )
               throw new Exception("Bug:InterNum.traducir no encontro "+c);
            pila.num_push( (Integer)numeros.get(ch) );
      } // switch
   } // while

   Integer r=new Integer(pila.num_pop());
   if ( pila.notEmpty() ) // [9]
      throw new Exception("Expresion incorrecta");
   return r;

 } // traducir

} // BinInter
