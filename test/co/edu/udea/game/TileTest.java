package co.edu.udea.game;

// Importes necesarios para que las pruebas funcionen
import static org.junit.Assert.*;
import org.junit.Test;

/**
* Class in charge of testing the Tile class
* @author Sebastian Montoya Jimenez
* @version 1.0.0
* @since 02/09/2018
*/
public class TileTest
{
	
	@Test
	public void testGettingTile() // Prueba para revisar la construccion de una baldosa
	{
		Tile baldosa = new Tile();
		
		assertEquals(baldosa.getState(), Tile.COVERED_STATE);
		assertEquals(baldosa.isMarked(), false);
		assertEquals(baldosa.isMined(), false);
		assertEquals(baldosa.getSurroundingMines(), 0);
		// Pasa si la baldosa esta descubierta, sin marca y sin minas
	}
	
	@Test
	public void testGettingAndSettingState() // Prueba para revisar los metodos: getState y setState
	{
		Tile baldosa = new Tile();
		
		assertEquals(baldosa.getState(), Tile.COVERED_STATE);
		
		baldosa.setState(Tile.UNCOVERED_STATE);
		
		assertNotEquals(baldosa, Tile.COVERED_STATE);
		// Pasa si se puede determinar y establecer correctamente el estado de la baldosa
	}
	
	@Test
	public void testCheckingAndSettingMark() // Prueba para revisar los metodos: isMarked y setMark
	{
		Tile baldosa = new Tile();
		
		assertEquals(baldosa.isMarked(), false);
		
		baldosa.setMark(true);
		
		assertNotEquals(baldosa.isMarked(), false);
		// Pasa si se puede determinar y establecer correctamente si la baldosa esta marcada o no
	}
	
	@Test
	public void testCheckingAndSettingMine() // Prueba para revisar los metodos: isMined y setMine
	{
		Tile baldosa = new Tile();
		
		assertEquals(baldosa.isMined(), false);
		
		baldosa.setMine(true);
		
		assertNotEquals(baldosa.isMined(), false);
		// Pasa si se puede determinar y establecer correctamente si la baldosa contiene una mina o no
	}
	
	
	@Test
	public void testGettingAndSettingSurroundingMines() // Prueba para revisar los metodos: getSurroundingMines y setSurroundingMines
	{
		Tile baldosa = new Tile();
		
		assertEquals(baldosa.getSurroundingMines(), 0);
		
		baldosa.setSurroundingMines(7);
		
		assertEquals(baldosa.getSurroundingMines(), 7);
		// Pasa si se puede determinar y establecer correctamente el numero de minas circundantes en una baldosa
	}
	
	
	@Test
	public void testCheckingEmptyTile() // Prueba para revisar el metodo isEmpty
	{
		Tile baldosa = new Tile();
		
		assertEquals(baldosa.isEmpty(), true);
		// Pasa si puede dectectar que una baldosa recien creada esta vacia
	}
	
}
