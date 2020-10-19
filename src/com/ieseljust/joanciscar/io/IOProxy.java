package com.ieseljust.joanciscar.io;

import com.ieseljust.joanciscar.Movement;
import com.ieseljust.joanciscar.configuration.IConfiguration;

import pkg2020_ad_p1_warship.Board;
import pkg2020_ad_p1_warship.Boat;

public class IOProxy implements ILoader, IPersistor ,IConfiguration {
	
	@Override
	public void setDefault() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadConfiguration() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveConfiguration() throws Exception {
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
