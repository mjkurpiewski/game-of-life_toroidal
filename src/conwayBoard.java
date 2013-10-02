/*
 * conwayBoard
 *
 * 2/18/2013
 *
 * CS280-004 SPRING 2013
 *
 * Project 1: Conway's Game of Life
 *
 * Peter J Kurpiewski (pjk26)
 */

public class conwayBoard {

	//Declare variables for live/dead state characters
	char dead;
	char live;
	
	//Configuration variables from config.life
	char[] config = new char[3]; 

	//Declare the board & dimension variables.
	char[][] lifeBoard;
	int      xBoardLen;
	int      yBoardLen;

	//Default constructor, creates a board of size 10x10 if no input.
	public conwayBoard() {
		xBoardLen = 10;
		yBoardLen = 10;
		live = 'X';
		dead = '_';

		lifeBoard = new char[xBoardLen][yBoardLen];

		for (int i = 0; i < xBoardLen; i++) {
			for (int j = 0; j < yBoardLen; j++) {
				lifeBoard[i][j] = dead;
			}
		}
	}

	//Contruct a board with the given dimensions.
	//Default state: all cells dead.
	public conwayBoard(int xLength, int yLength, char[] configuration) {
		xBoardLen = xLength;
		yBoardLen = yLength;
		config = configuration;
		live = configuration[0];
		dead = configuration[1];

		lifeBoard = new char[xBoardLen][yBoardLen];

		for (int i = 0; i < xBoardLen; i++) {
			for (int j = 0; j < yBoardLen; j++) {
				lifeBoard[i][j] = dead;
			}
		}
	} 

	//Flip the value @ coordinate pair. Live/Dead or Dead/Live.
	void flip(int x, int y) {
		if (lifeBoard[x][y] == dead) {
			lifeBoard[x][y] = live;
		} else {
			lifeBoard[x][y] = dead;
		}
	}

	//Return a int[2] array w/ current board dimensions.
	public int[] returnDimensions() {
		int[] dimensions = new int[2];
		dimensions[0] = xBoardLen;
		dimensions[1] = yBoardLen;

		return dimensions;
	}
	//Return config to pass to other objects.
	public char[] returnConfig() {
		return config;
	}

	//Write an updated line into the board.
	public void writeLine(char[] newLine, int lineNumber) {
		for (int i = 0; i < xBoardLen; i++)
			lifeBoard[i][lineNumber] = newLine[i];				
	}

	//Returns the requested line
	public char[] returnLine(int lineNumber) {
		char[] lineBuffer = new char[xBoardLen];
		for (int i = 0; i < xBoardLen; i++)
			lineBuffer[i] = lifeBoard[i][lineNumber];

		return lineBuffer;
	}

	//Is the board dead?
	public boolean isDead() {
		int lifecounter = 0;

		for (int i = 0; i < xBoardLen; i++) {
			for (int j = 0; j < yBoardLen; j++) {
				if (lifeBoard[i][j] == 'X') {
					lifecounter++;
				}
			}
		}

		if (lifecounter == 0) {
			return true;
		}
		
		return false;
	}

	public char[][] returnMatrix() {
		return lifeBoard;
	}

	//Print out the current board.
	void returnBoard() {
		System.out.print("\n");
		for (int i = 0; i < yBoardLen; i++) {
			for (int j = 0; j < xBoardLen; j++) {
				System.out.print(lifeBoard[j][i] + " ");
			}
			System.out.print("\n");
		}
	}
}
