package com.ieseljust.joanciscar.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
import com.ieseljust.joanciscar.WarshipMain;
import com.ieseljust.joanciscar.io.ILoader;
import com.ieseljust.joanciscar.io.IPersistor;

import Utils.Leer;
import pkg2020_ad_p1_warship.Board;
import pkg2020_ad_p1_warship.Boat;

public class WarshipConfigurationXML extends WarshipAbstractFileConfiguration implements ILoader, IPersistor {
	private static WarshipConfigurationXML instance;
	private DOMSource source;
	private StreamResult result;
	private Transformer trans;
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
		try {
			documentXML = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private WarshipConfigurationXML(String file) {
		this(new File(file));
	}
	private WarshipConfigurationXML() {
		this("warship.xml");
	}

	@Override
	public void loadConfiguration() throws IOException {
		try {
			if(getFileConf().exists()) {
				Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(getFileConf());
				
				if(doc.getElementsByTagName(FlagsConfiguration.FLAG_ROOT).getLength() != 0) {
				
					boolean savemov = Boolean.parseBoolean(doc.getElementsByTagName(FlagsConfiguration.FLAG_GUARDAR_MOVIMIENTOS).item(0).getTextContent());
					int nboats = Integer.parseInt(doc.getElementsByTagName(FlagsConfiguration.FLAG_NUMERO_BOATS).item(0).getTextContent());
					int max = Integer.parseInt(doc.getElementsByTagName(FlagsConfiguration.FLAG_MAX_JUGADAS).item(0).getTextContent());
					int bounds = Integer.parseInt(doc.getElementsByTagName(FlagsConfiguration.FLAG_TAMAÑO_BOARD).item(0).getTextContent());
					
					setCurrentBoats(nboats);
					setCurrentBounds(bounds);
					setCurrentGuardarMovimientos(savemov);
					setCurrentMaxJugadas(max);
				}
				else {
					throw new IOException();
				}
			}
			else {
				throw new FileNotFoundException();
			}
			
		} catch(SAXException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveConfiguration() throws TransformerException, IOException {
		Element root =documentXML.createElement(FlagsConfiguration.FLAG_ROOT);
		Element max =documentXML.createElement(FlagsConfiguration.FLAG_MAX_JUGADAS);
		Element save =documentXML.createElement(FlagsConfiguration.FLAG_GUARDAR_MOVIMIENTOS);
		Element nboats =documentXML.createElement(FlagsConfiguration.FLAG_NUMERO_BOATS);
		Element bounds = documentXML.createElement(FlagsConfiguration.FLAG_TAMAÑO_BOARD);
		
		
		max.setTextContent(String.valueOf(getCurrentMaxJugadas()));
		nboats.setTextContent(String.valueOf(getCurrentBoats()));
		bounds.setTextContent(String.valueOf(getCurrentBounds()));
		save.setTextContent(String.valueOf(getCurrentGuardarMovimientos()));
		
		
		root.appendChild(max);
		root.appendChild(save);
		root.appendChild(nboats);
		root.appendChild(bounds);
		
		saveToXML(root);
		
	}

	@Override
	public void saveBoats(Board b) throws TransformerException, IOException, SAXException, ParserConfigurationException {
		Boat[] boats = b.getBoats();
		Element root = documentXML.createElement(FlagsBoats.FLAG_ROOT);
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
			root.appendChild(boat);
		}
		saveToXML(root);
	}

	@Override
	public void saveMovements() throws TransformerException, IOException, SAXException, ParserConfigurationException {
		Element movementsRoot = documentXML.createElement(FlagsMovements.FLAG_ROOT);
		List<Movement> movements = getMovements();
		for (Movement movement : movements) {
			Element move = documentXML.createElement(FlagsMovements.FLAG_MOVE);
			Element result = documentXML.createElement(FlagsMovements.FLAG_RESULT);
			Element column = documentXML.createElement(FlagsMovements.FLAG_COLUMN);
			Element row = documentXML.createElement(FlagsMovements.FLAG_ROW);
			
			move.setAttribute(FLAG_ATT_ID, String.valueOf(movement.getId()));
			
			result.setTextContent(String.valueOf(movement.getResultado()));
			column.setTextContent(String.valueOf(movement.getColumna()));
			row.setTextContent(String.valueOf(movement.getFila()));
			
			move.appendChild(result);
			move.appendChild(column);
			move.appendChild(row);
			
			movementsRoot.appendChild(move);
		}
		saveToXML(movementsRoot);
	}
	private Node getRoot() {
		Node root = documentXML.getFirstChild();
		if(root == null) {
			Element el = documentXML.createElement(FLAG_ROOT);
			root = documentXML.appendChild(el);
		}
		return root;
	}
	@Override
	public void registerMovement(Movement m) {
		if(getMovements().contains(m)) {
			for (int i = 0 ; i < getMovements().size(); i++ ) {
				if(!getMovements().get(i).equals(m)) {
					getMovements().add(i, m);
				}
			}
		}
		else {
			getMovements().add(m);
		}
	}

	@Override
	public void resetMovs() {}

	@Override
	public Movement[] loadMovements() throws SAXException, IOException, ParserConfigurationException, FileNotFoundException {
		if (getFileConf().exists() ) {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(getFileConf());
			if(doc.getElementsByTagName(FlagsMovements.FLAG_ROOT).item(0).getChildNodes().getLength() != 0) {
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
				throw new IOException();
			}
		}
		else {
			throw new FileNotFoundException();
		}
	}

	@Override
	public Boat[] loadBoats() throws SAXException, IOException, ParserConfigurationException {
		if(getFileConf().exists()) {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(getFileConf());
			if(doc.getElementsByTagName(FlagsBoats.FLAG_ROOT).item(0).getChildNodes().getLength() != 0) {
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
				throw new IOException();
			}
		}
		else {
			throw new FileNotFoundException();
		}
	}

	@Override
	public Board loadBoardSafe() throws SAXException, IOException, ParserConfigurationException, FileNotFoundException {
		Boat[] boats = this.loadBoats();
		int newBounds = Boat.getMaxCoordOfBoats(boats);
		WarshipMain.configuration.setCurrentBounds(newBounds);
		Board board = new Board(boats);
		return board;
	}
	
	private void saveToXML(Element toAppend) throws TransformerException, FileNotFoundException, IOException {
		Node node = documentXML.getElementsByTagName(toAppend.getTagName()).item(0);
		if(node != null) {
			node.getParentNode().removeChild(node);
		}
		getRoot().appendChild(toAppend);
		if(source == null) {
			source = new DOMSource(documentXML);
			trans = TransformerFactory.newInstance().newTransformer();
		}
		result = new StreamResult(new FileOutputStream(getFileConf()));
		trans.transform(source, result);
	}
}
