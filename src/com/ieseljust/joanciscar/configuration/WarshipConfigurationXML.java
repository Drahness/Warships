package com.ieseljust.joanciscar.configuration;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

public class WarshipConfigurationXML extends WarshipAbstractFileConfiguration {
	private static WarshipConfigurationXML instance;
	
	private Document dom;
	public static WarshipConfigurationXML getInstance() {
		if(instance == null) {
			instance = new WarshipConfigurationXML();
		}
		return instance;
	}
	
	public static WarshipConfigurationXML getInstance(String file) {
		if(instance == null) {
			instance = new WarshipConfigurationXML(file);
		}
		return instance;
	}
	
	public static WarshipConfigurationXML getInstance(File f) {
		if(instance == null) {
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
			dom.create
		}
		catch(ParserConfigurationException e) {
			e.printStackTrace(); // Creo que nunca llegara aqui, no?
			System.out.println("QUE HAS HECHO JOAN.");
		}
		
	}

	@Override
	public void load() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCurrentBounds() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentBoats() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentMaxJugadas() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getCurrentGuardarMovimientos() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
