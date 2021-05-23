/*
	Interprete.java: Clase-Base para Interpretes de Calculadora
	Alberto Pacheco. apacheco@itch.edu.mx. Dic'98

Bitácora:
[8] 98-10-06: Versión Inicial, basada en InterLogic.java

*/

import Expresion; // uses

public abstract class Interprete {

 public Object traducir( Expresion expr ) throws Exception {
	if ( null==expr || expr.isEmpty() )
		throw new Exception("Expresión vacía");
	return expr;
 } // Incluir super.traducir(expr); en subclases

} // Interprete