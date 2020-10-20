package com.ieseljust.joanciscar;

public class Movement {
	private int id;
	private int fila;
	private int columna;
	private int resultado;
	
	private static int counter = 0;
	
	public Movement() {
		id = counter++;
	}
	public Movement(int id, int fila, int columna, int resultado) {
		this.id = id;
		this.fila = fila;
		this.columna = columna;
		this.resultado = resultado;
	}
	public int getFila() {
		return fila;
	}
	public int getId() {
		return id;
	}
	public int getColumna() {
		return columna;
	}
	public int getResultado() {
		return resultado;
	}
	public void setColumna(int columna) {
		this.columna = columna;
	}
	public void setResultado(int resultado) {
		this.resultado = resultado;
	}
	public void setFila(int fila) {
		this.fila = fila;
	}
	public void resetCounter() {
		counter = 0;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movement other = (Movement) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
