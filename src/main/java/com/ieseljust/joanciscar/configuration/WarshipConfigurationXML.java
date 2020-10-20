package com.ieseljust.joanciscar.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ieseljust.joanciscar.Movement;
import com.ieseljust.joanciscar.io.ILoader;
import com.ieseljust.joanciscar.io.IPersistor;

import Utils.Leer;
import pkg2020_ad_p1_warship.Board;
import pkg2020_ad_p1_warship.Boat;

public class WarshipConfigurationXML extends WarshipAbstractFileConfiguration implements ILoader, IPersistor {
	private static WarshipConfigurationXML instance;
	private Element configuracion;
	private Element element_moves;
	private Element element_boats;
	private Element element_root;
	private List<Movement> movementsList;
	private Document documentXML;

	public static WarshipConfigurationXML getInstance() {
		if (instance == null) {
			instance = new WarshipConfigurationXML();
		}
		return instance;
	}

	public static WarshipConfigurationXML getInstance(String file) {
		if (instance == null) {
			instance = new WarshipConfigurationXML(file);
		}
		return instance;
	}

	public static WarshipConfigurationXML getInstance(File f) {
		if (instance == null) {
			instance = new WarshipConfigurationXML(f);
		}
		return instance;
	}

	public WarshipConfigurationXML(File file) {
		super(file);
		setDefault();
	}

	private WarshipConfigurationXML(String file) {
		this(new File(file));
	}

	private WarshipConfigurationXML() {
		this("warship.xml");
	}

	@Override
	public void setDefault() {
		try {
			documentXML = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			element_root = documentXML.createElement("Warships");
			documentXML.appendChild(element_root);
			
			
			
			configuracion = documentXML.createElement("Config");
			element_moves = documentXML.createElement("Moves"); // Estos dos ultimos vacios. Solo cargas si se pide al usuario.
			element_boats = documentXML.createElement("Boats");

			element_root.appendChild(configuracion); // TODO hacerlo para que lo ponga cuando se vaya a guardar?
			element_root.appendChild(element_moves);
			element_root.appendChild(element_boats);

			Element element_guardar_mov = documentXML.createElement(FlagsConfiguration.FLAG_GUARDAR_MOVIMIENTOS);
			element_guardar_mov.appendChild(documentXML.createTextNode(Boolean.toString(DEFAULT_SAVE_MOVS)));
			
			Element element_max = documentXML.createElement(FlagsConfiguration.FLAG_MAX_JUGADAS);
			element_max.appendChild(documentXML.createTextNode(Integer.toString(DEFAULT_TRIES)));
			
			Element element_nboats = documentXML.createElement(FlagsConfiguration.FLAG_NUMERO_BOATS);
			element_nboats.appendChild(documentXML.createTextNode(Integer.toString(DEFAULT_NUMBER_BOATS)));
			
			Element element_bounds = documentXML.createElement(FlagsConfiguration.FLAG_TAMAÑO_BOARD);
			element_bounds.appendChild(documentXML.createTextNode(Integer.toString(DEFAULT_BOUNDS)));

			configuracion.appendChild(element_guardar_mov);
			configuracion.appendChild(element_max);
			configuracion.appendChild(element_nboats);
			configuracion.appendChild(element_bounds);
			
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace(); // Creo que nunca llegara aqui, no?
			System.out.println("QUE HAS HECHO JOAN.");
		}

	}

	@Override
	public void loadConfiguration() throws SAXException, IOException, ParserConfigurationException {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(getFileConf());
		String savemov = doc.getElementsByTagName(FlagsConfiguration.FLAG_GUARDAR_MOVIMIENTOS).item(0).getTextContent();
		String nboats = doc.getElementsByTagName(FlagsConfiguration.FLAG_NUMERO_BOATS).item(0).getTextContent();
		String max = doc.getElementsByTagName(FlagsConfiguration.FLAG_MAX_JUGADAS).item(0).getTextContent();
		String bounds = doc.getElementsByTagName(FlagsConfiguration.FLAG_TAMAÑO_BOARD).item(0).getTextContent();
		
		
		configuracion.getElementsByTagName(FlagsConfiguration.FLAG_GUARDAR_MOVIMIENTOS).item(0).setTextContent(savemov);
		configuracion.getElementsByTagName(FlagsConfiguration.FLAG_TAMAÑO_BOARD).item(0).setTextContent(bounds);
		configuracion.getElementsByTagName(FlagsConfiguration.FLAG_MAX_JUGADAS).item(0).setTextContent(max);
		configuracion.getElementsByTagName(FlagsConfiguration.FLAG_NUMERO_BOATS).item(0).setTextContent(nboats);	
	}

