package com.ieseljust.joanciscar;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Utils.Leer;

public class InputFactory {
	
	public IWarshipInput create() throws NumberFormatException, IOException, SAXException, ParserConfigurationException {
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
				input.setMovements(WarshipMain.loader.loadMovements());
				return input;
		}
	}
	
}
