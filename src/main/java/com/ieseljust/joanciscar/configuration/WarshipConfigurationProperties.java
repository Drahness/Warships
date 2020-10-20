package com.ieseljust.joanciscar.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import Utils.Leer;

public class WarshipConfigurationProperties extends WarshipAbstractFileConfiguration {
	private static WarshipConfigurationProperties instance;
	public Properties configuration;
	
	private WarshipConfigurationProperties(File f) {
		super(f);
		setDefault();
	}
	private WarshipConfigurationProperties(String file) {
		this(new File(file));
	}
	
	private WarshipConfigurationProperties() {
		this("warship.properties");
	}
	public void setDefault() {
		configuration = new Properties();
		configuration.put(FlagsConfiguration.FLAG_NUMERO_BOATS, Integer.toString(DEFAULT_NUMBER_BOATS));
		configuration.put(FlagsConfiguration.FLAG_MAX_JUGADAS, Integer.toString(DEFAULT_TRIES));
		configuration.put(FlagsConfiguration.FLAG_TAMAÑO_BOARD, Integer.toString(DEFAULT_BOUNDS));
		configuration.put(FlagsConfiguration.FLAG_GUARDAR_MOVIMIENTOS, Boolean.toString(DEFAULT_SAVE_MOVS));
	}

	public static WarshipConfigurationProperties getInstance() {
		if(instance == null) {
			instance = new WarshipConfigurationProperties();
		}
		return instance;
	}
	
	public static WarshipConfigurationProperties getInstance(String file) {
		if(instance == null) {
			instance = new WarshipConfigurationProperties(file);
		}
		return instance;
	}
	
	public static WarshipConfigurationProperties getInstance(File f) {
		if(instance == null) {
			instance = new WarshipConfigurationProperties(f);
		}
		return instance;
	}
	
	public void loadConfiguration() throws FileNotFoundException, IOException {
		configuration.load(new FileInputStream(getFileConf()));
	}
	
	public void saveConfiguration() throws FileNotFoundException, IOException {
		configuration.store(new FileOutputStream(getFileConf()), String.format("Configuracion creada el %s",new Date()));
	}
	public void set() {
		System.out.println("Que deseas modificar?");
		System.out.println("\t1- Todos, salvo el de guardar.");
		System.out.println("\t2- Numero de barcos = "+getCurrentBoats());
		System.out.println("\t3- Numero de jugadas maximas = "+ getCurrentMaxJugadas());
		System.out.println("\t4- Tamaño del tablero = "+getCurrentBounds());
		System.out.println("\t5- Guardar movimientos? Actual: "+getCurrentGuardarMovimientos());
		switch(Leer.leerEntero("Selecciona: ",6)-1) {
		case 1: 
			configuration.put(FlagsConfiguration.FLAG_NUMERO_BOATS, Integer.toString(Leer.leerEntero("Seleccion nuevo numero de barcos: ", getCurrentBounds()/2)));
			break;
		case 2: 
			configuration.put(FlagsConfiguration.FLAG_MAX_JUGADAS, Integer.toString(Leer.leerEntero("Seleccion nuevo maximo jugadas: ", getCurrentBoats()*5,getCurrentBounds()/2+1)));
			break;
		case 3:
			configuration.put(FlagsConfiguration.FLAG_TAMAÑO_BOARD, Integer.toString(Leer.leerEntero("Seleccion nuevo tamaño de tablero: ", 5, 1000)));
			break;
		case 4:
			configuration.put(FlagsConfiguration.FLAG_GUARDAR_MOVIMIENTOS, Boolean.toString(!getCurrentGuardarMovimientos()));
			break;
		default:
			configuration.put(FlagsConfiguration.FLAG_TAMAÑO_BOARD, Integer.toString(Leer.leerEntero("Seleccion nuevo tamaño de tablero: ", 5, 100)));
			configuration.put(FlagsConfiguration.FLAG_NUMERO_BOATS, Integer.toString(Leer.leerEntero("Seleccion nuevo numero de barcos: ", 1 ,getCurrentBounds()/2+1)));
			configuration.put(FlagsConfiguration.FLAG_MAX_JUGADAS, Integer.toString(Leer.leerEntero("Seleccion nuevo maximo jugadas: ", getCurrentBoats()*5,getCurrentBounds()*getCurrentBounds()/2+1)));
		}
		if(Leer.leerBoolean("¿Quieres seguir configurando? S/N: ")) {
			set();
		}
	}
	
	public int getCurrentBounds() {
		return Integer.parseInt((String) configuration.get(FlagsConfiguration.FLAG_TAMAÑO_BOARD));
	}
	public int getCurrentBoats() {
		return Integer.parseInt((String) configuration.get(FlagsConfiguration.FLAG_NUMERO_BOATS));
	}
	public int getCurrentMaxJugadas() {
		return Integer.parseInt((String) configuration.get(FlagsConfiguration.FLAG_MAX_JUGADAS));
	}
	public boolean getCurrentGuardarMovimientos() {
		return Boolean.parseBoolean((String) configuration.get(FlagsConfiguration.FLAG_GUARDAR_MOVIMIENTOS));
	}

 }
