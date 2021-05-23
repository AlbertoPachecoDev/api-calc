/*
  BoolPila.java. Alberto Pacheco. apacheco@itch.edu.mx.
  Oct'98, Abr'00
*/

import CharPila; // is-a

public final class BoolPila extends CharPila {

 public boolean bool_pop() throws Exception {
        char c=super.char_pop();
	if ( '0' == c )
		return false;
	if ( '1' == c )
		return true;
        throw new Exception("Operando invalido, debe ser 0 o 1");
 }

 public void bool_push( boolean b ) {
        super.char_push( b? '1' : '0' );
 }

 public boolean bool_peek() {
        return ((Boolean)info.peek()).booleanValue();
 }

 public static void main( String[] args ) {
	BoolPila p = new BoolPila();
	p.char_push('x');
	p.char_push('1');
	p.char_push('0');
	p.bool_push(true);
	p.bool_push(false);
        while ( p.notEmpty() )
		try {
			System.out.println("p: "+p.bool_pop());
		} catch (Exception e) { e.printStackTrace(); }
 } // main

} // BoolPila
