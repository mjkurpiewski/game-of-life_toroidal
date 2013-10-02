/*
 * conwayIterator
 *
 * 2/18/2013
 *
 * CS280-004 SPRING 2013
 *
 * Project 1: Conway's Game of Life
 *
 * Peter J Kurpiewski (pjk26)
 *
 */
 
import java.lang.*;

public class conwayIterator {

	//Read through constructor, mirrors what the current board is before changes.
	conwayBoard currentBoard;
	//Board after iterating.
	conwayBoard newBoard;

	//Set characters for live & dead.
	char live;
	char dead;
	char type;

	//Configuration values
	char[] config;

	//Linebuffers for updating the matrix.
	char[][] buffers;

	//Line counter & column counter
	int	 lineCounter;
	int	 xCounter;
	int	 surrLife;

	//Integer array to hold the dimensions in the board, set on construction (board size never changes),
	int[]	 dimensions = new int[2];

	//Contructor, requires a conwayBoard input.
	//Reads in the board, dimensions, inits 1st counter
	//inits 3 line buffers
	public conwayIterator(conwayBoard incomingBoard) {
		currentBoard = incomingBoard;
		dimensions = currentBoard.returnDimensions();
		buffers = new char[dimensions[0]][3];
		lineCounter = 0;
		config = currentBoard.returnConfig();
		live = config[0];
		dead = config[1]; 
		type = config[2];
	}

	//Update the board to the current version before iterating.
	public void inputBoard(conwayBoard incomingBoard) {
		currentBoard = incomingBoard;
	}

	//Function to copy a line from the board to the buffer.
	public void lineCopy(int destBuffer, int rowNumber) {
		char[] tempbuffer = new char[(dimensions[0] - 1)];

		tempbuffer = currentBoard.returnLine(rowNumber); 

		for (int i = 0; i < (dimensions[0]); i++) {
			buffers[i][destBuffer] = tempbuffer[i];
		}
	}

	//Inject dead line into buffers if running normally
	public void injectDeadLine(int bufferNumber) {
		for (int i = 0; i < dimensions[0]; i++) {
			buffers[i][bufferNumber] = dead;
		}
	}

	//Load buffers with target line & it's surroundings.
	//If type is 'n' in config file, inject a line of dead cells
	//into the buffers above and below the bounds of the game board
	public void fillBuffers(int rowNumber) {
		if (rowNumber == 0){
			if (type == 'n') {
				injectDeadLine(0);
			} else {
				lineCopy(0, (dimensions[1] - 1));
			}
			lineCopy(1, rowNumber);
			lineCopy(2, (rowNumber + 1));
		} else if (rowNumber == (dimensions[1] - 1)) {
			lineCopy(0, (rowNumber - 1));
			lineCopy(1, rowNumber);
			
			if (type == 'n') {
				injectDeadLine(2);
			} else {
				lineCopy(2, 0);
			}
		} else {
			lineCopy(0, (rowNumber - 1));
			lineCopy(1, rowNumber);
			lineCopy(2, (rowNumber + 1));
		}
	}

	//Test the conditions of the cell.
	public char updateCell(int xCoordinate) {
		int left;
		int right;
		surrLife = 0;

		//To account for looping around left to right
		//If at one extreme, adjust what's left or right of it
		if (xCoordinate == 0) {
			left = (dimensions[0] - 1);
			right = (xCoordinate + 1);
		} else if (xCoordinate == (dimensions[0] - 1)) {
			right = 0;
			left = (xCoordinate - 1);
		} else { 
			left = (xCoordinate - 1);
			right = (xCoordinate + 1);
		}

		//Loop through the 6 elements to the left and right of current cell
		//Increase the surrounding life counter if they are occupied
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				if ((i == 0) && (buffers[left][j] == live )) {
					surrLife++;
				} else if ((i == 1) && (buffers[right][j] == live )) {
					surrLife++;
				}
			}
		}

		//Loop through directly above and directly below the current cell
		for (int i = 0; i < 3; i+=2) {
			if (buffers[xCoordinate][i] == live) {
				surrLife++;
			}
		}

		//Logical tests for what the cell's status will be
		//Rules follow those outlined in the project description
		if ((buffers[xCoordinate][1] == dead) && (surrLife == 3)) {
			return live;
		} else if ((buffers[xCoordinate][1] == live) && (surrLife < 2)) {
			return dead;
		} else if ((buffers[xCoordinate][1] == live) && (surrLife > 3)) {
			return dead;
		} else if ((buffers[xCoordinate][1] == live)) {
			return live;
		}
		
		return buffers[xCoordinate][1];
	}
	
	//Produce the next board iteration.
	public conwayBoard nextIteration() {
		//Write the updated line to here, then insert into new board
		char[] newLine = new char[dimensions[0]];
	
		//Initialize the new board to output
		newBoard = new conwayBoard(dimensions[0], dimensions[1], config);

		//Loop through y rows, filling buffers each time.
		for (int i = 0; i < dimensions[1]; i++) {
			fillBuffers(i);

			//Check the states of all cells from line i
			//Write the new states into the new line
			for (int j = 0; j < dimensions[0]; j++) {
				newLine[j] = updateCell(j);
			}

			//Write the generated new line to the new board
			newBoard.writeLine(newLine, i);
		}

		//Return the finished board
		return newBoard;
	}
}
