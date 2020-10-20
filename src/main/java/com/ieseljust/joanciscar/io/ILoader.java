package com.ieseljust.joanciscar.io;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.xml.sax.SAXException;

import com.ieseljust.joanciscar.Movement;

import pkg2020_ad_p1_warship.Board;
import pkg2020_ad_p1_warship.Boat;

public interface ILoader {
	public Movement[] loadMovements() throws NumberFormatException, IOException, SAXException, IOException, ParserConfigurationException, JSONException ;
	public Boat[] loadBoats() throws IOException, NumberFormatException, IllegalArgumentException ,SAXException, IOException, ParserConfigurationException, JSONException ;
	public Board loadBoardSafe() throws IOException, NumberFormatException, SAXException, IOException, ParserConfigurationException, JSONException ;
	
}
