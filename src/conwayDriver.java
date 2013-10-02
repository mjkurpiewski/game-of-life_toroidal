/*
 * conwayDriver
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
 
import java.util.Arrays;

public class conwayDriver {

	public static void main(String[] args) {
		boolean sameBoard = false;
		int i = 1;		

		//Objects used for execution
		conwayBoard oldBoard;
		conwayBoard gameBoard;
		conwayIterator itr;
		//Gather input
		conwayInput boardGenerator = new conwayInput();

		//Generate new board from the input
		gameBoard = boardGenerator.generateInput();

		//Initialize the iterator with the initial board.
		itr = new conwayIterator(gameBoard);
		
		//If iterations are 0 or negative, run until the board stops changing.
		if (boardGenerator.rtrnIteration() <= 0) {
			while (sameBoard == false) {
				gameBoard.returnBoard();
				
				oldBoard = gameBoard;

				gameBoard = itr.nextIteration();		
	
				System.out.println("Generation #: " + i);

				//Since 2d array, check deep equality on the actual board matrices.
				if (Arrays.deepEquals(oldBoard.returnMatrix(), gameBoard.returnMatrix())) {
					sameBoard = true;
				}
				
				i++;
				//Feed in the new board to the iterator.
				itr.inputBoard(gameBoard);
			}
		//If iterations are positive integers, run for that number of iterations.
		} else {
			for (i = 0; i < boardGenerator.rtrnIteration(); i++) {
				gameBoard.returnBoard();
				gameBoard = itr.nextIteration();
				
				System.out.println("Generation #: " + (i + 1));
				
				itr.inputBoard(gameBoard);
			}
		}
	}
}
