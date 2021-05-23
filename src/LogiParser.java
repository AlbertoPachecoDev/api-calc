/*
	LogiParser.java: Parser p/Calculadora Lógica
	Alberto Pacheco. apacheco@itch.edu.mx. Dic'98
*/

import Parser; // is-a

public class LogiParser extends Parser {

LogiParser( char [] o_p ) { super(o_p); }

public boolean esOperando(char c) {
	return (Character.isLetter(c) || c=='0' || c=='1');
 }

} // LogiParser