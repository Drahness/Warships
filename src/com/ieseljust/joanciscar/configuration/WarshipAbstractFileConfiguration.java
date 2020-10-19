package com.ieseljust.joanciscar.configuration;

import java.io.File;

import Utils.Leer;

public abstract class WarshipAbstractFileConfiguration implements IConfiguration {
	public static String TAMAÑO_BOARD = "board_tam";
	public static String NUMERO_BOATS = "num_boats";
	public static String MAX_JUGADAS = "max_jugadas";
	public static String GUARDAR_MOVIMIENTOS = "save_movements";
	
	public static final int DEFAULT_BOUNDS = 10;
	public static final int DEFAULT_TRIES = 50;
	public static final int DEFAULT_NUMBER_BOATS = 5;
	public static final boolean DEFAULT_SAVE_MOVS = true;
	
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
	/**
	 * 
	 * @return int[] of 4 cells where :<br>
	 * int[0] == Number of boats<br>
	 * int[1] == Max tries<br>
	 * int[2] == Bounds of the board<br>
	 * int[3] == Can save the movs? 1 represents true, 0 false.<br>
	 * 
	 * If the method returns a cell with -1 the option is unchanged.
	 */
	protected int[] selectOptions() {
		int[] opciones = new int[] {-1,-1,-1,-1};
		System.out.println("Que deseas modificar?");
		System.out.println("\t1- Todos, salvo el de guardar.");
		System.out.println("\t2- Numero de barcos = "+getCurrentBoats());
		System.out.println("\t3- Numero de jugadas maximas = "+ getCurrentMaxJugadas());
		System.out.println("\t4- Tamaño del tablero = "+getCurrentBounds());
		System.out.println("\t5- Guardar movimientos? Actual: "+getCurrentGuardarMovimientos());
		switch(Leer.leerEntero("Selecciona: ",6)-1) {
		case 1: 
			opciones[0] = Leer.leerEntero("Seleccion nuevo numero de barcos: ", getCurrentBounds()/2);
			break;
		case 2: 
			opciones[1] = Leer.leerEntero("Seleccion nuevo maximo jugadas: ", getCurrentBoats()*5,getCurrentBounds()/2+1);
			break;
		case 3:
			opciones[2] = Leer.leerEntero("Seleccion nuevo tamaño de tablero: ", 5, 1000);
			break;
		case 4:
			if(getCurrentGuardarMovimientos()) {
				opciones[3] = 0;
			}
			else { 
				opciones[3] = 1;
				}
			break;
		default:
			opciones[2] = Leer.leerEntero("Seleccion nuevo tamaño de tablero: ", 5, 100);
			opciones[0] = Leer.leerEntero("Seleccion nuevo numero de barcos: ", 1 ,getCurrentBounds()/2+1);
			opciones[1] = Leer.leerEntero("Seleccion nuevo maximo jugadas: ", getCurrentBoats()*5,getCurrentBounds()*getCurrentBounds()/2+1);
		}
		return opciones;
	}
	/**
	 * 
	 * @param arr
	 * @return the number selected or NULL if none.
	 */
	protected final Integer getMaxTries(int[] arr) {
		if(arr[1] > -1) {
			return arr[0];
		}
		else {
			return null;
		}
	}
	/**
	 * 
	 * @param arr
	 * @return the number selected or NULL if none.
	 */
	protected final Integer getBounds(int[] arr) {
		if(arr[2] > -1) {
			return arr[0];
		}
		else {
			return null;
		}
	}
	/**
	 * 
	 * @param arr
	 * @return the number selected or NULL if none.
	 */
	protected final Integer getNBoats(int[] arr) {
		if(arr[0] > -1) {
			return arr[0];
		}
		else {
			return null;
		}
	}
	/**
	 * 
	 * @param arr
	 * @return the boolean selected or NULL if none.
	 */
	protected final Boolean getSave(int[] arr) {
		if(arr[3] == 1) {
			return true;
		} else if(arr[3] == 0) {
			return false;
		} else {
			return null;
		}
	}
	
}
