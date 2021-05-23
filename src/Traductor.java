/*
    Traductor.java: Traductor abstracto para Calculadora
    Colega del Patron de Dise�o Mediador-Colega
    Alberto Pacheco. apacheco@itch.edu.mx. Dic'98

BITACORA:
 8. 98-12-16: Versi�n inicial, basada en LogiTrad
12. 99-11-17: replace/replaceAll

BUGS:
	replaceAll falla en 2+23->A+B3

PDTES:

*/

import Parser;		// has-a
import Interprete;	// has-a
import Expresion;	// uses
import Console;		// uses [13]
import java.io.*;       // uses (debug w/run)

public abstract class Traductor {

 protected Parser     parser;
 protected Interprete interp;

/* Template:
 public Traductor<X>() {
	parser = new Parser"X"();
	interp = new Interprete"X"();
 }
*/

 // Remplaza <dato:String> por <id:char> en <original:String>
 protected String replace(String original, String dato, char id)
    throws Exception {
	int inicio=original.indexOf(dato);
	if ( inicio < 0 ) {
		throw new Exception("Bug -> BinTrad.replace: no encontro reemplazo!");
//		return original;
	}
	String izq="";
	if ( inicio > 0 )
		izq=original.substring(0,inicio);
	String der=original.substring(inicio+dato.length());
	original=izq+id+der;
	return original;
 }

 protected String replaceAll(String original, String dato, char id) {
 // Remplaza apariciones de <dato:String> por <id:char> en <original:String>
   while (true) {
	int inicio=original.indexOf(dato);
	if ( inicio < 0 )
		break;
	String izq="";
	if ( inicio > 0 )
		izq=original.substring(0,inicio);
	String der=original.substring(inicio+dato.length());
	original=izq+id+der;
   }
   return original;
 }

public abstract String[] evalExpr(Expresion expr_in) throws Exception;
/*	Template:
	// 1. Analizador de L�xico
	parser.analiza(expr_in);
	// 2. Parser: Expresi�n a notaci�n posfija
	Expresion expr_pos = parser.traducir(expr_in);
	// 3. Evalua expr p/c/valor posible
	interp.traducir(expr_pos);
*/

public static void run( Traductor calc ) {
	Console io=new Console();
	io.p("\n** Calculadora "+calc.toString()+" **"); //[13]
	io.p("Etapa: Interprete");
	while ( true ) {
		String es=io.input("\nExpresion (enter=terminar): ");
		if ( es.equals("") ) //[10]
			return;
		String[] res;
		try {
			res=calc.evalExpr(new Expresion(es));
			io.p("Resultado de "+es+res[0]);
		} catch ( Exception e2 ) { e2.printStackTrace(); }
	}
 }

} // class Traductor