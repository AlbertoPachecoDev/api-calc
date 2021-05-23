/*
	LogiCalc.java: Applet tipo Calculadora Booleana
	Alberto Pacheco. apacheco@itch.edu.mx. Sep-Dic'98

BITACORA:
[1] 98-09-23: Versión Inicial. Prototipo=Applet s/intérprete
[2] 98-10-05
[3] 98-10-06
[4] 98-10-07
[5] 98-10-10
[6] 98-10-10
[7] 98-10-20
[8] 98-12-16
[9] 98-12-19
    98-12-26

BUGS:
	
PDTES:

OJO -> Someterlo al Concurso ACM para JAVA!!

Actualizar fecha/hora (threads)
BOTON: !Keys->Keys!
BOTON: Undo
Time
Migrar a JDK 1.1: JAR-file, PopUpMenu,
	Graphic buttons(light-components),
		new event handlin model
*/

import AppletCalculadora; // is-a
import LogiTrad;          // has-a Traductor<>
import Expresion;         // has-a, uses

public final class LogiCalc extends AppletCalculadora {

   Expresion premisas; // [9] Resguardo para premisas

private boolean addPrem() { //[5e,9]
   Expresion tmp=getExpr(); //[9]
   if ( null == premisas || premisas.isEmpty() ) { //[8,9]
      if ( tmp.isEmpty() ) { //[9] Ignore
         outStatus.setText("");
         return true;
      }
      premisas=new Expresion("("+tmp+")");//[6,8,9]
   } else {
      premisas=new Expresion(premisas+"&("+tmp+")");//[6,8,9]
   }
   inExpr.setText("");
   addItem("Facts = "+premisas);//[9]
   return true;
 }

// - - - -

protected void setTraductor() { traductor=new LogiTrad(); }

protected String[] getKeys() {
	String[] keys = {
		"S", "1:true", "0:false", "=:<-->", CMDUndo,
		"R", "(:(..", "):..)", ">:-->", CMDCls,
		"Q", "!:not", "+:xor", "@Prem", CMDClr,
		"P", "&:and", "|:or", "@Goal", CMDCopy
	};
	return keys;
 }

protected String getHelpMsg() { // Help Text
   return "Ayuda en Línea:\n\n1. Anote expresiones:\n"+
          "   a) En campo de entrada, o\n"+
          "   b) Mediante botones.\n\n"+
          "2. Pulse <enter> ó botón <Prem> (inserta premisa)\n"+
          "   ó botón <Goal> (despúes de n-premisas).\n\n"+
          "3. Aparece el resultado (tabla de verdad).\n"+
          "\nOperadores por precedencia:\n"+
          "   (), !, &, |, +, >, =\n"+
          "Hay dos valores:0 y 1, y hasta 5 variables (una letra a..z)\n"+
          "Se ignoran espacios y todo se convierte a mayúsculas.\n"+
          "\nEjemplos: !!P, P&Q|R, !P&!(Q|R)\n"+
          "\nPulse botón <!Help> para seguir\n";
 }

protected boolean procesaBoton( String label ) { // resto del teclado
   if ( label.equals("Prem") ) //[5e] Insert a Fact
      return addPrem();
   if ( label.equals("Goal") ) { //[5e] Insert Goal & Eval
      expr=getExpr();//[6]
      if ( expr.isEmpty() ) { //[9] Ignore
         outStatus.setText("");
         return true;
      }
      if ( null!=premisas && !premisas.isEmpty() ) //[9] Bug
         expr=new Expresion("("+premisas+")>("+expr+")"); //Bug:(Goal)
      premisas=null;
      return execExpr();
   }
   return false;
 }

public String getAppletInfo() { //[7]
   return "LogiCalc by Alberto Pacheco, 12/98, alberto@acm.org";
 }

} // LogiCalc
