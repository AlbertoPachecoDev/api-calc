/*
	NumPila.java
        Alberto Pacheco, apacheco@itch.edu.mx, Dic'98
[01] 98-10-10: Version Inicial
[13] 00-04-20: Pila
*/

public class NumPila extends Pila {

 public void num_push( float n ) { info.push(new Float(n)); }

 public void num_push( Float n ) { info.push(n); }

 public float num_pop() { return ((Float)info.pop()).floatValue(); }

 public float num_peek() { return ((Float)info.peek()).floatValue(); }

} // NumPila

