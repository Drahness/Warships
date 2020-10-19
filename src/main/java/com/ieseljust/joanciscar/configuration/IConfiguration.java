package com.ieseljust.joanciscar.configuration;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public interface IConfiguration {
	public static String TAMAÑO_BOARD = "board_tam";
	public static String NUMERO_BOATS = "num_boats";
	public static String MAX_JUGADAS = "max_jugadas";
	public static String GUARDAR_MOVIMIENTOS = "save_movements";
	
	public static final int DEFAULT_BOUNDS = 10;
	public static final int DEFAULT_TRIES = 50;
	public static final int DEFAULT_NUMBER_BOATS = 5;
	public static final boolean DEFAULT_SAVE_MOVS = true;
	
	public void setDefault();
	public void loadConfiguration() throws SAXException, IOException, ParserConfigurationException ;
	public void saveConfiguration() throws FileNotFoundException, IOException, TransformerException, SAXException, ParserConfigurationException;
	/**
	 * With user interaction.
	 */
	public void set();
	public int getCurrentBounds();
	public int getCurrentBoats();
	public int getCurrentMaxJugadas();
	public boolean getCurrentGuardarMovimientos();
	public void put(String key, String value);
}
