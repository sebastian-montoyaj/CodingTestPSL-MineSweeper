package co.edu.udea.game;

//Importe necesario para aleatorizar la posicion de las minas
import java.util.Random;

/**
* The Grid class allows the management of a minesweeper board.
* This class implements all necessary operations to simplify the mechanics of the minesweeper game.
* @author Sebastian Montoya Jimenez
* @version 1.0.0
* @since 30/08/2018
*/
public class Grid // Clase encargada de la logica y metodos del tablero o rejilla del juego buscaminas
{
	// ------------ CONSTANTES ------------
	
	// Estas constantes son usadas para evitar el anti-patron: "Numeros magicos".
	
	// Por lo tanto, las siguientes tres constantes se usan para darle al tablero valores que indican si una partida se ha ganado, perdido o esta todavia esta en curso.
	
	/** Value of Playing state*/
	private static final int PLAYING = 0;
	/**	Value of Winned state */
	private static final int WINNED = 1;
	/**	Value of Losed state */
	private static final int LOSED = -1;
	
	// Y las siguientes son para establecer los caracteres que representaran cada objeto en pantalla y asociar el comando de revelar/descubrir al caracter 'U'
	
	/**	Representative character of unselected tiles */
	private static final String UNSELECTED_TILE_CARACTER = ". ";
	/**	Representative character of disabled tiles */
	private static final String DISABLED_TILE_CARACTER = "- ";
	/**	Representative character of mined tiles */
	private static final String MINED_TILE_CARACTER = "* ";
	/**	Representative character of marked tiles */
	private static final String MARKED_TILE_CARACTER = "P ";
	/**	Value of uncover command */
	private static final String UNCOVER_COMMAND = "U";
	
	// ------------ VARIABLES ------------
	
	private Tile[][] gameGrid; // Matriz que almacenara las baldosas del tablero de juego
	private int files; // Variable que guardara el numero de filas del tablero
	private int columns; // Variable que guardara el numero de columnas del tablero
	private int totalMines; // Variable que guardara el numero total de minas plantadas en el tablero
	private int missingMines; // Variable que llevara el conteo de las minas faltantes por marcar. OJO: Si se vuelve NEGATIVA es porque hay mas baldosas marcadas que minas plantadas
	private int turn; // Variable para llevar el numero de turnos/rondas que lleva jugando el usuario
	private int gameState; // Variable para saber en que estado se encuentra la partida de juego
	
	// ------------ CONSTRUCTOR ------------
	
	/**
	 * Constructor which initializes a empty game board
	 * @param files Numbers of files of the new game board
	 * @param columns Numbers of columns of the new game board
	 * @param totalMines Numbers of mines to set in the board
	 */
	public Grid(int files, int columns, int totalMines)
	{
		// Se inicializa el numero de filas y columnas
		this.files = files;
		this.columns = columns;
		
		// Se inicialia el numero de minas totales y faltantes
		this.totalMines = this.missingMines = totalMines;
		
		// Y se inicializa el turno, el estado y tablero en si mismo
		this.turn = 1;
		this.gameState = PLAYING;
		initializeGrid();
	}
	
	// ------------ METODOS ------------
	
	/**
	 * Method for getting started the board game.
	 */
	private void initializeGrid()
	{
		gameGrid = new Tile[files][columns]; // En primer lugar, instancio las dimensiones del tablero de juego
		
		// Luego, recorro cada una de las baldosas del tablero
		for (int i = 0; i < files; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				gameGrid[i][j] = new Tile(); // E inicializo cada baldosa. Todas empezaran vacias.
			}
		}
		
