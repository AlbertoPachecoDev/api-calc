/*
	NumCalc.java: Applet tipo Calculadora
	Alberto Pacheco. apacheco@itch.edu.mx. Dic'98

	BITACORA:
9. 26-12-98: Versi�n Inicial. Clase AppletCalculadora.

PDTE:

*/

import AppletCalculadora; // is-a
import NumTrad;           // has-a Traductor<>

public final class NumCalc extends AppletCalculadora {

protected void setTraductor() { traductor=new NumTrad(); }

protected String title() { return "Calki"; }

protected String[] getKeys() {
	String[] keys = {
		"9", "8", "7",  "/", CMDUndo,
		"4", "5", "6",  "*", CMDCls,
		"1", "2", "3",  "-", CMDClr,
		"0", ".", "@=", "+", CMDCopy
	};
	return keys;
 }

protected String getHelpMsg() { // Help Text
	return	"Ayuda en L�nea:\n\n1. Anote expresiones:\n"+
			"   a) En campo de entrada, �\n"+
			"   b) Mediante botones.\n\n"+
			"2. Pulse <enter> � bot�n '='\n\n"+
			"Operadores por Precedencia:\n"+
			"    (), *, /, +, -\n"+
			"Acepta N�meros con decimales.\n\n"+
			"Pulse bot�n <!Help> para continuar\n";
 }

protected boolean procesaBoton( String label ) { // resto del teclado
	if ( label.equals("=") )
		return exec();
	return false;
 }

public String getAppletInfo() {
	return "NumCalc by Alberto Pacheco, 04/11, alberto@acm.org";
 }

} // NumCalc