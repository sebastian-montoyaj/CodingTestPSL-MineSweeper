package co.edu.udea.game;

/**
* The Tile class contains the necessary properties and methods in order to handle the behavior of each square in the minesweeper game in a practical and easy way.
* @author Sebastian Montoya Jimenez
* @version 1.0.0
* @since 30/08/2018
*/
public class Tile // Clase encargada de la logica y metodos que debe tener cada celda o cuadro del tablero del bucaminas
{
	// ------------ CONSTANTES ------------
	
	// Estas constantes son usadas para evitar el anti-patron: "Numeros magicos".
	// De modo que se usan para asociar un cierto estado de la baldosa a un cierto numero de referencia o identificacion.
	
	/** Value of covered state */
	public static final int COVERED_STATE = 0;
	
	/** Value of uncovered state */
	public static final int UNCOVERED_STATE = 1;
	
	/** Value of empty tiles */
	public static final int EMPTY_TILE = 0;
	
	// ------------ VARIABLES ------------
	
	private int state; // Variable que almacenara el estado de la baldosa
	private boolean mark; // Variable para saber si la baldosa ha sido marcada con una bandera
	private boolean mine; // Variable para saber si la baldosa tiene una mina
	private int surroundingMines; // Variable para saber cuantas minas hay alrededor de esta baldosa
	
	// ------------ CONSTRUCTOR ------------
	
	// Por defecto, toda baldosa estara: cubierta, sin marcar y sin minas (alrededor o dentro)
	/**
	 * Default constructor, where every tile is initialized as: covered, unmarked and without any mine
	 */
	public Tile()
	{
		this.state = COVERED_STATE;
		this.mark = false;
		this.mine = false;
		this.surroundingMines = 0;
	}
	
	// ------------ METODOS ------------
	
	// Los siguientes son los metodos getters y setters de la clase, y los cuales nos ayudaran a obtener y modificar las caracteristicas de la baldosa
	
	/**
	 * Method for getting the tile's state
	 * @return tile's state
	 */
	public int getState()
	{
		return state;
	}
	
	/**
	 * Method for setting the tile's state
	 * @param state the new the tile's state
	 */
	public void setState(int state)
	{
		this.state = state;
	}
	
	/**
	 * Method which asks if this tile is marked
	 * @return true if this tile has a mark, false otherwise
	 */
	public boolean isMarked()
	{
		return mark;
	}
	
	/**
	 * Method for marking/unmarking this tile
	 * @param mark boolean value which says if the tile is going to be marked or not
	 */
	public void setMark(boolean mark)
	{
		this.mark = mark;
	}
	
	/**
	 * Method which asks if this tile has a mine
	 * @return true if this tile has a mine, false otherwise
	 */
	public boolean isMined()
	{
		return mine;
	}
	
	/**
	 * Method which set or remove a mine of this tile
	 * @param mine boolean value which says if the tile is going to have a mine or not
	 */
	public void setMine(boolean mine)
	{
		this.mine = mine;
	}
	
	/**
	 * Method which returns the number of surrounding mines
	 * @return the integer number of surrounding mines
	 */
	public int getSurroundingMines()
	{
		return surroundingMines;
	}
	
	/**
	 * Method which set the number of surrounding mines
	 * @param surroundingMines integer number of surrounding mines
	 */
	public void setSurroundingMines(int surroundingMines)
	{
		this.surroundingMines = surroundingMines;
	}
	
	/**
	 * Method which asks if this tile is empty
	 * @return true if the tile does not have a mine inside or around, false otherwise
	 */
	public boolean isEmpty() // Metodo adicional a los getters y setters y que utilizo para saber si la baladosa esta vacia o no
	{
		if (EMPTY_TILE == getSurroundingMines() && !isMined())
		{
			return true; // Retorno verdadero si la baldosa NO tiene una dentro y tampoco si tiene minas alrededor
		}
		
		return false; // De lo contario, retorno falso
	}
}
