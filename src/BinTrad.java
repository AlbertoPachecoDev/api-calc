/*
	BinTrad.java: Traductor para Calculadora Binaria
	Alberto Pacheco. apacheco@itch.edu.mx. Nov'99

BITACORA:

12. 99-11-18: Versión Inicial

BUGS:

PDTES:

*/

import Traductor;                 // is-a
import BinParser;                 // has-a Parser<>
import BinInter;                  // has-a Interprete<>
import java.util.Hashtable;       // uses
import java.util.StringTokenizer; // uses
import Expresion;                 // uses


public final class BinTrad extends Traductor {

 public BinTrad() {
 	char[] o_p={ // Operadores/Precedencia
		'*',  (char)2, // multiplicación
		'/',  (char)2, // división
		'+',  (char)4, // suma
		'-',  (char)4, // resta
		'&',  (char)6, // and
		'|',  (char)8, // or
		};
	parser=new BinParser(o_p);
	interp=new BinInter();
 }

 protected Expresion
  preproc( Expresion entrada, Hashtable tabla )
    throws Exception //[9]
 {
	// Sustituye constantes por variables en toda la expresión
	String exp=entrada.toString();
	String salida=exp;
	StringTokenizer tokens=new StringTokenizer(exp,parser.operadores());
	char id='A';
	while ( tokens.hasMoreTokens() ) {
		String dato=tokens.nextToken(); //Arnold/Gosling255
		Integer num=Integer.valueOf(dato,2); // bin --> dec
		tabla.put(new Character(id),num);
		salida=replace(salida,dato,id);
//		p("<"+id+","+num.toString()+">. Expr="+salida);
		++id;
		if ( id >= 'Z' )
			throw new Exception("Demasiados operandos"); // Sólo 26 vars A..Z
	}
	return new Expresion(salida);
 }

 public String[] evalExpr( Expresion expr0 ) throws Exception {
	// 1. Analizador de Léxico (con variables)
	parser.analiza(expr0); //[5h]
	// 2. Parser: Expr en notación posfija
	Hashtable tabla   = new Hashtable();          //[9] tabla <variable,numero>
	Expresion expr_in = preproc(expr0,tabla);     //[9] sustituye x variables
	Expresion posfija = parser.traducir(expr_in); //[5e]
	// 3. Evalua expr p/c/valor posible
        ((BinInter)interp).setNum(tabla); //[9]
	String [] row = new String[1];
	int r=((Integer)(interp.traducir(posfija))).intValue();
	row[0] = " --> "+Integer.toBinaryString(r);
//	p(expr_in.toString()+" --> "+posfija.toString());
	return row;
 }

 public static void main( String[] args ) { run(new BinTrad()); }

} // BinTrad
