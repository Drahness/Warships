package com.ieseljust.joanciscar;

import java.io.IOException;

import Utils.Leer;

public class InputFactory {
	
	public IWarshipInput create() {
		System.out.println("Como van a entrar las tiradas?");
		System.out.println("\t1 - Manualmente");
		System.out.println("\t2 - Ordenador");
		System.out.println("\t3 - Fichero y despues ordenador");
		System.out.println("\t4 - Fichero y despues manualmente");
		
		int opcion = Leer.leerEntero("Selecciona: ",4)-1;
		switch(opcion) {
			case 0:
				return new UserInput();
			case 1:
				return new AIInput();
			default:
				FileInput input = new FileInput(opcion%2);
				try {
					input.setMovements(WarshipMain.loader.loadMovements());
				} catch (NumberFormatException e) {
					System.out.println("Fichero mal-formado.");
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("Error al leer el fichero.");
					e.printStackTrace();
				};
				return input;
		}
	}
	
}
