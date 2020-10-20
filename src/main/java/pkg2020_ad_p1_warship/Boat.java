package pkg2020_ad_p1_warship;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joange
 */
public class Boat {
	// Constantes para controlar el estado general del barco
	public final static int BOAT_OK = 0;
	public final static int BOAT_TOUCHED = 1;
	public final static int BOAT_SUNKEN = 2;

	// Constantes para controlar su orientación
	public final static int BOAT_DIR_HOR = 0;
	public final static int BOAT_DIR_VER = 1;

	// Propiedades
	private int dimension = 0;
	private Cell[] cells;
	private int estado = BOAT_OK;
	private int id;
	private int filapos = -1;
	private int columnapos = -1;
	private int direccion = -1;
	
	// Constructor
	public Boat() {
		// Vacío, las propiedades se establecen en su iniciaización
	}
	
	public Boat(int dimension ,int dir,int fila ,int columna,int id) {
		this.dimension = dimension;
		this.filapos = fila;
		this.direccion = dir;
		this.columnapos = columna;
	}
	
	// Para consultar el estado de un barco
	public int getBoatState() {
		return estado;
	}

	// Establece la dimensión del barco y lo coloca en el tablero si cabe
	public void setBoat(int dim, Board board) {
		cells = new Cell[dim];
		if(dimension == 0) { // a�adido por joan
			this.dimension = dim;
		}
		// Posicionamiento aleatorio
		// Todos estos ifs son mios
		int fila, columna, dir;
		if( filapos == -1) {
			fila = new Double((Math.random() * (Board.BOARD_DIM))).intValue();
		}
		else {
			fila = filapos;
		}
		if( columnapos == -1) {
			columna = new Double(Math.random() * (Board.BOARD_DIM)).intValue();
		} else {
			columna = columnapos;
		}
		if(direccion == -1) {
			dir = new Double(Math.random() * (Boat.BOAT_DIR_VER + 1)).intValue();
		}else {
			dir = direccion;
		}

		while (!boatFits(fila, columna, dim, dir, board)) {
			fila = new Double((Math.random() * Board.BOARD_DIM)).intValue();
			columna = new Double(Math.random() * Board.BOARD_DIM).intValue();
		}

		if (dir == BOAT_DIR_HOR) {
			for (int i = columna; i < columna + dim; i++) {
				this.cells[i - columna] = board.getCell(fila, i);
				this.cells[i - columna].setBoat(this);
			}
		} else {
			for (int i = fila; i < fila + dim; i++) { // BOAT_DIR_VER
				this.cells[i - fila] = board.getCell(i, columna);
				this.cells[i - fila].setBoat(this);
			}
		}
		this.direccion = dir; /// Agregado por Joan
	}
	// Joan 
	public void setBoat(Board board) {
		setBoat(dimension, board);
	}
	
	// Controla si el bote cabe el el tablero
	private boolean boatFits(int fila, int columna, int dimension, int direccion, Board board) {
		int min_col = 0, max_col = 0, min_row = 0, max_row = 0;

		// los barcos se colocan de fila, columna hacia la derecha o hacia abajo
		// Dependiendo de la orientación calcula el recuadro a controlar

		if (direccion == Boat.BOAT_DIR_HOR) {
			if ((columna + dimension) > Board.BOARD_DIM)
				return false; // El barco no cabe

			min_col = board.fitValueToBoard(columna - 1);
			max_col = board.fitValueToBoard(columna + dimension);

			min_row = board.fitValueToBoard(fila - 1);
			max_row = board.fitValueToBoard(fila + 1);
		}
		if (direccion == Boat.BOAT_DIR_VER) {
			if ((fila + dimension) > Board.BOARD_DIM)
				return false; // El barco no cabe

			min_col = board.fitValueToBoard(columna - 1);
			max_col = board.fitValueToBoard(columna + 1);

			min_row = board.fitValueToBoard(fila - 1);
			max_row = board.fitValueToBoard(fila + dimension);
		}

		// Recorre la matriz que contendrá el barco para asegurarse que no hay ninguno
		for (int i = min_row; i <= max_row; i++) {
			for (int j = min_col; j <= max_col; j++) {
				if (board.getCell(i, j).getContains() == Cell.CELL_BOAT)
					return false; // Ya hay un barco
			}
		}
		return true;
	}
	public boolean boatFits(Board board) {
		return boatFits(filapos,columnapos,dimension,direccion,board);
	}

	// Cuando una shot cae sobre un barco
	public int touchBoat(int fila, int columna) {
		int tocados = 0;
		// Si ya está hundido no puede empeorar
		if (estado == BOAT_SUNKEN)
			return BOAT_SUNKEN;

		// Si no está hundido como mínimo estará tocado
		estado = Boat.BOAT_TOUCHED;

		// Comprueba si esta parte del barco aún no habia sido tocada
		// Cuenta los tocados para saber si está hundido
		for (int i = 0; i < dimension; i++) {
			Cell c = cells[i];
			if ((c.getRow() == fila) && (c.getColumn() == columna)) {
				if (c.getContains() == Cell.CELL_BOAT)
					c.setTouch();
			}
			if (c.getContains() == Cell.CELL_TOUCH)
				tocados++;
		}
		// Si todas las partes del barco están tocadas ... Cambiar estado a hundido
		if (tocados == dimension) {
			// Hundido ....
			for (int i = 0; i < dimension; i++) {
				Cell c = cells[i];
				c.setSunken();
			}
			estado = BOAT_SUNKEN;
		}

		return estado;
	}

	// Para mostrar por pantalla las celdas que ocupa un barco
	public void viewCells() {
		System.out.print("Posiciones: {");
		for (int i = 0; i < dimension; i++) {
			Cell c = cells[i];
			System.out.print("(" + c.getRow() + ", " + c.getColumn() + ")");
		}
		System.out.println(" }");
	}
	
	/**
	 * Autor Joan
	 * @return
	 */
	public int getDimension() {
		return dimension;
	}

	public int getDireccion() {
		return direccion;
	}
	public int getFilapos() {
		if(cells == null) {
			return filapos;
		}
		return cells[0].getRow();
	}

	public int getColumnapos() {
		if(cells == null) {
			return columnapos;
		}
		return cells[0].getColumn();
	}
	
	
	public boolean boardFits(Board board, Boat[] boats) {
		for (Boat boat : boats) {
			if(!boat.boatFits(board)) {
				return false;
			}
		}
		return true;
	}
	/**
	 */
	public int getMaxCoordOfBoat() {
		int filaMax = this.getDireccion() == Boat.BOAT_DIR_VER ? this.getFilapos() + this.getDimension() : this.getFilapos();
		int columnaMax = this.getDireccion() == Boat.BOAT_DIR_HOR ? this.getColumnapos() + this.getDimension() : this.getColumnapos();
		
		if(filaMax > columnaMax) {
			return filaMax;
		}
		else {
			return columnaMax;
		}
	}
	public static int getMaxCoordOfBoats(Boat[] boats) {
		int maxSize = 0;
		for (Boat boat : boats) {
			int max = boat.getMaxCoordOfBoat();
			if(maxSize < max) {
				maxSize = max;
			}
		}
		return maxSize;
	}
}
