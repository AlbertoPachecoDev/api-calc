/*
	NumTrad.java: Traductor para Calculadora Numérica
	Alberto Pacheco. apacheco@itch.edu.mx. Dic'98

BITACORA:
 9. 98-12-25: Calculadora Numérica. NumParser. Parser.analiza().
10. 99-11-15: BufferedReader (Java 1.2): Deprecated DataInputStream.readLine()
12. 99-11-17: replaceAll remplaza mal subcadenas, eg 2+23->A+A3. Usar 1 remplazo

BUGS:
Paréntesis no considerado como operador e.g. (1+2)*3. Sol. Parser.operadores()

Validar simbolos inválidos e.g. 1?1. Sol. call first Parser.analiza()

replaceAll falla al encontrar subcadenas, e.g. 23+231 --> A+A1
 --> Sol. temporal. deshabilitar reemplazos multiples

Acepta expr=1.0.2

PDTES:

*/

import Traductor;                 // is-a
import NumParser;                 // has-a Parser<>
import NumInter;                  // has-a Interprete<>
import java.util.Hashtable;       // uses
import java.util.StringTokenizer; // uses
import Expresion;                 // uses


public final class NumTrad extends Traductor {

public NumTrad() {
 	char[] o_p={ // Operadores/Precedencia
		'*', (char)4, // multiplicacion
		'/', (char)4, // division
		'+', (char)8, // suma
		'-', (char)8  // resta
		};
	parser=new NumParser(o_p);
	interp=new NumInter();
 }

protected Expresion
  preproc( Expresion entrada, Hashtable tabla )
    throws Exception //[9]
{
	// Sustituye constantes por variables en toda la expresion
	String exp=entrada.toString();
	String salida=exp;
	StringTokenizer tokens=new StringTokenizer(exp,parser.operadores());
	char id='A';
	while ( tokens.hasMoreTokens() ) {
		String dato=tokens.nextToken(); //Arnold/Gosling255
		Float num=Float.valueOf(dato);
// [12]		if ( tabla.contains(num) ) // Reusar si ya existe otro igual
// [12]			continue;
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
	((NumInter)interp).setNum(tabla); //[9]
	String [] row = new String[1];
	row[0] = " --> "+interp.traducir(posfija).toString();
//	p(expr_in.toString()+" --> "+posfija.toString());
	return row;
 }

public static void main( String[] args ) { run(new NumTrad()); }

} // NumTrad