/*
        Pila.java. Alberto Pacheco. apacheco@itch.edu.mx. Abr'00

        [13] 00-04-20: Pila
*/

import java.util.Stack; // has-a

public class Pila {

 protected Stack info=new Stack();

 public boolean isEmpty() { return info.empty(); } //[13]

 public boolean notEmpty() { return !info.empty(); } //[13]

} // Pila