	@Override
	public void saveConfiguration() throws TransformerException, IOException, SAXException, ParserConfigurationException {
		saveToXML();

	}
	@Override
	public void set() {
		int[] opciones = selectOptions();
		String opcion;
		if(this.getMaxTries(opciones) != null) {
			opcion = String.valueOf(this.getMaxTries(opciones));
			configuracion.getElementsByTagName(FlagsConfiguration.FLAG_MAX_JUGADAS).item(0).setTextContent(opcion);
		}
		if(this.getSave(opciones) != null) {
			opcion = String.valueOf(this.getSave(opciones));
			configuracion.getElementsByTagName(FlagsConfiguration.FLAG_GUARDAR_MOVIMIENTOS).item(0).setTextContent(opcion);
		}
		if(this.getBounds(opciones) != null) {
			opcion = String.valueOf(this.getBounds(opciones));
			configuracion.getElementsByTagName(FlagsConfiguration.FLAG_TAMAÑO_BOARD).item(0).setTextContent(opcion);
		}
		if(this.getNBoats(opciones) != null) {
			opcion = String.valueOf(this.getNBoats(opciones));
			configuracion.getElementsByTagName(FlagsConfiguration.FLAG_NUMERO_BOATS).item(0).setTextContent(opcion);
		}
		if(Leer.leerBoolean("Quieres seguir configurando?")) {
			set();
		}
	}

	@Override
	public int getCurrentBounds() {
		return Integer.valueOf(configuracion.getElementsByTagName(FlagsConfiguration.FLAG_TAMAÑO_BOARD).item(0).getTextContent());
	}

	@Override
	public int getCurrentBoats() {
		return Integer.valueOf(configuracion.getElementsByTagName(FlagsConfiguration.FLAG_NUMERO_BOATS).item(0).getTextContent());
	}

	@Override
	public int getCurrentMaxJugadas() {
		return Integer.valueOf(configuracion.getElementsByTagName(FlagsConfiguration.FLAG_MAX_JUGADAS).item(0).getTextContent());
	}

	@Override
	public boolean getCurrentGuardarMovimientos() {
		return Boolean.valueOf(configuracion.getElementsByTagName(FlagsConfiguration.FLAG_GUARDAR_MOVIMIENTOS).item(0).getTextContent());
	}

	@Override
	public void saveBoats(Board b) throws TransformerException, IOException, SAXException, ParserConfigurationException {
		element_root.appendChild(element_boats);
		Boat[] boats = b.getBoats();
		
		for (int i = 0; i < boats.length; i++) {
			Element boat = documentXML.createElement(FlagsBoats.FLAG_BOAT);
			Element dimension = documentXML.createElement(FlagsBoats.FLAG_DIMENSION);
			Element posicion= documentXML.createElement(FlagsBoats.FLAG_POSICION);
			Element orientacion = documentXML.createElement(FlagsBoats.FLAG_ORIENTACION);
			
			dimension.setTextContent(boats[i].getDimension()+"");
			orientacion.setTextContent(boats[i].getDireccion()+"");
			boat.setAttribute(FLAG_ATT_ID, String.valueOf(i));
			posicion.setAttribute(FlagsBoats.FLAG_ATT_COL_POSICION, ""+boats[i].getColumnapos());
			posicion.setAttribute(FlagsBoats.FLAG_ATT_ROW_POSICION, ""+ boats[i].getFilapos());
			
			boat.appendChild(dimension);
			boat.appendChild(orientacion);
			boat.appendChild(posicion);
			
			element_boats.appendChild(boat);
		}
		
		saveToXML();
	}

	@Override
	public void saveMovements() throws TransformerException, IOException, SAXException, ParserConfigurationException {
		saveToXML();
	}

	@Override
	public void registerMovement(Movement m) {
		for (int i = 0 ; i < movementsList.size(); i++ ) {
			if(movementsList.get(i).equals(m)) {
				movementsList.add(i, m);
			}
		}
		Element move = documentXML.createElement(FlagsMovements.FLAG_MOVE);
		move.setAttribute(FLAG_ATT_ID, String.valueOf(m.getId()));
		
		Element row = documentXML.createElement(FlagsMovements.FLAG_ROW);
		row.appendChild(documentXML.createTextNode(String.valueOf(m.getFila())));
		
		Element col = documentXML.createElement(FlagsMovements.FLAG_COLUMN);
		col.appendChild(documentXML.createTextNode(String.valueOf(m.getColumna())));
		
		Element result = documentXML.createElement(FlagsMovements.FLAG_RESULT);
		result.appendChild(documentXML.createTextNode(String.valueOf(m.getResultado())));
		
		move.appendChild(row);
		move.appendChild(col);
		move.appendChild(result);
		
		documentXML.getElementsByTagName(FlagsMovements.FLAG_ROOT).item(0).appendChild(move);
	}

