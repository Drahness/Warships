package com.ieseljust.joanciscar;

import pkg2020_ad_p1_warship.Board;

/**
 * Interfaz para englobar los tipos de inputs del programa.
 * @author Catalan Renegado
 */
public interface IWarshipInput {
	public int getCoord();
	/**
	 * @return La misma clase salvo la clase de {@link FileInput}
	 */
	public IWarshipInput returnInput();
	
	public boolean needsToBeStored();
	public boolean isAI();
	
	public default void paint(Board board) {
		if(this.isAI()) {
			board.paint();
		}
		else {
			board.paintGame();
		}
	}
}
