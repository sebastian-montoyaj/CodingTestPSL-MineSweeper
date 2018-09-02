package co.edu.udea.game;

// Importes necesarios para que las pruebas funcionen
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.CoreMatchers.containsString;

/**
* Class in charge of testing the Grid class
* @author Sebastian Montoya Jimenez
* @version 1.0.0
* @since 02/09/2018
*/
public class GridTest
{
	// ----------------------------------------------------------------------------------------------------------
	// Estas variables y metodos son para poder revisar la salida de la consola
	// Me base del siguiente link: https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
	// Y de este tambien: https://www.dontpanicblog.co.uk/2017/05/12/test-system-out-with-junit/
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	
	@Before
	public void setUpStreams()
	{
	    System.setOut(new PrintStream(outContent));
	}

	@After
	public void restoreStreams()
	{
	    System.setOut(originalOut);
	}
	// ----------------------------------------------------------------------------------------------------------

	@Test
	public void testPreparingBoard() // Prueba para revisar si se esta inicializando correctamente un tablero de juego
	{
		Grid tablero = new Grid(2, 2, 1);
		
		assertNotEquals(tablero.checkGameOver(), true);
		// Pasa si el juego no ha terminado (o sea, se esta jugando)
	}
	
	@Test
	public void testGameOver() // Prueba para revisar si un juego se ha terminado (Ya sea ganado o perdido)
	{
		Grid tablero = new Grid(2, 1, 1);
		
		tablero.executeCommand(0, 0, "u");
		
		assertEquals(tablero.checkGameOver(), true);
		// Pasa si el juego ya se encuentra ganado o perdido
	}
	
	@Test
	public void testClearConsole() // Prueba para comprobar que la funcionalidad de limpiar pantalla si funciona
	{
		Grid.clearScreen();
		
		assertThat(outContent.toString(), containsString(new String(new char[30]).replace("\0", "\r\n")));
		// Pasa si se detectaron más de 30 saltos de linea seguidos en la consola, lo que entre comillas seria como 'limpiar' la consola
	}
	
	@Test
	public void testDrawingBoard() // Prueba para revisar si esta graficando correctamente el tablero de juego
	{
		Grid tablero = new Grid(3, 3, 1);
		
		assertThat(tablero.drawGrid(), containsString("Turn: 1"));
		assertThat(tablero.drawGrid(), containsString("Total Mines: 1"));
		assertThat(tablero.drawGrid(), containsString("Missing Mines: 1"));
		assertThat(tablero.drawGrid(), containsString("|   . . . "));
		// Pasa si en el turno 1 se grafica correctamente las estadisticas del juego y las filas del tablero
	}
	
	@Test
	public void testExecutingCommands() // Prueba para revisar que si se estan ejecutando las jugadas en el tablero
	{
		while (true)
		{
			Grid tablero1 = new Grid(10, 10, 1);
			tablero1.drawGrid().contains(".");
			
			tablero1.executeCommand(0, 0, "m");
			tablero1.drawGrid().contains("P");
			
			tablero1.executeCommand(0, 0, "m");
			tablero1.executeCommand(0, 0, "u");
			
			if (tablero1.drawGrid().contains("-"))
			{
				break;
			}
		}
		
		while (true)
		{
			Grid tablero2 = new Grid(2, 1, 1);
			
			tablero2.executeCommand(0, 0, "u");
			
			if (tablero2.drawGrid().contains("*"))
			{
				break;
			}
		}
		
		// Pasa la prueba si logro obtener todas las posibles acciones que se pueden ejecutar en un tablero de buscaminas
	}

}
