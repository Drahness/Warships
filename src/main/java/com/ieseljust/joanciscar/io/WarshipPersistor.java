package com.ieseljust.joanciscar.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ieseljust.joanciscar.Movement;

import pkg2020_ad_p1_warship.Board;
import pkg2020_ad_p1_warship.Boat;
import pkg2020_ad_p1_warship.Cell;

public class WarshipPersistor implements IPersistor {
	
	private File boat_out = new File("boat_out.txt");
	private File movement_out = new File("moviments_out.txt");
	
	private FileWriter movemWri;
	private FileWriter boatWri;
	
	private List<Movement> movs;
	
	private WarshipPersistor() {}
	private static WarshipPersistor instance;
	public static WarshipPersistor getInstance() {
		if(instance == null) {
			instance = new WarshipPersistor();
		}
		return instance;
	}
	
	
	
	public void saveBoats(Board board) throws IOException {
		boatWri = new FileWriter(boat_out);
		
		Boat[] boats = board.getBoats();
		for (int i = 0; i < boats.length; i++) {
			int[] arr = new int[] {i
									,boats[i].getDimension()
									,boats[i].getDireccion()
									,boats[i].getFilapos()
									,boats[i].getColumnapos()};

			StringBuilder sb = new StringBuilder();
			sb	.append(arr[0]).append(';')
				.append(arr[1]).append(';')
				.append(arr[2]).append(';')
				.append(arr[3]).append(';')
				.append(arr[4]).append('\n');

			boatWri.write(sb.toString());
		}
		boatWri.close();
		
	}
	
	public void saveMovements() throws IOException {
		movemWri = new FileWriter(movement_out);

		for (Movement movement : movs) {
			StringBuilder sb = new StringBuilder();
			
			sb.append(movement.getId()).append(';')
			  .append(movement.getFila()).append(';')
			  .append(movement.getColumna()).append(';')
			  .append(movement.getResultado()).append('\n');
			
			movemWri.write(sb.toString());
		}
		movemWri.close();
	}
	public void registerMovement(Movement mov) {
		movs.add(mov);
	}
	public void resetMovs() {
		movs = new ArrayList<>();
	}
}
