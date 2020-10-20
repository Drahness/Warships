package com.ieseljust.joanciscar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ieseljust.joanciscar.configuration.WarshipConfigurationXML;
import com.ieseljust.joanciscar.configuration.WarshipAbstractFileConfiguration.FlagsBoats;
import com.ieseljust.joanciscar.configuration.WarshipAbstractFileConfiguration.FlagsMovements;

import Utils.Leer;
import pkg2020_ad_p1_warship.Boat;

public class Consultor {
	public WarshipConfigurationXML configXML;
	private String filename;
	
	public Consultor(String fileName) {
		this.filename = fileName;
	}
	
	public void loopPrintInfo() {
		while(true) {
			String consulta = Leer.leerTexto("Inserta tu consulta\n\tB = Barco \n\tM = Movimiento\n\tEjemploM24/B3\n0 para salir. Selecciona:");
			if(consulta.startsWith("0")) {
				break;
			}
			if(consultaEsValida(consulta)) {
				loadFromXML(consulta);
			}
			
		}
	}
	private boolean consultaEsValida(String consulta) {
		if((isBoat(consulta) || isMovement(consulta)) && consulta.length() >= 2) {
			String n = consulta.substring(1, consulta.length());
			return n.matches("\\d+");
		}
		return false;
	}
	private boolean isBoat(String consulta) {
		return consulta.startsWith("B"); 
	}
	private boolean isMovement(String consulta) {
		return consulta.startsWith("M"); 
	}
	private String getIndex(String consulta) {
		return consulta.substring(1, consulta.length());
	}
	private void displayMovement(NodeList[] nodesLists, int i) {
		String fila, columna, resultado;
		fila = nodesLists[1].item(i).getTextContent();
		columna = nodesLists[0].item(i).getTextContent();
		resultado = nodesLists[2].item(i).getTextContent();
		
		
		StringBuilder sb = new StringBuilder();
		sb.append("Move:");
		sb.append('\n').append('\t');
		sb.append("Fila: ").append(fila);
		sb.append('\n').append('\t');
		sb.append("Columna: ").append(columna);
		sb.append('\n').append('\t');
		sb.append("Resultado: ").append(resultado);
		System.out.println(sb);
	}
	
	private void displayBoat(NodeList[] nodesLists, int i) {
		String fila, columna, dimension, direccion;
		direccion = nodesLists[2].item(i).getTextContent();
		fila = nodesLists[1].item(i).getAttributes().getNamedItem(FlagsBoats.FLAG_ATT_ROW_POSICION).getTextContent();
		columna = nodesLists[1].item(i).getAttributes().getNamedItem(FlagsBoats.FLAG_ATT_COL_POSICION).getTextContent();
		dimension = nodesLists[0].item(i).getTextContent();
		StringBuilder sb = new StringBuilder();
		sb.append("Boat");
		sb.append('\n').append('\t');
		sb.append("Fila: ").append(fila);
		sb.append('\n').append('\t');
		sb.append("Columna: ").append(columna);
		sb.append('\n').append('\t');
		sb.append("Dimension: ").append(dimension);
		sb.append('\n').append('\t');
		sb.append("Orientacion: ").append(direccion);
		System.out.println(sb);
	}
	
	public void loadFromXML(String consulta) {
		String nodeTag = null;
		String id = getIndex(consulta);
		if(isBoat(consulta)) {
			nodeTag = FlagsBoats.FLAG_BOAT;
		} else if(isMovement(consulta)) {
			nodeTag = FlagsMovements.FLAG_MOVE;
		}
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(filename);
			NodeList list = doc.getElementsByTagName(nodeTag);
			for (int i = 0; i < list.getLength(); i++) {
				if(list.item(i).getAttributes().item(0).getTextContent().equals(id)) {
					if(nodeTag.equals(FlagsMovements.FLAG_MOVE)) {
						NodeList lists[] = new NodeList[3];
						lists[0] = doc.getElementsByTagName(FlagsMovements.FLAG_COLUMN);
						lists[1] = doc.getElementsByTagName(FlagsMovements.FLAG_ROW);
						lists[2] = doc.getElementsByTagName(FlagsMovements.FLAG_RESULT);
						displayMovement(lists,i);
						break;
					}
					else if (nodeTag.equals(FlagsBoats.FLAG_BOAT)){
						NodeList lists[] = new NodeList[3];
						lists[0] = doc.getElementsByTagName(FlagsBoats.FLAG_DIMENSION);
						lists[1] = doc.getElementsByTagName(FlagsBoats.FLAG_POSICION);
						lists[2] = doc.getElementsByTagName(FlagsBoats.FLAG_ORIENTACION);
						displayBoat(lists,i);
						break;
					}
					
				}
			}
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
