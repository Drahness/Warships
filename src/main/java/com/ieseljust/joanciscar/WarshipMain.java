package com.ieseljust.joanciscar;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.json.JSONException;
import org.xml.sax.SAXException;

import com.ieseljust.joanciscar.configuration.IConfiguration;
import com.ieseljust.joanciscar.configuration.WarshipConfigurationJSON;
import com.ieseljust.joanciscar.configuration.WarshipConfigurationProperties;
import com.ieseljust.joanciscar.configuration.WarshipConfigurationXML;
import com.ieseljust.joanciscar.io.ILoader;
import com.ieseljust.joanciscar.io.IPersistor;
import com.ieseljust.joanciscar.io.WarshipLoader;
import com.ieseljust.joanciscar.io.WarshipPersistor;

import Utils.ConsoleColors;
import Utils.Leer;
import pkg2020_ad_p1_warship.Board;
import pkg2020_ad_p1_warship.Cell;

public class WarshipMain {
	/*public static final WarshipConfigurationProperties configuration = WarshipConfigurationProperties.getInstance();
	public static final WarshipLoader loader = WarshipLoader.getInstance();
	public static final WarshipPersistor persistor = WarshipPersistor.getInstance();*/
	
	
	public static IConfiguration configuration;
	public static ILoader loader;
	public static IPersistor persistor;
	
	public static Board board;
	public static IWarshipInput input;
	
	public static void main(String[] args) throws IOException {
		try {
			if(args[0].equalsIgnoreCase("xml")) {
				setXML();
			}
			else if(args[0].equalsIgnoreCase("file")) {
				setFILE();
			}
			else if(args[0].equalsIgnoreCase("json")) {
				setJSON();
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			setJSON();
		}
		while(true) {
			menuPrincipal();
		}
	}
	/**
	 * Modo JSON, leera y escribira en un JSON
	 */
	public static void setJSON() {
		configuration = WarshipConfigurationJSON.getInstance();
		loader = WarshipConfigurationJSON.getInstance();
		persistor = WarshipConfigurationJSON.getInstance();
	}
	/**
	 * Modo XML
	 */
	public static void setXML() {
		configuration = WarshipConfigurationXML.getInstance();
		loader = WarshipConfigurationXML.getInstance();
		persistor = WarshipConfigurationXML.getInstance();
	}
	/**
	 * Modo fichero, lee y escribe en DISTINTOS FICHEROS.
	 */
	public static void setFILE() {
		configuration = WarshipConfigurationProperties.getInstance();
		loader = WarshipLoader.getInstance();
		persistor = WarshipPersistor.getInstance();
	}
	public static void menuPrincipal() {
		System.out.println("Juego Warships:");
		System.out.println("\t1. Configuraciones:");
		System.out.println("\t2. Tablero:");
		System.out.println("\t3. Jugar:");
		System.out.println("\t4. Salir:");
		System.out.println("\t5. Consultar archivo clase_warships.xml:");
		int opcio = Leer.leerEntero("Seleciona: ",6)-1;
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
		case 4:
			new Consultor("clase_warships.xml").loopPrintInfo();
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
				board = new Board(loader.loadBoats());
			} catch (NumberFormatException e) {
				System.out.println(ConsoleColors.printError("Fichero mal-formado."));
				e.printStackTrace();
			} catch(IllegalArgumentException e) {
				System.out.println(ConsoleColors.printError("El tama�o por defecto no permite este guardado de barcos, quieres crear un tablero que encaje con el guardado?"));
				if(Leer.leerBoolean("Si o no: ")) {
					try {
						board = loader.loadBoardSafe();
					} catch (IOException | NumberFormatException e1 ) {
						System.out.println(ConsoleColors.printError("Error al leer el fichero."));
					} catch (SAXException | ParserConfigurationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} catch (IOException e) {
				System.out.println(ConsoleColors.printError("Error al leer el fichero."));
				e.printStackTrace();
			} catch (SAXException | ParserConfigurationException e) {
				System.out.println("Error al parsear el fichero.");
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			break;
		default:
			try {
				if(board != null) {
					persistor.saveBoats(board);
				}
				else {
					System.out.println(ConsoleColors.printError("Inicializa un tablero."));
				}
				
			} catch (IOException | TransformerException e) {
				System.out.println("Error al guardar el fichero.");
				//e.printStackTrace();
			} catch (SAXException | ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
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
				configuration.saveConfiguration();
			} catch (IOException | TransformerException e1) {
				System.out.println(ConsoleColors.printError("Error al guardar el fichero de propiedades."));
				e1.printStackTrace();
			} catch (SAXException | ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			try {
				configuration.loadConfiguration();
			} catch (IOException e) {
				System.out.println(ConsoleColors.printError("Error al cargar el fichero de configuracion."));
			} catch (SAXException | ParserConfigurationException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		} catch (JSONException e) {
			// TODO Auto-generated catch block
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
				boolean store = input.needsToBeStored();
				boolean ai = input.isAI();
				
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
				if(store) {
					persistor.registerMovement(movement);
				}
				if(board.getEnd_Game() && !ai) {
					System.out.println(ConsoleColors.printString("�Has ganado!", ConsoleColors.GREEN_BOLD_BRIGHT));
					seguir = false;
				}
				if(ai && !(jugadas < configuration.getCurrentMaxJugadas())) {
					seguir = false;
				}
			} while(seguir);
			
			
			if(configuration.getCurrentGuardarMovimientos()) {
				try {
					persistor.saveMovements();
				} catch (IOException | TransformerException e) {
					System.out.println(ConsoleColors.printError("Ha habido un error guardando el fichero de movimientos."));
					e.printStackTrace();
				} catch (SAXException | ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else {
			System.out.println(ConsoleColors.printError("Crea un tablero nuevo."));
		}
	}
}