		plantMines(); // Posteriormente, pongo las minas en el tablero
		setSurroundingMinesCount(); // Y escribo el conteo de minas circundantes en aquellas baldosas cercanas a las minas
	}
	
	/**
	 * Method who set the mines randomly in the board
	 */
	private void plantMines()
	{
		Random randomGenerator = new Random(); // Para empezar, se crea un objeto Random el cual nos ayudara para la generacion de numeros aleatorios
		
		int minesCounter = 0; // Luego, se crea una variable auxiliar para llevar el conteo de cuantas minas han sido 'plantadas' exitosamente
		
		// Ahora, mientras el numero de minas plantadas sea menor al numero de minas que debe tener el tablero haga
		while (minesCounter < totalMines)
		{
			// Genero, un par de coordenadas X y Y con rangos entre [0, files-1] y [0, columns-1], respectivamente
			int randomFile = randomGenerator.nextInt(files);
			int randomColumn = randomGenerator.nextInt(columns);
			
			// Despues, tomo temporalmente la baldosa que corresponde a dichas coordenadas
			Tile auxTile = gameGrid[randomFile][randomColumn];
			
			// Y reviso si ya tiene una mina, Si no la tiene entonces
			if (!auxTile.isMined())
			{
				auxTile.setMine(true); // Le pongo una mina
				minesCounter++; // E incremento el contador de minas plantadas
			}
		}
	}
	
	/**
	 * Method which set the count of surrounding mines in those tiles who are near to mined tiles
	 */
	private void setSurroundingMinesCount()
	{
		// Para toda baldosa de la fila i y columna j del tablero procedo a ...
		for (int i = 0; i < files; i++) 
		{
			for (int j = 0; j < columns; j++)
			{
				// Tomar dicha baldosa
				Tile auxTile = gameGrid[i][j];
				
				// Y revisar si esta minada, Si NO lo esta entonces
				if (!auxTile.isMined())
				{
					int count = countMines(i, j); // Calculo el numero de minas que hay circundantes a la misma
					auxTile.setSurroundingMines(count); // Y escribo dicho valor dentro de ella
				}
			}
		}
	}
	
	/**
	 * Method in charge of making the count of surrounding mines of the specified tile
	 * @param fileTile X coordinate of the tile
	 * @param columnTile Y coordinate of the tile
	 * @return the exact number of surrounding mines
	 */
	private int countMines(int fileTile, int columnTile)
	{
		// En primer lugar, creo e inicializo en cero una variable para llevar el conteo de las minas que estan alrededor
		int count = 0;
		
		// Luego, para las posiciones arriba, en y abajo de la coordenada en X de la baldosa de estudio haga ...
		for (int x = -1; x < 2; x++)
		{
			// Calculo la coordenada X de la baldosa adyacente
			int xIndex = fileTile + x;
			
			// Si dicha posicion X de la baldosa adyacente es menor a cero O mayor o igual al numero de filas del tablero entonces
			if (xIndex < 0 || xIndex >= files)
			{
				continue; // Salto/Paso esta iteracion porque estariamos saliendo del rango del tablero
			}
			
			// Ahora, para las posiciones antes, en y despues de la coordenada en Y de la baldosa de estudio haga ...
			for (int y = -1; y < 2; y++)
			{
				// Calculo la coordenada Y de la baldosa adyacente
				int yIndex = columnTile + y;
				
				// Si dicha posicion Y de la baldosa adyacente es menor a cero O mayor o igual al numero de columnas del tablero entonces
				if (yIndex < 0 || yIndex >= columns)
				{
					continue; // Salto/Paso esta iteracion porque estariamos saliendo del rango del tablero
				}
				
				// Despues, si el desfase de ambas coordendas es cero entonces
				if (x == 0 && y == 0)
				{
					continue; // Continuo a la siguiente iteracion porque no se vale contar la baldosa a la que se debe calcular el numero de minas
				}
				
				// Si pasa aqui es porque tengo una baldosa adyacente valida por lo que reviso si esta tiene una mina. Si la tiene entonces
				if (gameGrid[xIndex][yIndex].isMined())
				{
					count++; // Sumo 1 al contador de minas
				}
			}
		}
		
		// Una vez se han recorrido todas las baldosas adyacentes entonces retorno el numero de minas circundantes que tiene la baldosa de estudio
		return count;
	}
	
	/**
	 * Method for uncovering all mined tiles when the user loses
	 */
	private void uncoverAllMinedTiles()
	{
		// Para toda baldosa de la fila i y columna j del tablero procedo a ...
		for (int i = 0; i < files; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				// Revisar si esta minada, Y si lo esta entonces
				if (gameGrid[i][j].isMined())
				{
					// La descubro/destapo
					gameGrid[i][j].setState(Tile.UNCOVERED_STATE);
				}
			}
		}
		
		// Antes de terminar y como signo de derrota entonces la minas faltantes fueron TODAS
		missingMines = totalMines;
	}
	
	/**
	 * Method for marking all mined tiles when the user wins
	 */
	private void markAllMinedTiles()
	{
		// Para toda baldosa de la fila i y columna j del tablero procedo a ...
		for (int i = 0; i < files; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				// Revisar si esta minada, Y si lo esta entonces
				if (gameGrid[i][j].isMined())
				{
					// La marco
					gameGrid[i][j].setMark(true);
				}
			}
		}
		
		// Antes de terminar y como signo de victoria entonces la minas faltantes fueron CERO
		missingMines = 0;
	}
	
	/**
	 * Recursive method which uncovers the indicated tile and all empty or non-mined adjacent tiles
	 * @param fileTile X coordinate of the tile
	 * @param columnTile Y coordinate of the tile
	 */
	private void seekAndUncoverTiles(int fileTile, int columnTile)
	{
		// Criterio de parada 1 - Si la baldosa de estudio esta MARCADA o MINADA o ya DESCUBIERTA entonces
		if (gameGrid[fileTile][columnTile].isMarked() || gameGrid[fileTile][columnTile].isMined() || Tile.UNCOVERED_STATE == gameGrid[fileTile][columnTile].getState())
		{
			return; // Simplemente retorno y no haga nada
		}
		
		// Ahora, Si pasa aqui es porque la baldosa indicada en los parametros esta cubierta y no minada por lo cual reviso ...
		
		// Criterio de parada 2 - Si la baldosa de estudio NO esta vacia entonces
		if (!gameGrid[fileTile][columnTile].isEmpty())
		{
			// Tan solo destapo la baldosa y retorno
			gameGrid[fileTile][columnTile].setState(Tile.UNCOVERED_STATE);
			return;
		}
		
		// Ahora, Si pasa aqui es porque la baldosa en cuestion esta VACIA y por tanto la descubro
		gameGrid[fileTile][columnTile].setState(Tile.UNCOVERED_STATE);
		
		// Luego, para destapar las baldosas adyacentes vacias o no minadas hago ...
		
		// Para las posiciones arriba, en y abajo de la coordenada en X de la baldosa de estudio, procedo con lo siguiente ...
		for (int x = -1; x < 2; x++)
		{
			// Calculo la coordenada X de la baldosa adyacente
			int xIndex = fileTile + x;
			
			// Si dicha posicion X de la baldosa adyacente es menor a cero O mayor o igual al numero de filas del tablero entonces
			if (xIndex < 0 || xIndex >= files)
			{
				continue; // Salto/Paso esta iteracion porque estariamos saliendo del rango del tablero
			}
			
			// Ahora, para las posiciones antes, en y despues de la coordenada en Y de la baldosa de estudio, procedo con lo siguiente ...
			for (int y = -1; y < 2; y++)
			{
				// Calculo la coordenada Y de la baldosa adyacente
				int yIndex = columnTile + y;
				
				// Si dicha posicion Y de la baldosa adyacente es menor a cero O mayor o igual al numero de columnas del tablero entonces
				if (yIndex < 0 || yIndex >= columns)
				{
					continue; // Salto/Paso esta iteracion porque estariamos saliendo del rango del tablero
				}
				
				// Despues, si el desfase de ambas coordendas es cero entonces
				if (x == 0 && y == 0)
				{
					continue; // Continuo a la siguiente iteracion porque ya descubri la baldosa de estudio
				}
				
				// Si pasa aqui es porque tengo una baldosa adyacente con unas coordenadas apropiadas y por consiguiente llamo al metodo recursivo seekAndUncoverTiles para determinar si debe seguir destapando baldosas
				seekAndUncoverTiles(xIndex, yIndex);
			}
		}
	}
	
	/**
	 * Method which execute an action in the board game and check if the user wins, losses or must continue playing
	 * @param fileTile X coordinate of the tile
	 * @param columnTile Y coordinate of the tile
	 * @param action Action to execute in the selected tile
	 */
	public void executeCommand(int fileTile, int columnTile, String action)
	{
		// Para empezar tomo la baldosa indicada en los parametros y la guardo temporalmente en una variable auxiliar
		Tile auxTile = gameGrid[fileTile][columnTile];
		
		// Luego, Si la accion a realizar es destapar/descubrir entonces
		if (UNCOVER_COMMAND.equalsIgnoreCase(action))
		{
			// Reviso que la baldosa elegida no este marcada, Si no lo esta entonces
			if (!auxTile.isMarked())
			{
				// Paso a revisar si dicha baldosa a descubrir tiene una mina, Si en verdad tiene una mina entonces
				if (auxTile.isMined())
				{
					// Descubro todas las boldosas con minas y doy por perdido el juego
					uncoverAllMinedTiles();
					gameState = LOSED;
				}
				else // En caso que no tenga ninguna mina entonces
				{
					// Descubro la baldosa indicada Y busco las demas baldosas cercanas que no tengan minas para destaparlas.
					seekAndUncoverTiles(fileTile, columnTile);
				}
			}
			else // En caso que si este marcada entonces
			{
				// Imprimo que primero se debe quitar la marca de la baldosa para descubrirla
				System.out.println("ERROR -> You can not uncovered a marked tile!");
			}
		}
		else // Si por otra parte el comando es marcar/desmarcar entonces
		{
			// Reviso que la baldosa indicada si este cubierta, si lo esta entonces
			if (Tile.COVERED_STATE == auxTile.getState())
			{
				// Invierto la marca de la baldosa
				if (auxTile.isMarked())
				{
					// O sea, si la baldosa estaba MARCADA pasa a DESMARCADA
					auxTile.setMark(false);
					missingMines++; // Esto implica aumentar el numero de minas por identificar
				}
				else
				{
					// Y si la baldosa estaba DESMARCADA pasa a MARCADA
					auxTile.setMark(true);
					missingMines--; // Esto implica disminuir el numero de minas por identificar 
				}
			}
			else // Si la baldosa indicada no estaba cubierta entonces
			{
				// Imprimo un mensaje de que que no se puede marcar una baldosa ya descubierta
				System.out.println("ERROR -> You can not mark/unmark a tile if it is uncovered!");
			}
		}
		
		//Una vez se ha ejecutado la accion se pasa a revisar el estado del juego, por lo que ...
		
		// Si el juego se ha perdido entonces
		if (LOSED == gameState)
		{
			// Imprimo el tablero de juego con la posicion de las minas reveladas
			clearScreen();
			System.out.println(drawGrid());
			
			// E imprimo tambien la frase YOU LOSE en arte ASCII
			System.out.println("____    ____  ______    __    __      __        ______        _______  _______ \n\\   \\  /   / /  __  \\  |  |  |  |    |  |      /  __  \\      /       ||   ____|\n \\   \\/   / |  |  |  | |  |  |  |    |  |     |  |  |  |    |   (----`|  |__   \n  \\_    _/  |  |  |  | |  |  |  |    |  |     |  |  |  |     \\   \\    |   __|  \n    |  |    |  `--'  | |  `--'  |    |  `----.|  `--'  | .----)   |   |  |____ \n    |__|     \\______/   \\______/     |_______| \\______/  |_______/    |_______|\n");
			
			return; // Por ultimo retorno, para no continuar con la ejecucion de este metodo
		}
		
		// Ahora, Si pasa aqui es porque el juego no se ha perdido por lo que entonces
		
		// Se crea e inicializa en cero una variable para contar el numero de baldosas sin descubrir
		int coveredTiles = 0;
		
		// Luego, Para toda baldosa de la fila i y columna j del tablero procedo a ...
		for (int i = 0; i < files; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				// Contar el numero de baldosas que estan cubiertas
				if (gameGrid[i][j].getState() == Tile.COVERED_STATE)
				{
					coveredTiles++;
				}
			}
		}
		
		// Finalmente, Si el numero de baldosas cubiertas es igual al numero de minas entonces
		if (coveredTiles == totalMines)
		{
			// Acabo de marcar las baldosas cubiertas, indicando con ello que ahi estan las minas y declaro el juego ganado
			markAllMinedTiles();
			gameState = WINNED;
			
			// Tambien, imprimo el tablero de juego con la posicion de las minas marcadas
			clearScreen();
			System.out.println(drawGrid());
			
			// E imprimo en arte ASCII la frase YOU WIN
			System.out.println("____    ____  ______    __    __     ____    __    ____  __   __   __ \n\\   \\  /   / /  __  \\  |  |  |  |    \\   \\  /  \\  /   / |  | |  \\ |  | \n \\   \\/   / |  |  |  | |  |  |  |     \\   \\/    \\/   /  |  | |   \\|  | \n  \\_    _/  |  |  |  | |  |  |  |      \\            /   |  | |  . `  | \n    |  |    |  `--'  | |  `--'  |       \\    /\\    /    |  | |  |\\   | \n    |__|     \\______/   \\______/         \\__/  \\__/     |__| |__| \\__|  \n");
		}
		
		// NOTA: Se puede llegar a pensar que se puede obtener un conteo de baldosas MENOR que el numero de minas, pero eso NO ES POSIBLE.
		//       Porque con cada accion se van acabando las baldosas cubiertas SIN MINAS (que al principo son mayores) y cuando se llega
		//       al punto de que tener +1 baldosa cubierta sin minas, entonces en el siguiente turno destapo la que tiene la mina (y pierdo)
		//       o destapo la que no tiene mina (y gano). Por tanto, solo debe revisar si hay igualdad.
		//
		//       Por otra parte, se piensa que todas las baldosas con mina se deben marcar para ganar pero eso no es estrictamente una condicion
		//       para ganar por lo que solo reviso el numero de baldosas cubiertas que si es el verdadero indicador de vencer al juego.
		
		// En caso que el juego, no se haya ganado o perdido ... todavia entonces aumento el turno o ronda
		turn++;
	}
	
	/**
	 * Method for drawing the current state of the board game
	 * @return a string with the "picture" and statistics of the game
	 */
	public String drawGrid()
	{
		// Para empezar, vamos a crear e inicializar una variable en donde se dibujara linea por linea la "FOTO" del juego
		StringBuilder gridScreen = new StringBuilder();
		
		// Luego, como encabezado de la foto vamos a poner las estadisticas de la partida
		gridScreen.append(String.format("Turn: %d\nTotal Mines: %d\nMissing Mines: %d\n\n", turn, totalMines, missingMines));
		
		// Ahora, para orientar al usuario vamos a dibujar los ejes en los que aumentan las filas y columnas del tablero de juego
		
		// Por lo que, se imprime el indice 1 y a continuación una linea punteada horizontal hasta que se terminan las columnas
		gridScreen.append("    1 ");
		for (int c = 0; c < columns; c++)
		{
			gridScreen.append("--");
		}
		
		// Para terminar dicho eje de las columnas, escribo: '>' indicando el sentido del incremento, el numero de la ultima columna y la etiqueta 'Columns'
		gridScreen.append(String.format("> %d Columns\n  1  \n", columns));
		
		// A continuacion, Para cada baldosa de la fila i y columna j del tablero procedo a ...
		for (int i = 0; i < files; i++)
		{
			// A imprimir por cada fila un caracter horizontal, el cual ira formando el eje de las filas
			gridScreen.append("  |   ");
			
			for (int j = 0; j < columns; j++)
			{
				// Luego, si la baldosa i,j esta cubierta entonces
				if (Tile.COVERED_STATE == gameGrid[i][j].getState())
				{
					// Reviso si esta marcada, si lo esta entonces
					if (gameGrid[i][j].isMarked())
					{
						// Agrego a esta fila de la foto el caracter de baldosa marcada
						gridScreen.append(MARKED_TILE_CARACTER);
					}
					else // sino
					{
						// Agrego a esta fila de la foto el caracter de baldosa cubierta
						gridScreen.append(UNSELECTED_TILE_CARACTER);
					}
				}
				else // En caso que la baldosa No este cubierta entonces
				{
					// Reviso si tiene una mina, Si la tiene entonces
					if (gameGrid[i][j].isMined())
					{
						// Agrego a esta fila de la foto el caracter de baldosa con mina
						gridScreen.append(MINED_TILE_CARACTER);
					}
					else // sino
					{
						// Reviso si la baldosa esta VACIA, Si lo esta entonces
						if (gameGrid[i][j].isEmpty())
						{
							// Agrego a esta fila de la foto el caracter de baldosa vacia
							gridScreen.append(DISABLED_TILE_CARACTER);
						}
						else // sino
						{
							// Agrego a esta fila de la foto el numero de minas circundantes de esta baldosa
							gridScreen.append(String.format("%d ", gameGrid[i][j].getSurroundingMines()));
						}
					}
				}
			}
			
			// Para terminar la impresion de la fila i del tablero, agrego un salto de linea
			gridScreen.append("\n");
		}
		
		// Una vez se termina la impresion del tablero, como ultimas filas de la foto y para terminar el eje de las filas, agrego: 'V' indicando el sentido del incremento, el numero de la ultima fila y la etiqueta 'Files'
		gridScreen.append(String.format("  V    \n  %d    \n Files    \n", files));
		
		// Finalmente, retorno la foto actual obtenida de la partida
		return gridScreen.toString();
	}
	
	/**
	 * Method for checking if the game has ended
	 * @return true if the game has ended, false otherwise
	 */
	public boolean checkGameOver()
	{
		// Simplemente, Si el estado del juego es PLAYING entonces
		if (gameState == PLAYING)
		{
			return false; // Retorno falso
		}
		
		return true; // Si es cualquier otro (WINNED o LOSED) entonces retorno verdadero
	}
	
	/**
	 * Method to clear output console
	 */
	public static void clearScreen()
	{
		// Pues ... simplemente imprimo 50 saltos de linea porque no hay una solucion para limpiar la consola que sea independiente de la plataforma
		System.out.println(new String(new char[50]).replace("\0", "\r\n"));
	}
}
