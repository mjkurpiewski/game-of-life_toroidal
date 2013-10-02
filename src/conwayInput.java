/*
 * conwayInput
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
 
import java.util.Scanner;
import java.io.*;

public class conwayInput {

	//Variable objects used in the class.
	int[][]		intitialPlacement;
	int			iterations = 0;
	int[]		dimensions = new int[2];
	int			fileCounter;

	//3 character array that contains information from a config file
	//filename: config.life
	//config[0] = Live Character
	//config[1] = Dead Character
	//config[2] = (n = Normal, t = Toroidal)
	char[]		config = new char[3];

	//System.in and file scanners
	Scanner cScanner = new Scanner(System.in);
	Scanner fScanner;

	//Initial board configuration
	conwayBoard initialBoard;

	String initialPosition;
	String[] coordSplit;

	//Empty constructor, this requires no input.
	public conwayInput() {
	}

	conwayBoard generateInput() {
		//Prompt for dimensions & iterations, place in a string & split it w/ regex " "
		System.out.print("Please specify x & y dimensions, as well as iterations (## ## ##): ");
		String dimIterations = cScanner.nextLine();
		String[] diSplit = dimIterations.split(" ");

		//Run the file scanner, parse config file and place in config array.
		try {
			fScanner = new Scanner(new File("config.life"));
			fScanner.useDelimiter(" ");

			while (fScanner.hasNext()) {
				config[fileCounter] = (fScanner.next()).charAt(0);
				fileCounter++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//System.in input
		try {
			//If the user overloads parameters, throw exception & terminate.
			if (diSplit.length != 3) {
				throw new Exception("Incorrect number of arguments.");
			}
			
			//Iterate through split string, parse to int & store for later.
			//Throw an exception for bad dimensions.
			for (int i = 0; i < diSplit.length; i++) {
				if (i == 0 | i == 1) {
					dimensions[i] = Integer.parseInt(diSplit[i]);									
					if (dimensions[i] <= 0) {
						throw new Exception("Dimensions must be non-zero and positive.");	
					}
				} else  {
					iterations = Integer.parseInt(diSplit[i]);
				}	
			}
		} catch (Exception exc) {
			System.out.println("Error: " + exc.getMessage());
			System.exit(-1);
		}

		//Initialize a conwayBoard with user dimensions & config file values.
		initialBoard = new conwayBoard(dimensions[0], dimensions[1], config);

		//Grab initial live cells
		System.out.print("Input coordinates for initial live cells in X Y format (## ##), blank line terminates: ");

		//Loop until we get an empty string.
		while(true) {
			initialPosition = cScanner.nextLine();
			
			if (initialPosition.isEmpty() == true) {
				break;
			}
			
			try {
				coordSplit = initialPosition.split(" ");
				//If the user inputs more/ess than 2 arguments, terminate.
				if (coordSplit.length > 2 | coordSplit.length == 1) {
					throw new Exception("Incorrect number of arguments.");
				}
				
				//Check to make sure coordinates are positive integers.
				if (((Integer.parseInt(coordSplit[0])) < 1) | (Integer.parseInt(coordSplit[1])) < 1) {
					throw new Exception("Coordinates must be non-zero and positive.");
				}
				
				//Check to make sure the values are in the dimensions of the array.
				if ((Integer.parseInt(coordSplit[0]) > dimensions[0]) | (Integer.parseInt(coordSplit[1]) > dimensions[1])) {
					throw new Exception("Coordinates are out of bounds. Must be within array size.");
				}
				
			} catch (Exception exc) {
				System.out.println("Error: " + exc.getMessage());
				System.exit(-1);
			}

			//Add the coordinates as X to the new board.
			initialBoard.flip((Integer.parseInt(coordSplit[0])-1), (Integer.parseInt(coordSplit[1])-1));
		}

		//Return the board in its initial starting configuration.
		return initialBoard;			
	}

	//Returns the specified # of iterations.
	public int rtrnIteration() {
		return iterations;
	}

	//Return the config file contents
	public char returnConfig(int index) {
		return config[index];
	}
}
		
