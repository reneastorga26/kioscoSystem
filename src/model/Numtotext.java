
package model;

import java.io.*;



public class Numtotext{
	public static void main(String Arg[ ]) throws IOException{
	numberToText numero;
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	int num;
	String res;
        System.out.print("Ingrese numero : ");
        num = Integer.parseInt(in.readLine( ));
		numero = new numberToText(num);
		res = numero.convertirLetras(num);
		System.out.print(res);
		System.out.println("\n");
	}	
}

