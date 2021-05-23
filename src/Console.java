/*
	Console.java: Consola de Texto para Calculadoras
	Alberto Pacheco. apacheco@itch.edu.mx. Abr'00

Uso:

        main .. {
                Console cio=new Console();
                ..
                cio.p("Holoa");
                String t=cio.gets();
                String n=cio.input("Nombre");
                cio.pause();
                ..
*/

import java.io.*; // Debug & Test

public class Console {

	private BufferedReader cin;

 public Console() {
 /* Java 1.0: DataInputStream cin=new DataInputStream(System.in);*/
   cin=new BufferedReader(new InputStreamReader(System.in));
 }

 public void p(Object s) { System.out.println(s.toString()); }

 public String input(String s) {
	System.out.print(s);
	return gets();
 }

 public String gets() { //[13]
   try {
	return cin.readLine();
   } catch ( IOException ex ) { ex.printStackTrace(); }
   return "";
 }

 public String pause() {
        return input("Pulse ENTER para continuar");
 }

} // Console
