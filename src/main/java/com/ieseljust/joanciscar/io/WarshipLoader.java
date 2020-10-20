package com.ieseljust.joanciscar.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.ieseljust.joanciscar.Movement;
import com.ieseljust.joanciscar.WarshipMain;
import com.ieseljust.joanciscar.configuration.WarshipAbstractFileConfiguration.FlagsConfiguration;
import com.ieseljust.joanciscar.configuration.WarshipConfigurationProperties;

import pkg2020_ad_p1_warship.Board;
import pkg2020_ad_p1_warship.Boat;

public class WarshipLoader implements ILoader {
	private static WarshipLoader instance;
	
	private File boat_in = new File("boat_in.txt");
	private File movement_in = new File("moviments_in.txt");
	
	private FileReader movemRead;
	private FileReader boatRead;
	
	private WarshipLoader() {}
	
	public static WarshipLoader getInstance() {
		if(instance == null) {
			instance = new WarshipLoader();
		}
		return instance;
	}
	
	public Movement[] loadMovements() throws NumberFormatException, IOException {
		BufferedReader br;
		try {
			movemRead = new FileReader(movement_in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		br = new BufferedReader(movemRead);
		br.mark(10000);
		Movement[] movements = new Movement[(int) br.lines().count()];
		br.reset();
		for (int i = 0; i < movements.length; i++) {
			String currentRow="";
			currentRow = br.readLine();
			String[] split = currentRow.split(";");
			movements[i] = new Movement(Integer.parseInt(split[0]),Integer.parseInt(split[1]),Integer.parseInt(split[2]),Integer.parseInt(split[3]));	
		}
		br.close();
		movemRead.close();
		return movements;
	}
	
	public Boat[] loadBoats() throws IOException, NumberFormatException, IllegalArgumentException {
		BufferedReader br;
		try {
			boatRead = new FileReader(boat_in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		br = new BufferedReader(boatRead);
		br.mark(10000);
		Boat[] boats = new Boat[(int) br.lines().count()]; // Esto deja el puntero al final lol
		br.reset();
		for (int i = 0; i < boats.length; i++) {
			String[] cb = br.readLine().split(";");
			boats[i] = new Boat(Integer.parseInt(cb[1]),Integer.parseInt(cb[2]),Integer.parseInt(cb[3]),Integer.parseInt(cb[4]),Integer.parseInt(cb[0]));			
		}
		br.close();
		boatRead.close();
		if(Boat.getMaxCoordOfBoats(boats) >= WarshipMain.configuration.getCurrentBounds()) {
			throw new IllegalArgumentException("Los barcos no caben en el tablero que tenemos definido.");
		}
		return boats;
	}
	
	public Board loadBoardSafe() throws IOException, NumberFormatException {
		Boat[] boats = loadBoats();
		WarshipMain.configuration.setCurrentBounds(Boat.getMaxCoordOfBoats(boats));
		Board board = new Board(boats);
		return board;
	}
}
