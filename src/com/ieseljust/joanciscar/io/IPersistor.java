package com.ieseljust.joanciscar.io;

import com.ieseljust.joanciscar.Movement;

import pkg2020_ad_p1_warship.Board;

public interface IPersistor {
	public void saveBoats(Board b);
	public void saveMovements();
	public void registerMovement(Movement m);
	public void resetMovs(); 
}
