/*
	CxTrad.java
	Alberto Pacheco. apacheco@itch.edu.mx. Mzo'99

Bitácora:

99-03-16: Versión Inicial

*/

import Traductor;		// is-a
import CxParser;		// has-a
import java.util.Hashtable;	// uses
import java.util.StringTokenizer; // uses
import Expresion;		// uses
import Complejo;		// uses

public final class CxTrad extends Traductor {

 public CxTrad() {

	char [] o_p= {
              '*', (char)4,
              '/', (char)4,
              '+', (char)8,
              '-', (char)8 };
	parser=new CxParser(o_p);
	interp=new CxInter();
 }

 protected Expresion preproc(Expresion entrada, Hashtable tabla)
           throws Exception
 {
	String exp=entrada.toString();
	String salida=exp;

	StringTokenizer tokens=new StringTokenizer(exp,parser.operadores());
	char id='A';
	
	while( tokens.hasMoreTokens() ) {

		String dato=tokens.nextToken();

		StringTokenizer cxtok=new StringTokenizer(dato, "<>{},");

		boolean polar=(dato.indexOf('{') >= 0);

		double re=(Double.valueOf(cxtok.nextToken())).doubleValue();
		double im=(Double.valueOf(cxtok.nextToken())).doubleValue();

		Complejo comp=new Complejo(re,im,polar);

		if( tabla.contains(comp) )
			continue;

		tabla.put(new Character(id), comp);
		salida=replaceAll(salida, dato, id); // Bug: comp.toString <> dato-original
//		p("["+id+","+comp.toString()+"] <-- "+salida); // ??
		id++;
		if ( id > 'Z' )
			throw new Exception("Demasiados operandos");
		
	} // while

	return new Expresion(salida);

 } // preproc

 public String[] evalExpr( Expresion expr0 ) throws Exception {
	// 1. Analizador de Léxico (con variables)
	
	parser.analiza(expr0);

	// 2. Parser: Expr en notación posfija

	Hashtable tabla=new Hashtable(); // Tabla <variable,complejo>

	Expresion expr_in=preproc(expr0,tabla);
	Expresion posfija=parser.traducir(expr_in);

	// 3.Evalua expr p/c/valor posible

	((CxInter)interp).setCx(tabla);
//	p(tabla); // ??
	String [] row=new String[1];
	row[0] = " --> "+interp.traducir(posfija).toString();
	return row;
 }

 public static void main( String[] args) {  run(new CxTrad()); }

} // CxTrad

