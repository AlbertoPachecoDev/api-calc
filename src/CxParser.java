/*
	CxParser.java
*/

import Parser; // is-a

public class CxParser extends Parser {

 private final String delim="0123456789.,{}<>";

 CxParser( char[] o_p ) { super(o_p); }

 public boolean esOperando(char c) {
	 return delim.indexOf(c) >= 0;
 }

} //CxParser
