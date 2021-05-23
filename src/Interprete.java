/*
	Interprete.java: Clase-Base para Interpretes de Calculadora
	Alberto Pacheco. apacheco@itch.edu.mx. Dic'98

Bit�cora:
[8] 98-10-06: Versi�n Inicial, basada en InterLogic.java

*/

import Expresion; // uses

public abstract class Interprete {

 public Object traducir( Expresion expr ) throws Exception {
	if ( null==expr || expr.isEmpty() )
		throw new Exception("Expresi�n vac�a");
	return expr;
 } // Incluir super.traducir(expr); en subclases

} // Interprete