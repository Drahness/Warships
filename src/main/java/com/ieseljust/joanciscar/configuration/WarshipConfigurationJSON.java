package com.ieseljust.joanciscar.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import com.ieseljust.joanciscar.Movement;
import com.ieseljust.joanciscar.WarshipMain;
import com.ieseljust.joanciscar.io.ILoader;
import com.ieseljust.joanciscar.io.IPersistor;

import pkg2020_ad_p1_warship.Board;
import pkg2020_ad_p1_warship.Boat;

public class WarshipConfigurationJSON extends WarshipAbstractFileConfiguration implements ILoader, IPersistor{
	private static WarshipConfigurationJSON instance;
	
	public static WarshipConfigurationJSON getInstance() {
		if(instance == null) {
			instance = new WarshipConfigurationJSON("warships.json");
		}
		return instance;
	}
	
	private JSONObject json;
	
	public WarshipConfigurationJSON(String file) {
		super(new File(file));
		try {
			json = new JSONObject().put(FLAG_ROOT, new JSONObject());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadConfiguration() throws JSONException, IOException {
		
		JSONObject configuration = getRoot(readFile()).getJSONObject(FlagsConfiguration.FLAG_ROOT);
		
		int max = configuration.getInt(FlagsConfiguration.FLAG_MAX_JUGADAS);
		int boats = configuration.getInt(FlagsConfiguration.FLAG_NUMERO_BOATS);
		int bounds = configuration.getInt(FlagsConfiguration.FLAG_TAMAÑO_BOARD);
		boolean save = configuration.getBoolean(FlagsConfiguration.FLAG_GUARDAR_MOVIMIENTOS);
		
		setCurrentBoats(boats);
		setCurrentGuardarMovimientos(save);
		setCurrentMaxJugadas(max);
		setCurrentBounds(bounds);
	}

	@Override
	public void saveConfiguration() throws FileNotFoundException, IOException, TransformerException, SAXException,
			ParserConfigurationException, JSONException {
		JSONObject configuration = new JSONObject()
				.put(FlagsConfiguration.FLAG_GUARDAR_MOVIMIENTOS, getCurrentGuardarMovimientos())
				.put(FlagsConfiguration.FLAG_NUMERO_BOATS, getCurrentBoats())
				.put(FlagsConfiguration.FLAG_MAX_JUGADAS, getCurrentMaxJugadas())
				.put(FlagsConfiguration.FLAG_TAMAÑO_BOARD, getCurrentBounds());
		
		getRoot().put(FlagsConfiguration.FLAG_ROOT, configuration);
		saveToFile();
	}

	@Override
	public void saveBoats(Board b)
			throws IOException, TransformerException, SAXException, ParserConfigurationException, JSONException {
		Boat[] boats = b.getBoats();
		JSONArray array = new JSONArray();
		for (int i = 0; i < boats.length; i++) {
			JSONObject boat = new JSONObject().put(FlagsBoats.FLAG_BOAT
					, new JSONObject()
						.put(FLAG_ATT_ID, i)
						.put(FlagsBoats.FLAG_ATT_COL_POSICION, boats[i].getColumnapos())
						.put(FlagsBoats.FLAG_ATT_ROW_POSICION, boats[i].getFilapos())
						.put(FlagsBoats.FLAG_DIMENSION, boats[i].getDimension())
						.put(FlagsBoats.FLAG_ORIENTACION, boats[i].getDireccion()));
			array.put(boat);
		}
		getRoot().put(FlagsBoats.FLAG_ROOT, array);
		saveToFile();
	}

	@Override
	public void saveMovements() throws JSONException, IOException {
		List<Movement> movs = getMovements();
		JSONArray array = new JSONArray();
		for (int i = 0; i < movs.size(); i++) {
			JSONObject moves = new JSONObject().put(FlagsMovements.FLAG_MOVE
					,new JSONObject()
						.put(FLAG_ATT_ID, i)
						.put(FlagsMovements.FLAG_COLUMN, movs.get(i).getColumna())
						.put(FlagsMovements.FLAG_ROW, movs.get(i).getFila())
						.put(FlagsMovements.FLAG_RESULT, movs.get(i).getResultado()) );
			array.put(moves);
		}
		getRoot().put(FlagsMovements.FLAG_ROOT, array);
		saveToFile();
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
	public Movement[] loadMovements() throws JSONException, IOException {
		JSONArray movements = getRoot(readFile()).getJSONArray(FlagsMovements.FLAG_ROOT);
		int leng = movements.length();
		Movement[] movs = new Movement[leng];
		for (int i = 0; i < leng; i++) {
			JSONObject jsonmov = movements.getJSONObject(i).getJSONObject(FlagsMovements.FLAG_MOVE);
			Movement movement = new Movement(jsonmov.getInt(FLAG_ATT_ID)
					,jsonmov.getInt(FlagsMovements.FLAG_ROW)
					,jsonmov.getInt(FlagsMovements.FLAG_COLUMN)
					,jsonmov.getInt(FlagsMovements.FLAG_RESULT));
			movs[i] = movement;
		}
		return movs;
	}

	@Override
	public Boat[] loadBoats() throws JSONException, IOException {
		JSONArray movements = getRoot(readFile()).getJSONArray(FlagsBoats.FLAG_ROOT);
		int leng = movements.length();
		Boat[] movs = new Boat[leng];
		for (int i = 0; i < leng; i++) {
			JSONObject jsonmov = movements.getJSONObject(i).getJSONObject(FlagsBoats.FLAG_BOAT);
			Boat boat = new Boat(jsonmov.getInt(FlagsBoats.FLAG_DIMENSION)
								,jsonmov.getInt(FlagsBoats.FLAG_ORIENTACION)
								,jsonmov.getInt(FlagsBoats.FLAG_ATT_ROW_POSICION)
								,jsonmov.getInt(FlagsBoats.FLAG_ATT_COL_POSICION)
								,jsonmov.getInt(FLAG_ATT_ID));
			movs[i] = boat;			
		}
		return movs;
	}

	public Board loadBoardSafe() throws JSONException, IOException {
		Boat[] boats = this.loadBoats();
		int newBounds = Boat.getMaxCoordOfBoats(boats);
		WarshipMain.configuration.setCurrentBounds(newBounds);
		Board board = new Board(boats);
		return board;
	}
	private void saveToFile(JSONObject o) throws IOException, JSONException {
		FileWriter fw = new FileWriter(getFileConf());
		fw.write(o.toString(4));
		fw.close();
	}
	private void saveToFile() throws IOException, JSONException {
		saveToFile(json);
	}
	
	private JSONObject readFile() throws IOException, JSONException {
		FileReader fr = new FileReader(getFileConf());
		String json = "";
		int ch;
		while((ch = fr.read()) != -1)  {
			json += (char) ch;
		}
		fr.close();
		return new JSONObject(json);
	}
	/**
	 * To get the root of the json. Where is the roots of configuration/boats/moves with a JSONObject
	 * @return
	 */
	private JSONObject getRoot(JSONObject o) {
		try {
			return o.getJSONObject(FLAG_ROOT);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * To get the root of the json. Where is the roots of configuration/boats/moves
	 * @return
	 */
	private JSONObject getRoot() {
		return getRoot(json);
	}
}
