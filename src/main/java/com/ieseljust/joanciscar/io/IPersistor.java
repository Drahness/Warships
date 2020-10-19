package com.ieseljust.joanciscar.io;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.ieseljust.joanciscar.Movement;

import pkg2020_ad_p1_warship.Board;

public interface IPersistor {
	public void saveBoats(Board b) throws IOException, TransformerException, SAXException, ParserConfigurationException;
	public void saveMovements() throws IOException, TransformerException, SAXException, ParserConfigurationException;
	public void registerMovement(Movement m) ;
	public void resetMovs(); 
}
