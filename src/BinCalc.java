/*
	BinCalc.java: Applet tipo Calculadora
	Alberto Pacheco. apacheco@itch.edu.mx. Nov'99

	BITACORA:
12. 99-11-18: Versión Inicial: Basada en NumCalc

*/

import AppletCalculadora; // is-a
import BinTrad;           // has-a Traductor<>

public final class BinCalc extends AppletCalculadora {

protected void setTraductor() { traductor=new BinTrad(); }

//protected String title() { return "LogiCalc"; }

protected String[] getKeys() {
	String[] keys = {
		"shl",   "shr",  "mod", "/", CMDUndo,
		"nand",  "nor",  "xor", "*", CMDCls,
		"&:and", "|:or", "not", "-", CMDClr,
		"0",     "1",    "@=",  "+", CMDCopy
	};
	return keys;
 }

protected String getHelpMsg() { // Help Text
	return	"Ayuda en Línea:\n\n1. Anote expresiones:\n"+
			"   a) En campo de entrada, ó\n"+
			"   b) Mediante botones.\n\n"+
			"2. Pulse <enter> ó botón '='\n\n"+
			"Operadores por Precedencia:\n"+
			"(), *, /, +, -, &, |\n"+
			"Pulse botón <!Help> para continuar\n";
 }

protected boolean procesaBoton( String label ) { // resto del teclado
	if ( label.equals("=") )
		return exec();
	return false;
 }

public String getAppletInfo() {
	return "BinCalc by Alberto Pacheco, 11/1999, alberto@acm.org";
 }

} // BinCalc