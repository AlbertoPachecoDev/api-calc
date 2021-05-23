/*
	AppletCalculadora.java
	Clase Abstracta para Applet de Calculadora
	Alberto Pacheco. apacheco@itch.edu.mx. Dic'98

BITACORA:
8. 98-12-16: Patron de Diseño: Mediator-Colega: Clases
9. 98-12-26: AppletCalculadora factorizando clases LogiCalc y NumCalc

*/

import java.applet.Applet; // is-a
import Traductor;          // has-a
import Expresion;          // has-a
import java.awt.*;         // has-a Button, Label, TextField, TextArea
import java.util.Hashtable;// has-a

public abstract class AppletCalculadora extends Applet {

	// Máx No Líneas outSalida
	static final byte   MaxLns=(byte)((1<<LogiTrad.MaxVars)+1); //[5g]
	static final String HelpOn="Help!";  //[5f]
	static final String HelpOff="!Help"; //[5f]
	static final String Undo="Undo";     //[9]
	static final String Clr="Clr";       //[9]
	static final String Copy="Copy";     //[9]
	static final String Cls="Cls";       //[9]
	static final char   CMD='@';         //[9]
	static final char   SEP=':';         //[9]
	static final String CMDUndo=CMD+Undo;//[9]
	static final String CMDClr=CMD+Clr;  //[9]
	static final String CMDCopy=CMD+Copy;//[9]
	static final String CMDCls=CMD+Cls;  //[9]

protected Traductor  traductor; // Tipo de Traductor específico
protected Expresion  expr;      // Expr=variable-1-letra-mayúscula, s/espacios

private   String     buffer;    //[5f] Soporte p/pantalla de ayuda

private   byte       counter=1; // Contador de líneas
private   byte       lines=1;   // Líneas presentes en ventana de resultados

private   Hashtable  botones;   //[9] Tabla <button-label,symbol>
private   Button[]   btnKey;    //[9] Teclado

private   Button     btnHelp;
protected Label      outStatus;
protected TextField  inExpr;	//[2]Bug: antes TextArea
private   TextArea   outSalida;

// - - - UTILERIAS - - - \\

private static String ErrMsj(String s) { //[5]
	if ( s.indexOf(':') >= 0 ) // Elimina "java.lang.Exception:"
		return s.substring(s.indexOf(':')+2);
	else	//[6c]s.substring(s.lastIndexOf('.')+1);//Borra "java.lang.XX"
		return s+"\nConsulte <Help!>";
 }

protected void addItem(String text) { //[3,4] Backwards
	String t=outSalida.getText();
	if ( lines > MaxLns ) { //[4]
		t=t.substring(0,t.lastIndexOf('\n')); //Bug:substring
		lines=MaxLns;
	}
	if ( text.charAt(0) != '\t' ) {
		outSalida.setText(counter+") "+text+"\n"+t); //[4]
		++counter;
	} else {
		outSalida.setText("    "+text.substring(1)+"\n"+t); //[4]
	}
	++lines;
 }

private boolean addInput( Object obj ) { //[4,5c,9]
	String in=inExpr.getText()+obj.toString();
	inExpr.setText(in); // JDK1.0.2
	return true;
 }

protected Expresion getExpr() {//[6,8]
	return new Expresion(inExpr.getText());
 }

private String[] evalExpr() { //[5e,8]
	String[] r;
	try {
		r=traductor.evalExpr(expr);//[5g]
	} catch ( java.util.EmptyStackException e1 ) { // [9]
		r=new String[1];
		r[0]="\nError: "+expr+" [Faltan o Sobran Operadores]";
		e1.printStackTrace();
	} catch ( Exception e2 ) {
		r=new String[1];
		r[0]="\nError: "+expr+" ["+ErrMsj(e2.toString())+"]";//[5]
		e2.printStackTrace();
	}
	return r;
 }

protected boolean execExpr() { //[5e]
	outStatus.setText("Procesando..");
	String[] r=evalExpr();//[5g,8]
	if ( 1==r.length ) { // Reporte de un renglón
		addItem("Expresión: "+expr+r[0]);
	} else { // Reporte tipo tabla (backwards)
		for ( byte i=(byte)(r.length-1); i>=0; i-- ) //Bug:>=0
			addItem("\t"+(i+1)+") "+r[i]);
		addItem("Expresión: "+expr); //[4]
	}
	//??Thread:1 seg.. cambiar luego a "Anote.."
	outStatus.setText("Procesando.. OK!!");
//[7]	expr="";
	inExpr.setText(""); //Bug remove (expr)
	return true;
 }

private void initTeclado() { //[9] Inicializa 'BtnKey[]' y tabla 'botones'
   String[] keys=getKeys();
   int tam=keys.length;
   botones=new Hashtable(3*tam/4); // <Str-label,Char-token>, dim-heurístico
   btnKey=new Button[tam];
   for ( int i=0; i < tam; ++i ) {
      String label;
      if ( keys[i].charAt(0) == CMD ) { // Tecla-accion CMD+LABEL
         label=keys[i].substring(1);
      } else {
          if ( keys[i].length()>1 && keys[i].charAt(1)==SEP ) // Tecla-dato
             label=keys[i].substring(2); // Formato "C:LABEL"
          else
             label=keys[i];              // Formato "C"
          botones.put(label,new Character(keys[i].charAt(0)));
      }
      btnKey[i]=new Button(label);
   }
 }

private void setBtns( Panel btns ) { //[9] Inserta todas las teclas
	for ( int i=0; i<20; i+=5 ) {
	    Panel r = new Panel();
		r.setLayout(new GridLayout(1,5));
		for ( int j=i; j<(i+5); j++ )
			r.add( btnKey[j] );
		btns.add(r);
	}
 }

private boolean esTeclaDirecta( String key ) { //[9]
	return botones.containsKey(key); // Deduce símbolo de Button-label
 }

// - - - -

protected abstract void setTraductor(); // traductor=Traductor<T>

protected abstract String[] getKeys();
/*
	String[] keys = { // 5x4
			"0", "1", "@=", "+", CMDUndo,
			"p", "&:and", "!:not", ">:->", CMDCls,
			..
		};
	return keys;
*/

protected abstract String getHelpMsg(); // Help Text

protected abstract boolean procesaBoton( String label ); // resto del teclado

public abstract String getAppletInfo();

// - - - -

protected boolean exec() { //[9]
	showStatus("");//[7]
	expr=getExpr();//[6]
	return execExpr();//[9]
 }

// - - - -

public void init() {
/* JDK1.1 Notes:
	a) TextField senses <enter>?
	b) TextArea.SCROLLBARS_VERTICAL_ONLY
*/
	setTraductor();
	initTeclado();

	btnHelp   = new Button(HelpOn);//[5f]
	outStatus = new Label();
	inExpr    = new TextField();
	outSalida = new TextArea(10,24);
	outSalida.setEditable(false);

	setBackground(Color.pink); //[5]
	setForeground(Color.blue);
	setFont(new Font("Helvetica",Font.BOLD,14));

	setLayout(new GridLayout(2,1));

	// (1/2): Título, Botones, Expr, Status [4,5]
	{
	  Panel btns = new Panel();
	  btns.setLayout(new GridLayout(7,1)); //[5]

	  { // (1/7): Título
	    Panel r1 = new Panel();
	      r1.setLayout(new GridLayout(1,3));
	      r1.add(new Label(this.getClass().getName())); //[9] Title
	      r1.add(new Label("")); //?? Time
		  Panel r11 = new Panel();
		  r11.setLayout(new GridLayout(1,2));
	      	r11.add(new Button("!Keys")); //??Temp
	      	r11.add(btnHelp);//[5f]
		  r1.add(r11);
	    btns.add(r1);
	  }

	  // (2-5/7): teclas row 1-4 (4x5)
      setBtns(btns);

	  {// (6-7/7) Entrada y Status
		outStatus.setText("Anote una Expresión");
		btns.add(outStatus);
		btns.add(inExpr);
	  }
	  add(btns);
	} // END-BTNS

	// (2/2): outSalida
	add(outSalida);

	validate();
 }

public boolean action( Event event, Object obj ) //[9]
{
	Object destino = event.target;

	if ( destino == btnHelp || buffer != null ){//[5f]
		outStatus.setText("Pulse un botón para regresar");
		String m=btnHelp.getLabel();
		if ( m.equals(HelpOn) ) {
			buffer=outSalida.getText();
			outSalida.setText(getHelpMsg()); //[9]
			btnHelp.setLabel(HelpOff);
			return true;
		} else {
			outSalida.setText(buffer);
			buffer=null;
			btnHelp.setLabel(HelpOn);
		}
	}
	if ( destino instanceof Button ) {
		String label=((Button)(destino)).getLabel();
		if ( esTeclaDirecta(label) ) { // Write symbol
			return addInput( botones.get(label) );
		} else if ( label.equals(Clr) ) { //[7]
			inExpr.setText("");
			outStatus.setText("");
			showStatus("");
			return true;
		} else if ( label.equals(Cls) ) { //[7]
			outSalida.setText("");
			outStatus.setText("");
			showStatus("");
			return true;
		} else if ( label.equals(Copy) ) { //[7]
			inExpr.setText(expr.toString());
			outStatus.setText("");
			showStatus("");
			return true;
		} else
			procesaBoton( label );
	}
	if ( destino == inExpr ) { //[5] <enter>
		return exec(); //[9]
	}
	return false;
 }

public boolean mouseEnter( Event evt, int x, int y ) { //[7]
	showStatus(getAppletInfo());
	return true;
 }

public boolean mouseExit( Event evt, int x, int y ) { //[7]
	showStatus("");
	return true;
 }

} // AppletCalculadora
