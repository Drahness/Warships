package com.ieseljust.joanciscar.configuration;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ieseljust.joanciscar.Movement;
import com.ieseljust.joanciscar.io.ILoader;
import com.ieseljust.joanciscar.io.IPersistor;

import Utils.Leer;
import pkg2020_ad_p1_warship.Board;
import pkg2020_ad_p1_warship.Boat;

public class WarshipConfigurationXML extends WarshipAbstractFileConfiguration implements ILoader, IPersistor {
	private static WarshipConfigurationXML instance;
	private Element configuracion;
	private Element moves;
	private Element boats;
	private Element root;

	
	private Element guardar_mov ;
	private Element max ;
	private Element nboats ;
	private Element bounds ;
	
	private Document dom;

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
			dom = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			root = dom.createElement("Warships");

			configuracion = dom.createElement("Config");
			moves = dom.createElement("Moves"); // Estos dos ultimos vacios. Solo cargas si se pide al usuario.
			boats = dom.createElement("Boats");

			root.appendChild(configuracion);
			root.appendChild(moves);
			root.appendChild(boats);

			guardar_mov = dom.createElement(GUARDAR_MOVIMIENTOS);
			guardar_mov.appendChild(dom.createTextNode(Boolean.toString(DEFAULT_SAVE_MOVS)));
			max = dom.createElement(MAX_JUGADAS);
			max.appendChild(dom.createTextNode(Integer.toString(DEFAULT_TRIES)));
			nboats = dom.createElement(NUMERO_BOATS);
			nboats.appendChild(dom.createTextNode(Integer.toString(DEFAULT_NUMBER_BOATS)));
			bounds = dom.createElement(TAMAÑO_BOARD);
			bounds.appendChild(dom.createTextNode(Integer.toString(DEFAULT_BOUNDS)));

			configuracion.appendChild(guardar_mov);
			configuracion.appendChild(max);
			configuracion.appendChild(nboats);
			configuracion.appendChild(bounds);
			
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace(); // Creo que nunca llegara aqui, no?
			System.out.println("QUE HAS HECHO JOAN.");
		}

	}

	@Override
	public void loadConfiguration() throws Exception {
		var doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(getFileConf());
		// TODO
		
	}

	@Override
	public void saveConfiguration() throws Exception {
		// TODO Auto-generated method stub

	}
	@Override
	public void set() {
		int[] opciones = selectOptions();
		String opcion;
		if(this.getMaxTries(opciones) != null) {
			opcion = String.valueOf(this.getMaxTries(opciones));
			max.appendChild(dom.createTextNode(opcion));
		}
		if(this.getSave(opciones) != null) {
			opcion = String.valueOf(this.getSave(opciones));
			guardar_mov.appendChild(dom.createTextNode(opcion));
		}
		if(this.getBounds(opciones) != null) {
			opcion = String.valueOf(this.getBounds(opciones));
			bounds.appendChild(dom.createTextNode(opcion));
		}
		if(this.getNBoats(opciones) != null) {
			opcion = String.valueOf(this.getNBoats(opciones));
			nboats.appendChild(dom.createTextNode(opcion));
		}
		if(Leer.leerBoolean("Quieres seguir configurando?")) {
			set();
		}
	}

	@Override
	public int getCurrentBounds() {
		return Integer.valueOf(bounds.getTextContent());
	}

	@Override
	public int getCurrentBoats() {
		return Integer.valueOf(nboats.getTextContent());
	}

	@Override
	public int getCurrentMaxJugadas() {
		return Integer.valueOf(max.getTextContent());
	}

	@Override
	public boolean getCurrentGuardarMovimientos() {
		return Boolean.valueOf(guardar_mov.getTextContent());
	}

	@Override
	public void saveBoats(Board b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveMovements() {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerMovement(Movement m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetMovs() {
		// TODO Auto-generated method stub

	}

	@Override
	public Movement[] loadMovements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boat[] loadBoats() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boat[] loadBoardSafe() {
		// TODO Auto-generated method stub
		return null;
	}

}
