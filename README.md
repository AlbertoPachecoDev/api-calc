# api-calc

Generic Calculator Java API (generic and reusable basic expressions parser and interpreter) 

Framework de clases de Java para construir Calculadoras

By Alberto Pacheco (alberto@acm.org)

First prototype: Sept-Dec, 1998

Versions: March, 1999, Dec, 1999, April, 2000

Based on:

- A. Pacheco, C++ course notes (calc.hpp & calc.cpp), July, 1994.
- H. Katzan Jr, "Invitation to Forth", , PBI, 1981.

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -		
Componentes
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -		
### ProblemDomain-Framework
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -		
            Expresion         <-has-a String
            OperPrior         <-is-a  Hashtable
            Pila              <-has-a Stack
            CharPila          <-is-a  Pila
            CharFila          <-is-a  Vector
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            Parser [abstract]
                              <-has-a OperPrior
                              <-uses  CharFila, CharPila
                              
            Interprete [abstract]                               
                              <-uses  Expresion
                              
            Traductor         <-has-a Parser<>, Interprete<>
                              <-uses  Expresion
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -		
### UI-Framework
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -		
            AppletCalculadora [abstract]
                              <-is-a  Applet
                              <-has-a Traductor<>
                              <-uses  Expresion
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -		

### LogiCalc Application

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -		
### ProblemDomain-Application
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -		
            BoolPila          <-is-a  CharPila
            
            LogiParser        <-is-a  Parser
            
            LogiInter         <-is-a  Interprete
                              <-uses  CharFila, BoolPila, Expresion
                              
            LogiTrad          <-is-a  Traductor
                              <-has-a Parser<LogiParser>
                              <-has-a Interprete<LogiInter>
                              <-uses  Expresion
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -		
## UI-Application
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -		
            LogiCalc          <-is-a  Applet
                              <-has-a Traductor<LogiTrad>
                              <-has-a Button, Label, TextField, TextArea
                              <-uses  Expresion
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -		

### NumCalc Application

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -		
### ProblemDomain-Application
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -		
            NumPila           <-is-a  CharPila
                                     
            NumParser         <-is-a  Parser
  
            NumInter          <-is-a  Interprete
                              <-uses  CharFila, NumPila, Expresion
  
            NumTrad           <-is-a  Traductor
                              <-has-a Parser<NumParser>
                              <-has-a Interprete<NumInter>
                              <-uses  Expresion
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -		
### UI-Application
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -		
            NumCalc           <-is-a  Applet
                              <-has-a Traductor<NumTrad>
                              <-has-a Button, Label, TextField, TextArea
                              <-uses  Expresion
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -		

## PROCESO:
1. Leer expresion logica en notacion infija, e.g. P+Q
2. Verificar expresion lexicograficamente
3. Convertir expresion a notacion posfija, e.g. PQ+
4. Evaluar expresion
5. Reportar resultado

## Limitaciones:
- token=una-letra, donde token=operador|operando|valor
- La asociatividad es siempre de izquierda a derecha
- Prioridad operadores 1..99
 - No verifica lexico, trata operadores invalidos como operandos
 - No verifica minusculas/mayuscula en operandos
- No verifica duplicados en def. operadores/precedencia
 - NO soporta analisis sintactico c/Expr Regulares, AFD, BNF
