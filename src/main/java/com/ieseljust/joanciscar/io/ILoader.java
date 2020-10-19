package com.ieseljust.joanciscar.io;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.ieseljust.joanciscar.Movement;

import pkg2020_ad_p1_warship.Board;
import pkg2020_ad_p1_warship.Boat;

public interface ILoader {
	public Movement[] loadMovements() throws NumberFormatException, IOException, SAXException, IOException, ParserConfigurationException ;
	public Boat[] loadBoats() throws IOException, NumberFormatException, IllegalArgumentException ,SAXException, IOException, ParserConfigurationException ;
	public Board loadBoardSafe() throws IOException, NumberFormatException, SAXException, IOException, ParserConfigurationException ;
	
}
