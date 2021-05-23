/*
  CharFila.java. Alberto Pacheco. apacheco@itch.edu.mx. Oct'98, Abr'00

  [13] 00-04-20: Composicion
*/

import java.util.Vector; // has-a

public class CharFila {

 private Vector info=new Vector();

 private char at( int i ) { // No valida indice por eficiencia
        return ((Character)info.elementAt(i)).charValue();
 }

 private int size() {
        return info.size();
 }

 public CharFila() { super(); }

 public CharFila( String s ) {
        super();
	for ( int i=0; i < s.length(); i++ )
		char_add( s.charAt(i) );
 }

 public boolean equals( CharFila q ) {
        int sz = this.size();
	if ( sz != q.size() )
		return false;
	for ( int i=0; i<sz; i++ )
                if ( this.at(i) != q.at(i) )
			return false;
	return true;
 }

 public void char_add( char c ) {
        info.insertElementAt( new Character(c), info.size() );
 }

 public Character next() {
        if ( info.isEmpty() )
		return null;
        Character obj=(Character)info.firstElement();
        info.removeElementAt(0);
	return obj;
 }

 public char char_next() { return ((Character)this.next()).charValue(); }

 public char char_peek() {
        return ((Character)info.firstElement()).charValue();
 }

 public String toString() { //[13]
        char[] s=new char[info.size()];
	for ( int i=0; i < s.length; i++ )
                s[i]=this.at(i);
	return new String(s);
 }

 public boolean isEmpty() { return info.isEmpty(); } //[13]

 public boolean notEmpty() { return !info.isEmpty(); } //[13]

 public static void main( String[] args ) {

	CharFila q1=new CharFila();
	CharFila q2=new CharFila("abc");

	q1.char_add('a');
	q1.char_add('b');
	q1.char_add('c');

	if ( q1.equals(q2) )
		System.out.println(q1+" == "+q2);

	while ( q1.notEmpty() )
		System.out.println("q1: "+q1.char_next());

	while ( q2.notEmpty() )
		System.out.println("q2: "+q2.char_next());

 }

} // CharFila

