package com.ieseljust.joanciscar;

import Utils.Leer;

public class UserInput implements IWarshipInput {
	private int counter = 0;
	@Override
	public int getCoord() {
		int i = counter;
		counter++;
		switch(i%2) {
		case 0:
			return Leer.leerEntero("Introduce un coordenada horizontal es un entero [1,2...], -1 per a rendirse: ",-2, WarshipMain.configuration.getCurrentBounds());
		default:
			int c = Leer.leerCaracter("Introduce un coordenada vertical es un caracter [A,B...], 0 per a rendirse: ",'A', 'A'+WarshipMain.configuration.getCurrentBounds());
			if(c == 0) {
				return -1;
			}
			return c -'A';
		}
	}

	@Override
	public IWarshipInput returnInput() {
		return this;
	}

	@Override
	public boolean needsToBeStored() {
		return true;
	}

	@Override
	public boolean isAI() {
		return false;
	}

}
