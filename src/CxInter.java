/*
	CxInter.java
	Alberto Pacheco. apacheco@itch.edu.mx. Mzo'99

Bitacora:
99-03-16: Version Inicial
99-05-15: CxPila->Stack
*/

import Interprete;		// is-a
import java.util.Hashtable;	// has-a
import CharFila;		// uses
import java.util.Stack;         // uses  (does not require CxPila)
import Complejo;		// uses


public final class CxInter extends Interprete {

	Hashtable complejos; // par ordenado <variable, numero_complejo>

public void setCx(Hashtable comps) { complejos=comps; }

public Object traducir(Expresion expr) throws Exception {

//	p(expr); // ??
	super.traducir(expr);
	if ( null == complejos )
		throw new Exception("Bug: Llamar setCx() antes de traducir()");

	CharFila fuente=new CharFila(expr.toString());
//	CxPila pila=new CxPila();
	Stack pila=new Stack();

        while ( fuente.notEmpty() ) {

		Complejo op1, op2;
		char c=fuente.char_next();

		switch (c) {

		case '+':
			op2=(Complejo)pila.pop();
			op1=(Complejo)pila.pop();
			pila.push(Complejo.suma(op1,op2));
			break;

		case '-':
			op2=(Complejo)pila.pop();
			op1=(Complejo)pila.pop();
			pila.push(Complejo.resta(op1,op2));
			break;

		case '*':
			op2=(Complejo)pila.pop();
			op1=(Complejo)pila.pop();
			pila.push(Complejo.producto(op1,op2));
			break;

		case '/':
			op2=(Complejo)pila.pop();
			op1=(Complejo)pila.pop();
			pila.push(Complejo.cociente(op1,op2));
			break;

		default:
			if( !Character.isLetter(c) )
                                throw new Exception("Simbolo "+c+" invalido");

			Character ch=new Character(c); // Variable

			if( false == complejos.containsKey(ch) )
				throw new Exception("Bug: CxInter.traducir no encontró "+c);

			pila.push( (Complejo)complejos.get(ch) );

		} //switch
	} //while

	return (Complejo)pila.pop();

  } // traducir

} // CxInter

