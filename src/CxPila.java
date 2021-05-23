/*
        CxPila.java: Alberto Pacheco. alberto@acm.org
        [13] 00-04-20: Pila
*/

import Pila;     // is-a
import Complejo; // uses

public class CxPila extends Pila {

 public void cx_push(double r, double i) { info.push(new Complejo(r,i)); }

 public void cx_push(Complejo x) { info.push(x); }

 public Complejo cx_pop() { return (Complejo)info.pop(); }

 public Complejo cx_peek() { return (Complejo)info.peek(); }

 public static void main(String [] args) {

	CxPila pila = new CxPila();

	Complejo x = new Complejo(5,16);
	Complejo y = new Complejo(15,45,true);

	pila.cx_push(x);
	pila.cx_push(y);
	pila.cx_push(Complejo.suma(x,y));

        while ( pila.notEmpty() ) {
		System.out.println("\ndato="+pila.cx_pop());
	}
 }

}

