/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author joange
 */
public class Leer {
	private final static InputStreamReader isr = new InputStreamReader(System.in);
	private final static BufferedReader entradaConsola = new java.io.BufferedReader(isr);

	/**
	 * Llig un text del teclat.
	 * 
	 * @param mensaje Text que es passa a l'usuaro
	 * @return el text introduit. Blanc en cas d'error
	 */
	public static String leerTexto(String mensaje) {
		limpiarBuffer();
		String respuesta = null;
		do {
			try {
				System.out.print(mensaje);
				respuesta = entradaConsola.readLine();
			} // ()
			catch (IOException ex) {
				return "";
			}
		} while (respuesta == null);
		return respuesta;

	} // ()

	/**
	 * Introducció de numeros enters
	 * 
	 * @param mensaje Missatge que es dona a l'usuari
	 * @return un enter amb el valor
	 */
	public static int leerEntero(String mensaje) {
		int n = 0;
		boolean aconseguit = false;
		while (!aconseguit) {
			try {
				n = Integer.parseInt(leerTexto(mensaje));
				aconseguit = true;
			} catch (NumberFormatException ex) {
				System.out.println(ConsoleColors.printError("Deus posar un numero correcte"));
			}
		}
		return n;
	}

	/**
	 * 
	 * @param mensaje
	 * @param limite  desde 0 hasta X, no se permiten limites negativos.
	 * @return
	 */
	public static int leerEntero(String mensaje, int limite) {
		int n = leerEntero(mensaje);
		if (!(n < limite && n > 0)) {
			System.out.println(ConsoleColors.printError("Deus posar un numero correcte maxim ="+ limite));
			return leerEntero(mensaje, limite);
		}
		return n;
	}
	/**
	 * Lee un entero entre A y B.
	 * @param mensaje
	 * @param minimo
	 * @param maximo 
	 * @return
	 */
	public static int leerEntero(String mensaje, int minimo, int maximo) {
		int n = leerEntero(mensaje);
		if (!(n > minimo-1 && n < maximo+1)) {
			System.out.println(String.format(ConsoleColors.printError("Tienes que elegir un numero entre %d y %d"),minimo,maximo));
			return leerEntero(mensaje);
		}
		return n;
	}
	/**
	 * Introducció de numeros enters
	 * 
	 * @param mensaje Missatge que es dona a l'usuari
	 * @return un enter amb el valor
	 */
	public static double leerDouble(String mensaje) {
		double n = 0.0;
		boolean aconseguit = false;
		while (!aconseguit) {
			try {
				n = Double.parseDouble(leerTexto(mensaje));
				aconseguit = true;
			} catch (NumberFormatException ex) {
				System.out.println(ConsoleColors.printError("Deus posar un numero correcte"));
			}
		}
		return n;
	}
	
	public static boolean leerBoolean(String mensaje) {
		String bool = leerTexto(mensaje);
		if(bool.equalsIgnoreCase("Si") || bool.equalsIgnoreCase("S") || bool.equalsIgnoreCase("Y") || bool.equalsIgnoreCase("Yes")) {
			return true;
		}
		else if(bool.equalsIgnoreCase("no") || bool.equalsIgnoreCase("n")) {
			return false;
		}
		else {
			System.out.println(ConsoleColors.printError("Error en la introduccion, opciones S/N."));
			return leerBoolean(mensaje);
		}
	}
	
	public static int leerCaracter(String mensaje, int minimo, int maximo) {
		int c = 1;
		System.out.print(mensaje);
		try {
			c =  entradaConsola.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(String.format("Resultado:%d  Minimo:%d  Maximo:%d",c,minimo,maximo));
		if(c == '0') {
			return 0;
		}
		if(c < minimo || c > maximo) {
			String msg = String.format("Caracter minimo %c maximo %c",(char) minimo,(char) maximo);
			System.out.println(ConsoleColors.printError(msg));
			limpiarBuffer();
			return leerCaracter(mensaje,minimo,maximo);
		}
		return c;
	}
	
	private static boolean limpiarBuffer() {
		try {
			if(entradaConsola.ready()) {
				entradaConsola.readLine();
				return true;
			}
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	static {
	}
}