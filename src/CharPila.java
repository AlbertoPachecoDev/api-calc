// CharPila.java. Alberto Pacheco. apacheco@itch.edu.mx. Oct'98, Abr'00

public class CharPila extends Pila {

 public void char_push( char c ) { info.push(new Character(c)); }

 public char char_pop() { return ((Character)info.pop()).charValue(); }

 public char char_peek() { return ((Character)info.peek()).charValue(); }

 public static void main( String[] args ) {

	CharPila p = new CharPila();

	p.char_push('a');
	p.char_push('b');
	p.char_push('c');

	while ( p.notEmpty() )
		System.out.println("p: "+p.char_pop());
 }

} // CharPila

