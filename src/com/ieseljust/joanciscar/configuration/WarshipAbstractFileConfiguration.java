package com.ieseljust.joanciscar.configuration;

import java.io.File;

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
	
	
}
