package com.ieseljust.joanciscar;

public class FileInput implements IWarshipInput { 
	private int mode;
	
	private static int counter;
	private Movement currentMovement;
	private Movement[] movements;
	
	public Movement[] getMovements() {
		return movements;
	}
	public void setMovements(Movement[] movements) {
		counter = 0;
		this.movements = movements;	

	}
	public FileInput(int mode) {
		this.mode = mode;
	}
	@Override
	public int getCoord() {
		System.out.println(counter);
		switch(counter%2) {
		case 0:
			currentMovement = movements[counter/2];
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			counter++;
			return currentMovement.getFila();
		default:
			counter++;
			return currentMovement.getColumna();
		}
	}
	@Override
	public IWarshipInput returnInput() {
		if (counter/2 < movements.length) {
			return this;
		}
		return getNextMode();
	}
	
	private IWarshipInput getNextMode() {
		switch(mode) {
		case 1:
			return new UserInput();
		default:
			return new AIInput();
		}
	}
	@Override
	public boolean needsToBeStored() {
		return false;
	}
	@Override
	public boolean isAI() {
		return mode == 0;
	}
	
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
