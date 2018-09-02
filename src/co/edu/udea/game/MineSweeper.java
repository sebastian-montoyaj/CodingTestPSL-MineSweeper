package co.edu.udea.game;

//Importe necesario poder tomar los datos del usuario por consola
import java.util.Scanner;

/**
 * Main class of MineSweeper game.
 * Basically is in charge of taking the user's commands, check them and execute them.
 * @author Sebastian Montoya Jimenez
 * @version 1.0.0
 * @since 30/08/2018
 */
public class MineSweeper // Clase principal del juego Buscaminas
{
	/**
	 * Main method which is the entry point of the application.
	 * @param args This parameter allows the reception of commands by console, but is not used.
	 */
	public static void main(String[] args) // Metodo principal de la aplicacion
	{
		// En pirmer lugar, creo los siguientes objetos ...
		Grid boardGame; // Variable que representara el tablero del buscaminas
		boolean runnigApp = true; // Variable para determinar si el usuario quiere jugar una partida mas
		int heightGrid = 0; // Variable para almacenar la altura (numero de filas) del tablero de juego
		int widthGrid = 0; // Variable para almacenar el ancho (numero de columnas) del tablero de juego
		int numberOfMines = 0; // Variable para almacenar el numero de minas que estaran plantadas en el tablero
		Scanner consoleReader = new Scanner(System.in); // Objeto scanner el cual estara pendiente de lo que sucede en la consola
		String inputValue; // Variable auxiliar para recibir el valor que digito el usuario en la consola
		
		// Luego, imprimo que el aplicativo ha iniciado
		System.out.println(":::::::::::::::::: MINESWEEPER START ::::::::::::::::::");
		
		// A continuacion y por primera vez haga ...
		do 
		{
			do
			{
				// Pregunto el numero de filas que va a tener el tablero de juego
				System.out.println("Type the board's height (Files):");
				inputValue = consoleReader.next();
				
				// Si lo escrito por el usuario NO es un numero entero positivo entonces
				if (!isAPositiveIntegerNumber(inputValue))
				{
					// Imprimo un mensaje al respecto y vuelvo a preguntar
					System.out.println("The board's height is not a positive integer number!");
				}
			} while (!isAPositiveIntegerNumber(inputValue));
			
			// Una vez pasa el ciclo, casteo dicho valor ingresado a la variable heightGrid
			heightGrid = Integer.parseInt(inputValue);
			
			do
			{
				// Despues, pregunto el numero de columnas que va a tener el tablero de juego
				System.out.println("Type the board's width (Columns):");
				inputValue = consoleReader.next();
				
				// Si lo escrito por el usuario NO es un numero entero positivo entonces
				if (!isAPositiveIntegerNumber(inputValue))
				{
					// Imprimo un mensaje al respecto y vuelvo a preguntar
					System.out.println("The board's width is not a positive integer number!");
				}
				
			} while (!isAPositiveIntegerNumber(inputValue));
			
			// Una vez pasa entonces guardo/casteo el valor ingresado a la variable widthGrid
			widthGrid = Integer.parseInt(inputValue);
			
			do
			{
				do
				{
					// Finalmente, pregunto el numero de minas que el usuario desea poner en el tablero
					System.out.println("Type the number of mines:");
					inputValue = consoleReader.next();
					
					// Nuevamente, Si lo escrito por el usuario NO es un numero entero positivo entonces
					if (!isAPositiveIntegerNumber(inputValue))
					{
						// Imprimo un mensaje al respecto y vuelvo a preguntar
						System.out.println("The number of mines is not a positive integer number!");
					}
				} while (!isAPositiveIntegerNumber(inputValue));
				
				// Una vez pasa el ciclo anterior entonces guardo el valor ingresado en la variable numberOfMines
				numberOfMines = Integer.parseInt(inputValue);
				
				// Adicionalmente, Si el numero de minas es MAYOR O IGUAL al numero de baldosas del tablero entonces
				if (numberOfMines >= heightGrid*widthGrid)
				{
					// Imprimo un mensaje al respecto y vuelvo a solicitar un numero de minas mas bajo
					System.out.println("The number of mines must be less than board's squares");
				}
			} while (numberOfMines >= heightGrid*widthGrid);
			
			// Ya con los parametros del juego correctos, se crea el tablero de juego
			boardGame = new Grid(heightGrid, widthGrid, numberOfMines);
			
			// Tambien se crean unas variables auxiliares, las cuales nos ayudaran a procesar los comandos que ingrese el usuario
			int indexFile;
			int indexColumn;
			String action;
			
			// Y se da inicio a la partida, de modo que mientras no se termine la misma haga ...
			while (!boardGame.checkGameOver())
			{
				Grid.clearScreen(); // Se limpia la pantalla o consola
				System.out.println(boardGame.drawGrid()); // Se dibuja el estado actual del tablero de juego
				
				System.out.println("---- ENTER NEXT COMMAND ----"); // Y se empieza la lectura del siguiente comando
				// Antes de pasar a leer la consola, reinicio los valores del comando anterior
				indexFile = 0;
				indexColumn = 0;
				action = "";
				
				do
				{
					// Ahora, solicito que se ingrese el indice de la fila de la baldosa en la que se quiere actuar
					System.out.print("File index: ");
					inputValue = consoleReader.next();
					
					// Si lo ingresado NO es un numero entonces
					if (!isAPositiveIntegerNumber(inputValue))
					{
						// Emito un mensaje y salto a la siguiente iteración para volver a preguntar
						System.out.println("File index is not a positive number!");
						continue;
					}
					
					// Si lo ingresado si fue un numero, lo guardo en indexFile
					indexFile = Integer.parseInt(inputValue);
					
					// Y reviso que dicho indice si este dentro del numero de filas indicadas al principio, Si no es asi entonces
					if ((indexFile < 1) || (indexFile > heightGrid))
					{
						// Imprimo que el indice se sale del rango esperado
						System.out.println("File index is out of range!");
					}
				} while ((indexFile < 1) || (indexFile > heightGrid)); // No se continua hasta que el usuario no ingrese un indice valido
				
				do
				{
					// Despues, solicito el indice de la columna de la baldosa en la que se quiere actuar
					System.out.print("Column index: ");
					inputValue = consoleReader.next();
					
					// Si lo ingresado NO es un numero entonces
					if (!isAPositiveIntegerNumber(inputValue))
					{
						// Emito un mensaje y salto a la siguiente iteración para volver a preguntar
						System.out.println("Column index is not a positive number!");
						continue;
					}
					
					// Si lo ingresado si fue un numero, lo guardo en indexColumn
					indexColumn = Integer.parseInt(inputValue);
					
					// Y reviso que dicho indice si este dentro del numero de columnas indicadas al principio, Si no es asi entonces
					if ((indexColumn < 1) || (indexColumn > widthGrid))
					{
						// Imprimo que el indice se sale del rango esperado
						System.out.println("Column index is out of range!");
					}
				} while ((indexColumn < 1) || (indexColumn > widthGrid));  // No se continua hasta que el usuario no ingrese un indice valido
				
				do
				{
					// Finalmente, solicito que se indique cual es la accion a tomar con la baldosa
					System.out.print("Action: ");
					inputValue = consoleReader.next();
					
					// Si el comando/accion ingresado NO es ni 'U' ni 'M' entonces
					if (!("U".equalsIgnoreCase(inputValue) || "M".equalsIgnoreCase(inputValue)))
					{
						// Imprimo comando invalido e indico cuales son las acciones permitidas
						System.out.println("Invalid action!\nType 'U' to Uncover or 'M' to Mark/UnMark");
					}
				} while(!("U".equalsIgnoreCase(inputValue) || "M".equalsIgnoreCase(inputValue))); // No se continua hasta que el usuario no ingrese una accion valida
				
				// Una vez pasa el ciclo anterior guardo la accion
				action = inputValue;
				
				// Y ejecuto el comando en el tablero. NOTA: Se resta -1 a los indices porque los arreglos comienzan desde el 0 y no desde el 1 dibujado en consola
				boardGame.executeCommand(indexFile-1, indexColumn-1, action);
			}
			
			// Una vez se termina la partida, preguntamos al usuario si desea seguir jugando y comenzar un nuevo juego
			System.out.println("Do you want to start a new game? (Y/N):");
			inputValue = consoleReader.next();
			
			// Si la respuesta es cualquier cosa diferente de 'Y' entonces
			if (!"Y".equalsIgnoreCase(inputValue))
			{
				runnigApp = false; // Se cambia el valor de la variable centinela runnigApp a FALSO
			}
			
		} while (runnigApp); // Ahora, mientras que el usuario desee seguir jugando vuelva a hacer ...
		
		// Para terminar, cerramos la comunicacion del objeto scanner Y se imprime un mensaje para advertir de la terminacion del programa
		consoleReader.close();
		System.out.println("::::::::::::::::::: MINESWEEPER END :::::::::::::::::::");
	}
	
	/**
	 * This method help to define if a string is a positive integer number (Zero is not considered positive)
	 * @param stringToAnalize String to study
	 * @return true if the string is truly a positive integer, false otherwise
	 */
	public static boolean isAPositiveIntegerNumber(String stringToAnalize) // Metodo para validar que una cadena de caracteres si corresponde a un numero entero positivo
	{
		try // Se intenta ...
		{
			int number = Integer.parseInt(stringToAnalize); // Castear la cadena de caracteres a un numero entero
			
			// Si pasa la instruccion anterior entonces ahora reviso que el numero en cuestion SI sea Positivo
			if (number > 0)
			{
				return true; // Si la validacion es correcta retorno VERDADERO
			}

			return false; // Sino retorno FALSO
		}
		catch (NumberFormatException ex) // En caso de error ...
		{
			return false; // Retorno FALSO
		}
	}
	
}
