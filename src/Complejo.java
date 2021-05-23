/*
	Complejo.java
*/

class Complejo {

 private static final double kte=Math.PI/180.0;
 private boolean polar=false;
 private double re, im;

 public static double rad_a_grados( double rad ) {
	return rad / kte;
 }

 public static double grados_a_rad( double grad ) {
        return grad * kte;
 }

 public Complejo() {
	re=0.0;
	im=0.0;
 }

 public Complejo(double r, double i) {
	re=r;
	im=i;
 }

 public Complejo(double r, double i, boolean p ) {
	re=r;
	im=i;
	polar=p;
 }

 public boolean esPolar() { return polar; }

 public static Complejo conjugado(Complejo c) {
        return new Complejo(c.re, -1*c.im, c.esPolar() );

}

 public static Complejo suma(Complejo x1, Complejo y1) {
	Complejo x=x1, y=y1;
	if ( x.polar ) x=Complejo.aRect(x);
	if ( y.polar ) y=Complejo.aRect(y);
	return new Complejo(x.re+y.re,x.im+y.im); // Objeto anónimo
 }

 public static Complejo resta(Complejo x1, Complejo y1) {
	Complejo x=x1, y=y1;
	if ( x.polar ) x=Complejo.aRect(x);
	if ( y.polar ) y=Complejo.aRect(y);
	return new Complejo(x.re-y.re,x.im-y.im);
 }

 public static Complejo producto(Complejo x1, Complejo y1) {
	Complejo x=x1, y=y1;
	if ( !x.polar ) x=Complejo.aPolar(x);
	if ( !y.polar ) y=Complejo.aPolar(y);
	return new Complejo(x.re*y.re,x.im+y.im,true);
 }

 public static Complejo cociente(Complejo x1, Complejo y1) {
	Complejo x=x1, y=y1;
	if ( !x.polar ) x=Complejo.aPolar(x);
	if ( !y.polar ) y=Complejo.aPolar(y);
	return new Complejo(x.re/y.re,x.im-y.im,true);
 }

 public static Complejo aRect(Complejo x) {
	if ( !x.polar ) return x;
	double rad = grados_a_rad(x.im);
	double r = x.re * Math.cos(rad);  
	double i = x.re * Math.sin(rad);  
	return new Complejo(r,i,false);        
 }

 public static Complejo aPolar(Complejo x) {
	if ( x.polar ) return x;
	double mag = Math.sqrt(Math.pow(x.re,2.0)+Math.pow(x.im,2.0));
	double ang = Math.atan2(x.im,x.re);
        ang = rad_a_grados(ang);
	return new Complejo(mag,ang,true);
 }

 public String toString() {
	if ( polar )
		return "{"+re+","+im+"}";
	else
		return "<"+re+","+im+">";
 }

 static public void main( String [] args ) {
	Complejo x = new Complejo(3.5,2.0);
	Complejo y = new Complejo(1.5,3.0);
	Complejo z = new Complejo();

	System.out.println("\nValores Iniciales:\n");
	System.out.println("x="+x);
	System.out.println("y="+y);
	System.out.println("z="+z);

	z = Complejo.suma(x,y);

	System.out.println("\nValores despues de sumar x=x+y, z=x+y:\n");
	System.out.println("x="+x);
	System.out.println("y="+y);
	System.out.println("z="+z);

	Complejo p = Complejo.aPolar(x);
	Complejo q = Complejo.aPolar(y);

        System.out.println("\nValores Iniciales:\n");
	System.out.println("p="+p);
	System.out.println("q="+q);	

	Complejo r = Complejo.producto(p,q);

	System.out.println("\nValores despues de multiplicar r=p*q\n");
	System.out.println("p="+p);
	System.out.println("q="+q);
	System.out.println("r="+r);

        System.out.println("\nValores Iniciales:\n");
	System.out.println("p="+p);
	System.out.println("q="+q);
 
        Complejo w = Complejo.cociente(p,q); 

        System.out.println("\nValores despues de dividir w=p/q\n");
	System.out.println("p="+p);
	System.out.println("q="+q);
	System.out.println("w="+w);

	Complejo G = Complejo.conjugado(x);
	Complejo H = Complejo.conjugado(y);
	Complejo I = Complejo.conjugado(z);
	Complejo J = Complejo.conjugado(p);
	Complejo K = Complejo.conjugado(q);
	Complejo L = Complejo.conjugado(r);
	Complejo M = Complejo.conjugado(w);

        System.out.println("El conjugado de x es :"+G);
	System.out.println("El conjugado de y es :"+H);
	System.out.println("El conjugado de z es :"+I);
	System.out.println("El conjugado de p es :"+J);
	System.out.println("El conjugado de q es :"+K);
	System.out.println("El conjugado de r es :"+L);
	System.out.println("El conjugado de w es :"+M);
 }

} // Complejo

