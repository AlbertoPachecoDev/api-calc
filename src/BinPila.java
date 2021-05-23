/*
	BinPila.java
        Alberto Pacheco, apacheco@itch.edu.mx, Dic'99
[12] 99-11-18: Maneja enteros
[13] 00-04-20: Pila
*/

public class BinPila extends Pila {

 public void num_push( int n ) { info.push(new Integer(n)); }

 public void num_push( Integer n ) { info.push(n); }

 public int  num_pop() { return ((Integer)info.pop()).intValue(); }

 public int  num_peek() { return ((Integer)info.peek()).intValue(); }

} // BinPila
