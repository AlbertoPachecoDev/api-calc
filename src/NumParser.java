/*
        NumParser.java: Parser p/Calculadora Numerica
	Alberto Pacheco. apacheco@itch.edu.mx. Dic'98, Abr'00
*/

import java.io.*; // Debug & Test
import Parser; // is-a

public class NumParser extends Parser {

 NumParser( char [] o_p ) { super(o_p); }

 public boolean esOperando(char c) {
	return (Character.isDigit(c) || c=='.');
 }

 public static void main( String [] args ) {

        Console cio=new Console();
 	char[] o_p={ // Operadores/Precedencia
		'*', (char)4, // multiplicacion
		'/', (char)4, // division
		'+', (char)8, // suma
		'-', (char)8, // resta
		};
	NumParser parser=new NumParser(o_p);
        cio.p("\n** Calculadora Numerica **"); //[13]
        cio.p("Etapa: Conversion Expresion Notacion Infija a Posfija");
        cio.p("Nota: Utilize numeros de un digito para probar, eg 1+2*3");
	while ( true ) {
                String expr_leida=cio.input("\nAnota expresion (Enter=salir):"); //[13]
		if ( expr_leida.equals("") )
			return;
		try {
			Expresion expr_infija= new Expresion(expr_leida);
                        // 1. Analizador de Lexico
			parser.analiza(expr_infija);
                        // 2. Parser: Expr en notacion posfija
			Expresion expr_posfija=parser.traducir(expr_infija);
                        cio.p("Entrada: Expresion infija  -> "+expr_infija);
                        cio.p(" Salida: Expresion posfija -> "+expr_posfija);
		} catch ( Exception ex ) { ex.printStackTrace(); }
	}
 } // main

} // NumParser
