package com.ieseljust.joanciscar;

public class AIInput implements IWarshipInput {

	@Override
	public IWarshipInput returnInput() {
		return this;
	}

	@Override
	public int getCoord() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new Double((Math.random() * (WarshipMain.configuration.getCurrentBounds()))).intValue();
	}

	@Override
	public boolean needsToBeStored() {
		return true;
	}

	@Override
	public boolean isAI() {
		return true;
	}

}
