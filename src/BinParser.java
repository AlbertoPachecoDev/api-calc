/*
	BinParser.java: Parser p/Calculadora Binaria
	Alberto Pacheco. apacheco@itch.edu.mx. Nov'99
*/

import Parser; // is-a

public class BinParser extends Parser {

 public BinParser( char [] o_p ) { super(o_p); }

 public boolean esOperando(char c) { return (c=='0' || c=='1'); }

} // BinParser