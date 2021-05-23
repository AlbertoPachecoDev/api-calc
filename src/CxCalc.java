/* 
      CxCalc.java: Calculadora para Operaciones con Números Complejos
      Alberto Pacheco. apacheco@itch.edu.mx. Mzo'99

Bitácora:
99-03-16: Versión Inicial

*/


import AppletCalculadora; //is-a
import CxTrad;            //has-a Traductor<>

public final class CxCalc extends AppletCalculadora {

 protected void setTraductor() { traductor=new CxTrad(); }

 protected String[] getKeys() {
 	String[] keys = {
		 "7", "8", "9", "*",  CMDUndo,
 		 "4", "5", "6", "/",  CMDCls,
		 "1", "2", "3", "-",  CMDClr,
		 "0", ".", "@=", "+", CMDCopy };
	return keys;
 }

 protected String getHelpMsg() { // Help Text
	return 	"Ayuda en línea:\n\n1. Introduzca expresiones:\n"+
			"  a).- En campo de entrada, ó\n"+
			"  b).- Mediante botones.\n\n"+
			"2. Pulse <Enter> ó botón '='\n\n"+
			"Operadores por Precedencia:\n"+
			"    (), *, /, +, -\n"+
			"No Complejo Polar: {mag,ang}\n"+
			"No Complejo Rectangular: <x,y>\n\n"+
			"Pulse botón <!Help> para continuar\n";
 }

 protected boolean procesaBoton( String label ) { // Resto del teclado
	if ( label.equals("=") )
		return exec();
	return false;
 }

 public String getAppletInfo() {
	 return "CxCalc by Alberto Pacheco, 03/99, alberto@acm.org";
 }

}//CxCalc