	@Override
	public void resetMovs() {
		Node moves = documentXML.getElementsByTagName(FlagsMovements.FLAG_ROOT).item(0);
		/*for (int i = 0; i < list.getLength(); i++) {
			element_moves.removeChild(list.item(i));
		}*/
		Node parent = moves.getParentNode();
		parent.removeChild(moves);
		element_moves = documentXML.createElement(FlagsMovements.FLAG_ROOT);
		parent.appendChild(element_moves);

	}

	@Override
	public Movement[] loadMovements() throws SAXException, IOException, ParserConfigurationException, FileNotFoundException {
		if (getFileConf().exists() ) {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(getFileConf());
			
			NodeList moves = doc.getElementsByTagName(FlagsMovements.FLAG_MOVE);
			Movement[] movements = new Movement[moves.getLength()];
			
			NodeList columns = doc.getElementsByTagName(FlagsMovements.FLAG_COLUMN);
			NodeList rows = doc.getElementsByTagName(FlagsMovements.FLAG_ROW);
			NodeList results = doc.getElementsByTagName(FlagsMovements.FLAG_RESULT);
			
			
			for (int i = 0; i < movements.length; i++) {	
				int id = Integer.parseInt(moves.item(i).getAttributes().getNamedItem(FLAG_ATT_ID).getTextContent());
				int col = Integer.parseInt(columns.item(i).getTextContent());
				int row = Integer.parseInt(rows.item(i).getTextContent());
				int result = Integer.parseInt(results.item(i).getTextContent());
				movements[i] = new Movement(id, col, row, result);
			}
			
			return movements;
		}
		else {
			throw new FileNotFoundException();
		}
	}

	@Override
	public Boat[] loadBoats() throws SAXException, IOException, ParserConfigurationException {
		if(getFileConf().exists()) {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(getFileConf());
			
			NodeList nodes_boats = doc.getElementsByTagName(FlagsBoats.FLAG_BOAT);
			Boat[] boats = new Boat[nodes_boats.getLength()];
			
			NodeList list_dimension = doc.getElementsByTagName(FlagsBoats.FLAG_DIMENSION);
			NodeList list_posicion = doc.getElementsByTagName(FlagsBoats.FLAG_POSICION);
			NodeList list_orientacion = doc.getElementsByTagName(FlagsBoats.FLAG_ORIENTACION);
			
			for (int i = 0; i < boats.length; i++) {
				int id = Integer.parseInt(nodes_boats.item(i).getAttributes().getNamedItem(FLAG_ATT_ID).getTextContent());
				int row = Integer.parseInt(list_posicion.item(i).getAttributes().getNamedItem(FlagsBoats.FLAG_ATT_ROW_POSICION).getTextContent());
				int col = Integer.parseInt(list_posicion.item(i).getAttributes().getNamedItem(FlagsBoats.FLAG_ATT_COL_POSICION).getTextContent());
				int orientacion = Integer.parseInt(list_orientacion.item(i).getTextContent());
				int dimension = Integer.parseInt(list_dimension.item(i).getTextContent());
				boats[i] = new Boat(dimension,orientacion,row,col,id);
			}
			return boats;
		}
		else {
			throw new FileNotFoundException();
		}
	}

	@Override
	public Board loadBoardSafe() throws SAXException, IOException, ParserConfigurationException, FileNotFoundException {
		Boat[] boats = this.loadBoats();
		int newBounds = Boat.getMaxCoordOfBoats(boats);
		put(FlagsConfiguration.FLAG_TAMAÑO_BOARD, newBounds);
		Board board = new Board(boats);
		return board;
	}

	private void saveToXML() throws TransformerException, FileNotFoundException, IOException, SAXException, ParserConfigurationException {
		DOMSource source;
		source = new DOMSource(documentXML);
		StreamResult result = new StreamResult(new FileOutputStream(getFileConf()));
		Transformer trans = TransformerFactory.newInstance().newTransformer();
		trans.transform(source, result);
	}
	/**
	 * This is only for UNIQUE KEYS and ATTACHED TO THE DOM
	 */
	public void put(String key, String value) {
		put(key,value,0);
	}
	
	private void put(String key, String value, int i) {
		element_root.getElementsByTagName(key).item(i).setTextContent(value);
	}
	
	private void put(String key, int value, int i) {
		element_root.getElementsByTagName(key).item(i).setTextContent(String.valueOf(value));
	}
	private void put(String key, int value) {
		put(key,value,0);
	}
}
