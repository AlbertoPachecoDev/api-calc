/*
	LogiTrad.java: Container/Adapter class (PDC)
	Alberto Pacheco. apacheco@itch.edu.mx. Oct'98

Bitácora:
[6] 98-10-10: LogiTrad (versión inicial): Factoriza Traductor <- LogiCalc
    a. LogiTrad <- (OperPrior,Parser,InterLogic)
    b. ops, lexico(): Verif. Léxico. Bug: reset counters, espacios
[8] 98-12-16: Clase Traductor, Expresion
[9] 98-12-28: b2c -> b2s

BUGS:
PDTES:

*/

import Traductor; // is-a
import LogiParser;// has-a Parser<>
import LogiInter; // has-a Interprete<>
import Expresion; // uses


public final class LogiTrad extends Traductor {

 static final byte MaxVars=5; // [4] Máx. No. Variables

 public LogiTrad() {
 	char[] o_p={ // Operadores/Precedencia
		'!', (char)1, // not
		'&', (char)2, // and
		'|', (char)3, // or Bug: Letra 'V' complica algoritmos
		'+', (char)3, // xor
		'>', (char)4, // implicación ->
		'=', (char)4  // doble implicación <->
		};
	parser=new LogiParser(o_p);
	interp=new LogiInter();
 }

protected static String b2s( Object b ) throws Exception { //[4,9]
   if ( !(b instanceof Boolean) )
      throw new Exception("Bug: LogiTrad.b2c(b no es Boolean)");
   return (((Boolean)b).booleanValue()?"1":"0");
 }

public String[] evalExpr( Expresion expr_in ) throws Exception { //[3-5]

   // 1. Analizador de Léxico
   parser.analiza(expr_in); // [5h]

   // 2. Parser: Expr en notación posfija
   Expresion posfija=parser.traducir(expr_in); // [5e]

   // 3. Localiza c/variable
   String vars=posfija.getVars();

   // 4. Evalua expr p/c/valor posible
   int nv=posfija.numVars();
   if ( nv > MaxVars ) // [4] Demasiadas variables
      throw new Exception("Demasiadas variables (máx. "+MaxVars+")");
   if ( 0 == nv ) { // Expr. sin variables
//      if ( posfija.indexOf('0')<0 && posfija.indexOf('1')<0 )
//         throw new Exception("Operadores sin datos");
      String[] row = new String[1];
      row[0] = "-->"+b2s(interp.traducir(posfija)); // Eval expr
      return row;
   }

   // Genera Tabla de Verdad
   int nr=(1<<nv); // no rengs=2^n (n=no vars) Bug[4]:>> x <<
   String[] rows=new String[nr]; // lista de cadenas p/c/eval (reng)
   for ( int i=0; i < nr; i++ ) { // eval c/sust valores
      String rv=posfija.toString();
      String vals=Integer.toBinaryString(nr+i); // [4] Bug nr+
      String asig="";
      for ( int j=0; j < nv; j++  ) { // Sustituye c/var x c/val
         char c1=vars.charAt(j);
         char c2=vals.charAt(j+1); // [4] Bug j+1
         asig=asig+c1+'='+c2+' ';
         rv=rv.replace(c1,c2); // Bug[3]: rv = ..
     }
     rows[i]=asig+"--> "+
        b2s(interp.traducir(new Expresion(rv))); //[4] Eval expr
   }
   return rows;
 } // evalExpr

} // LogiTrad