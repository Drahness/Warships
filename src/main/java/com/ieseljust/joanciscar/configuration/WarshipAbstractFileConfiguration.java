package com.ieseljust.joanciscar.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.ieseljust.joanciscar.Movement;

import Utils.Leer;

public abstract class WarshipAbstractFileConfiguration implements IConfiguration {
	public static String FLAG_ROOT = "Warship";
	public static String FLAG_ATT_ID = "id";
	private int nboats = DEFAULT_NUMBER_BOATS;
	private int bounds = DEFAULT_BOUNDS;
	private int tries = DEFAULT_TRIES;
	private boolean save = true;
	private List<Movement> movementsList = new ArrayList<>();
	
	public static class FlagsConfiguration {
		public static String FLAG_ROOT = "Config";
		public static String FLAG_TAMAÑO_BOARD = "board_tam";
		public static String FLAG_NUMERO_BOATS = "num_boats";
		public static String FLAG_MAX_JUGADAS = "max_jugadas";
		public static String FLAG_GUARDAR_MOVIMIENTOS = "save_movements";	
	}
	
	public static class FlagsMovements {
		public static String FLAG_ROOT= "Moves";
		public static String FLAG_MOVE = "Move";
		public static String FLAG_ROW = "row";
		public static String FLAG_COLUMN = "col";
		public static String FLAG_RESULT = "result";
	}
	
	public static class FlagsBoats {
		public static String FLAG_ROOT = "Boats";
		public static String FLAG_BOAT = "Boat";
		public static String FLAG_DIMENSION = "Dimension";
		public static String FLAG_ORIENTACION = "Orientación";
		public static String FLAG_POSICION = "Posición";
		
		public static String FLAG_ATT_COL_POSICION = "Column";
		public static String FLAG_ATT_ROW_POSICION = "Row";
	}
	
	private File fileConf;
	
	
	public WarshipAbstractFileConfiguration(File file) {
		this.fileConf = file;
	}

	public File getFileConf() {
		return fileConf;
	}

	public void setFileConf(File fileConf) {
		this.fileConf = fileConf;
	}
	@Override
	public void set() {
		System.out.println("Que deseas modificar?");
		System.out.println("\t1- Numero de barcos = "+getCurrentBoats());
		System.out.println("\t2- Numero de jugadas maximas = "+ getCurrentMaxJugadas());
		System.out.println("\t3- Tamaño del tablero = "+getCurrentBounds());
		System.out.println("\t4- Guardar movimientos? Actual: "+getCurrentGuardarMovimientos());
		switch(Leer.leerEntero("Selecciona: ",5)-1) {
		case 0: 
			setCurrentBoats(Leer.leerEntero("Seleccion nuevo numero de barcos: ", getCurrentBounds()/2));
			break;
		case 1: 
			setCurrentMaxJugadas(Leer.leerEntero("Seleccion nuevo maximo jugadas: ", getCurrentBoats()*5,(getCurrentBounds()*getCurrentBounds())/2+1));
			break;
		case 2:
			setCurrentBounds(Leer.leerEntero("Seleccion nuevo tamaño de tablero: ", 5, 1000));
			break;
		default:
			if(getCurrentGuardarMovimientos()) {
				setCurrentGuardarMovimientos(false);
			}
			else { 
				setCurrentGuardarMovimientos(true);
			}
			break;
		}
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('\n').append('\n');
		sb.append(FlagsConfiguration.FLAG_NUMERO_BOATS).append(" = ").append(String.valueOf(this.getCurrentBoats())).append('\n');
		sb.append(FlagsConfiguration.FLAG_TAMAÑO_BOARD).append(" = ").append(String.valueOf(this.getCurrentBounds())).append('\n');
		sb.append(FlagsConfiguration.FLAG_GUARDAR_MOVIMIENTOS).append(" = ").append(String.valueOf(this.getCurrentGuardarMovimientos())).append('\n');
		sb.append(FlagsConfiguration.FLAG_MAX_JUGADAS).append(" = ").append(String.valueOf(this.getCurrentMaxJugadas()));
		sb.append('\n').append('\n');
		return sb.toString();
	}
	
	public void setCurrentBounds(int bounds) {
		this.bounds = bounds;
	}
	protected void setCurrentBoats(int nboats) {
		this.nboats = nboats;
	}
	protected void setCurrentMaxJugadas(int tries) {
		this.tries = tries;
	}
	protected void setCurrentGuardarMovimientos(boolean save) {
		this.save = save;
	}
	public int getCurrentBounds() {
		return bounds;
	}
	public int getCurrentBoats() {
		return nboats;
	}
	public int getCurrentMaxJugadas() {
		return tries;
	}
	public boolean getCurrentGuardarMovimientos() {
		return save;
	}
	
	public List<Movement> getMovements() {
		return movementsList;
	}
	public void setMovements(List<Movement> movements) {
		movementsList = movements;
	}
}
