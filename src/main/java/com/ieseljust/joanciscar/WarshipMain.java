package com.ieseljust.joanciscar;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.ieseljust.joanciscar.configuration.IConfiguration;
import com.ieseljust.joanciscar.configuration.WarshipConfigurationXML;
import com.ieseljust.joanciscar.io.ILoader;
import com.ieseljust.joanciscar.io.IPersistor;
import Utils.ConsoleColors;
import Utils.Leer;
import pkg2020_ad_p1_warship.Board;
import pkg2020_ad_p1_warship.Cell;

public class WarshipMain {
	/*public static final WarshipConfigurationProperties configuration = WarshipConfigurationProperties.getInstance();
	public static final WarshipLoader loader = WarshipLoader.getInstance();
	public static final WarshipPersistor persistor = WarshipPersistor.getInstance();*/
	
	
	public static final IConfiguration configuration = WarshipConfigurationXML.getInstance();
	public static final ILoader loader = WarshipConfigurationXML.getInstance();
	public static final IPersistor persistor = WarshipConfigurationXML.getInstance();
	
	public static Board board;
	public static IWarshipInput input;
	
	public static void main(String[] args) throws IOException {
		
		while(true) {
			menuPrincipal();
		}
	}
	public static void menuPrincipal() {
		System.out.println("Juego Warships:");
		System.out.println("\t1. Configuraciones:");
		System.out.println("\t2. Tablero:");
		System.out.println("\t3. Jugar:");
		System.out.println("\t4. Salir:");
		int opcio = Leer.leerEntero("Seleciona: ",5)-1;
		switch(opcio) {
		case 0:
			menuConfiguraciones();
			break;
		case 1:
			menuTauler();
			break;
		case 2:
			menuJugar();
			break;
		default:
			System.exit(0);
			break;
		}
	}
	public static void menuTauler() {
		System.out.println("Tablero:");
		System.out.println("\t1. Nuevo");
		System.out.println("\t2. Cargar");
		System.out.println("\t3. Guardar");
		int opcio = Leer.leerEntero("Seleciona: ",4)-1;
		switch(opcio) {
		case 0:
			board = new Board();
			break;
		case 1:
			try {
				try {
					board = new Board(loader.loadBoats());
				} catch (SAXException | ParserConfigurationException e) {
					System.out.println("Error al parsear el fichero.");
					e.printStackTrace();
				}
			} catch (NumberFormatException e) {
				System.out.println(ConsoleColors.printError("Fichero mal-formado."));
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println(ConsoleColors.printError("Error al leer el fichero."));
				e.printStackTrace();
			} catch(IllegalArgumentException e) {
				System.out.println(ConsoleColors.printError("El tamaño por defecto no permite este guardado de barcos, quieres crear un tablero que encaje con el guardado?"));
				if(Leer.leerBoolean("Si o no: ")) {
					try {
						try {
							board = loader.loadBoardSafe();
						} catch (SAXException | ParserConfigurationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (IOException | NumberFormatException e1 ) {
						System.out.println(ConsoleColors.printError("Error al leer el fichero."));
					}
				}
			}
			break;
		default:
			try {
				try {
					persistor.saveBoats(board);
				} catch (SAXException | ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			} catch (IOException | TransformerException e) {
				System.out.println("Error al guardar el fichero.");
				e.printStackTrace();
			}
			break;
		}
	}
	public static void menuConfiguraciones() {
		System.out.println("Configuraciones:");
		System.out.println("\t1. Consultar");
		System.out.println("\t2. Configurar");
		System.out.println("\t3. Guardar");
		System.out.println("\t4. Cargar");
		int opcio = Leer.leerEntero("Selecciona: ", 5)-1;
		switch (opcio) {
		case 0:
			System.out.println(configuration);
			break;
		case 1:
			configuration.set();
			break;
		case 2:
			try {
				try {
					configuration.saveConfiguration();
				} catch (SAXException | ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException | TransformerException e1) {
				System.out.println(ConsoleColors.printError("Error al guardar el fichero de propiedades."));
				e1.printStackTrace();
			}
			break;
		case 3:
			try {
				try {
					configuration.loadConfiguration();
				} catch (SAXException | ParserConfigurationException e) {
				
				}
			} catch (IOException e) {
				System.out.println(ConsoleColors.printError("Error al cargar el fichero de configuracion."));
			}
			break;
		default:
			
			break;
		}
	}
	public static void menuJugar() {
		System.out.println("Jugar:");
		try {
			input = new InputFactory().create();
			mainWarships();
		} catch (NumberFormatException e) {
			System.out.println(ConsoleColors.printError("Fichero de movimientos mal-formado."));
		} catch(FileNotFoundException e) {
			System.out.println(ConsoleColors.printError("El fichero no existe."));
		} catch (IOException e) {
			System.out.println(ConsoleColors.printError("No se ha podido leer el fichero."));
		} catch (SAXException e) {
			// Nunca llegara aqui por:  
			// This class can contain basic error or warning
			// information fromeither the XML parser or the application: a parser writer or application writer 
			// can subclass it to provide additional functionality. SAX handlers may throw this exception 
			// or any exception subclassed from it.
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			System.out.println("QUE HAS HECHO JOAN");
			e.printStackTrace();
		}
	}
	public static void mainWarships() {
		if(board != null) {
			board.initBoats();
			input.paint(board);
			Movement movement;
			persistor.resetMovs();
			int jugadas = 0;
			boolean seguir = true;
			do {
				System.out.println("JUGADA: " + ++jugadas);
				int columna = input.getCoord();
				if(columna == -1) {
					break;
				}
				int fila = input.getCoord();
				if(fila == -1) {
					break;
				}
				input = input.returnInput();
				int resultado;
				if ((resultado = board.shot(fila, columna)) != Cell.CELL_WATER) {
					input.paint(board);
					
				} else {
					System.out.println("(" + fila + "," + columna + ") --> AGUA");
				}
				movement = new Movement(jugadas,fila,columna, resultado);
				if(input.needsToBeStored()) {
					persistor.registerMovement(movement);
				}
				if(board.getEnd_Game() && !input.isAI()) {
					System.out.println(ConsoleColors.printString("¡Has ganado!", ConsoleColors.GREEN_BOLD_BRIGHT));
					seguir = false;
				}
				if(input.isAI() && !(jugadas < configuration.getCurrentMaxJugadas())) {
					seguir = false;
				}
			} while(seguir);
			
			
			if(configuration.getCurrentGuardarMovimientos()) {
				try {
					try {
						persistor.saveMovements();
					} catch (SAXException | ParserConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IOException | TransformerException e) {
					System.out.println(ConsoleColors.printError("Ha habido un error guardando el fichero de movimientos."));
					e.printStackTrace();
				}
			}
		}
		else {
			System.out.println(ConsoleColors.printError("Crea un tablero nuevo."));
		}
	}
}
