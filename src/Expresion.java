/*
	Expresion.java: Clase "Expresion" para la Calculadora
	apacheco@itch.edu.mx. Dic'98

BITACORA:
8. 98-12-16: Primera Versión, basada en LogiCalc.getExpr()

REESTRICCIONES:
Minúsculas = Mayúsculas
Los espacios son ignorados (eliminados)
El nombre de una variable esta formado por una letra

PDTES:
Será factible Objeto-base sea Expresion? y tener Expresion.traducir()?
--> expr.lexico(), expr.parser(), expr.interp() ?

*/

public class Expresion {

	String expresion;
	String vars;

public Expresion( String expr ) {
	char [] s=expr.toCharArray();
	char [] out=new char[s.length];
	int j=0;//[6c]
	for ( int i=0; i < s.length; i++ ) { // Elimina espacios
		if ( !Character.isSpaceChar(s[i]) ) { //[6c,9,11]
			out[j]=Character.toUpperCase(s[i]); // A mayúsculas
			++j;
		}
	}
	expresion=(new String(out)).substring(0,j); //[6c]
 }

public String getVars() { // Regresa lista de nombres de variables
	if ( null != vars )
		return vars;
	String vars="";
	for ( byte i=0; i < expresion.length(); i++ ) {
		char c=expresion.charAt(i);
		if ( Character.isLetter(c) ) // if letra->variable
			if ( vars.indexOf(c) < 0 ) // if not found
				vars+=c; // add var
	}
	return vars;
 }

public int numVars() { return getVars().length(); }

public boolean isEmpty() { return 0 == expresion.length(); }

public String toString() { return expresion; }

} // Expresion