/*
	OperPrior.java: par ordenado <operador:char, precedencia:int>
	Alberto Pacheco. apacheco@itch.edu.mx. Oct'98

	Entrada:
		Asume def. operadores y precedencia a través de un char[]
		Donde primer elemento es el operador, seguido de su precedencia.
		No necesita especificar paréntesis (prioridad max=0)

	Limitaciones:
		Precedencia 1..99 (mayor->menor)

	Ejemplo:
		char[] o_p = {
			'!', (char)1, // not
			'&', (char)2, // and
			'V', (char)3, // or
			};
		OperPrior op_pr = new OperPrior(o_p);

	Bitácora:
[01]	98-10-05: Versión Inicial. Extiende Hashtable. Interface CteExpr
[02]	98-10-07: Bug:!!P->!P!->EmptyStackException Sol:OperPior.esMayor()
[11]	99-11-16: Incorpora ctes. definidas en CteExpr, la cual desaparece

*/

import java.util.Hashtable; // is-a

public final class OperPrior extends Hashtable {

// public static final char SPACE = ' ';      // Espacio [8]
// public static final char CR    = '\n';     // Salto línea [8]
// public static final char TAB   = '\t';     // Tabulador [8]

 public static final int  MayorPrior = 0;   // Mayor precedencia: '('
 public static final int  MenorPrior = 100; // Menor precedencia: '['

// s = lista de <operadores,precedencia>

 OperPrior( char[] s ) {
	super( 2 + s.length/2 );  // dimension (heurístico)
	put('(',MayorPrior); // Paréntesis (mayor prioridad)
	put('[',MenorPrior); // Paréntesis(reservado p/Parser) (menor)
	for ( int i=0; i < s.length; i+=2 )
		put( s[i], (int)s[i+1] );
 }

 public void put( char op, int pr ) {
		put( new Character(op), new Integer(pr) );
 }

 public int get( char op ) {
	return ((Integer)get(new Character(op))).intValue();
 }

 public boolean esOperador( char c ) {
	return containsKey(new Character(c));
 }

 public int prior( char c ) { return get(c); }

 public boolean esMayor( char op1, char op2 ) { //[2]
	return prior(op1) >= prior(op2);
 }

} // OperPrior