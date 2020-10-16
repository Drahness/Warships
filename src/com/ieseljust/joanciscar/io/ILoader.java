package com.ieseljust.joanciscar.io;

import com.ieseljust.joanciscar.Movement;

import pkg2020_ad_p1_warship.Boat;

public interface ILoader {
	public Movement[] loadMovements();
	public Boat[] loadBoats();
	public Boat[] loadBoardSafe();
}
