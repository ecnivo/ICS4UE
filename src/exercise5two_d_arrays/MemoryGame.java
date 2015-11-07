package exercise5two_d_arrays;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * A simulation of the popular "matching pairs" card game
 * 
 * @author Vince Ou
 * @version September 2015
 */

// make the scoring system work properly, and make the
public class MemoryGame {

	final static boolean DEBUG = false;

	public static void main(String[] args) throws IOException {
		// Scores legend: Player 1 = index 0, Player -1 = index 1
		int[] pairs = { 0, 0 };
		int[] flips = { 0, 0 };
		// Keeps track of which player's turn it is
		int playerNum = 1;
		// Keeps track of the cards
		int[][] cards = new int[4][4];
		// Input
		Scanner keyboard = new Scanner(System.in);

		// Gets the high scores from the file and puts it in the RAM
		File highScoreFile = new File("memoryGameHighScores.dat");
		Scanner fileIn = new Scanner(highScoreFile);
		// High scores: ten top scores, then each has number of pairs, then
		// number of flips (stored as a
		// string), and a player name
		String[][] highScores = new String[10][3];
		for (int i = 0; i < highScores.length; i++) {
			int nextPairs = fileIn.nextInt();
			int nextFlips = fileIn.nextInt();
			fileIn.nextLine();
			highScores[i][2] = fileIn.nextLine();
			highScores[i][0] = nextPairs + "";
			highScores[i][1] = nextFlips + "";
			if (DEBUG)
				System.err.println(nextPairs + " " + nextFlips);// debug

		}
		// Tells user the current highest score
		System.out.println("The highest score so far is " + highScores[0][0]
				+ ", with " + highScores[0][1] + " flips.");
		fileIn.close();

		// Randomly generates a board
		// Goes through card numbers 1-8
		for (int insert = 1; insert <= 8; insert++) {
			// Generates two of each card
			for (int twice = 0; twice < 2; twice++) {
				int randomR = randomCoord();
				int randomC = randomCoord();
				// Guarantees no overlaps
				while (cards[randomR][randomC] != 0) {
					randomR = randomCoord();
					randomC = randomCoord();
				}
				cards[randomR][randomC] = insert;
			}
		}

		// Actual game-playing code
		do {
			// Tells user which player's turn it is
			if (playerNum == 1) {
				System.out.println("Player 1's turn");
			} else {
				System.out.println("Player 2's turn");
			}

			// Displays board for debugging purposes
			if (DEBUG) {
				System.err.println("");
				for (int[] row : cards) {
					for (int item : row) {
						System.err.print(item + " ");
					}
					System.err.println("");
				}
			}

			/*
			 * Moves legend: 0 and 1 are the first of the pair's row and col,
			 * and 2 and 3 are the second of the pair's row and column,
			 * respectively.
			 */
			int[] pairCoords = { -1, -1, -1, -1 };
			System.out.println("The board looks like this currently:");
			printBoard(cards, pairCoords);

			// Asks user for next move
			System.out
					.println("What's your next move? Put in row, then column.");
			pairCoords[0] = keyboard.nextInt();
			pairCoords[1] = keyboard.nextInt();
			// Input sanitization
			while (!isInputValid(cards, pairCoords[0], pairCoords[1])) {
				System.out
						.println("This input is not valid.\nEnter a valid coordinate in row/column format.");
				pairCoords[0] = keyboard.nextInt();
				pairCoords[1] = keyboard.nextInt();
			}
			// Shows the board as it is.
			printBoard(cards, pairCoords);

			// Asks user for second card in the guessing pair
			System.out
					.println("What will this match with? Put in row, then column.");
			pairCoords[2] = keyboard.nextInt();
			pairCoords[3] = keyboard.nextInt();
			// Sanitizes input again, with additional checks to see if the user
			// has selected the same card
			while (!isInputValid(cards, pairCoords[2], pairCoords[3])
					|| (pairCoords[2] == pairCoords[0] && pairCoords[3] == pairCoords[1])) {
				System.out
						.println("This input is not valid.\nEnter a valid coordinate in row/column format.");
				pairCoords[2] = keyboard.nextInt();
				pairCoords[3] = keyboard.nextInt();
			}
			// Shows board after second card is "turned over"
			printBoard(cards, pairCoords);

			// Increments the player's number of moves
			if (playerNum == 1) {
				flips[0]++;
			} else if (playerNum == -1) {
				flips[1]++;
			}

			// Checks if the two cards selected are the same
			if (cards[pairCoords[0]][pairCoords[1]] == cards[pairCoords[2]][pairCoords[3]]) {
				// "removes" the cards from the board if they are matching
				cards[pairCoords[0]][pairCoords[1]] = 0;
				cards[pairCoords[2]][pairCoords[3]] = 0;
				System.out.println("You found a matching pair!");

				// Adds scores as necessary to the correct player's index
				if (playerNum == 1)
					pairs[0]++;
				else
					pairs[1]++;
				System.out.println("Player 1 - " + flips[0] + " flips and "
						+ pairs[0] + " pairs.\nPlayer 2 - " + flips[1]
						+ " flips and " + pairs[1] + " pairs.");

			} else {
				// Alternate message if they did not find the correct match
				System.out
						.println("Sorry, you did not find the matching pair. Try again.");
			}
			// Switches player and "ends" their turn
			System.out.println("---------------------------\n\n\n");
			playerNum *= -1;

		} while (!isBoardEmpty(cards));

		System.out.println("--------------END GAME--------------");
		// Supposing all cards are turned over by this point
		int winPairs;
		int winFlips;
		String winningPlayerName = "";
		boolean tie = false;
		// Tells who has won/tie and sets variables appropriately
		if (pairs[0] > pairs[1]) {
			System.out.println("Player 1 has won!");
			winPairs = pairs[0];
			winFlips = flips[0];
		} else if (pairs[0] < pairs[1]) {
			System.out.println("Player 2 has won!");
			winPairs = pairs[1];
			winFlips = flips[1];
		} else {
			System.out.println("Tie game");
			winPairs = pairs[0];
			winFlips = flips[0];
			winningPlayerName = "Tie Game";
			tie = true;
		}
		if (!tie) {
			System.out.print("Please enter your name: ");
			keyboard.nextLine();
			winningPlayerName = keyboard.nextLine();
		}
		System.out.println("Thank you for playing");

		// Sorts the array
		insertHighScore(highScores, winPairs, winFlips, winningPlayerName);

		// Shows the sorted array for debugging purposes
		if (DEBUG)
			for (String[] strArr : highScores) {
				System.err.println(strArr[0] + " " + strArr[1] + " "
						+ strArr[2]);
			}
		// // Writes new high scores back into the file
		FileWriter writer = new FileWriter(highScoreFile);
		for (int person = 0; person < 10; person++) {
			writer.write(highScores[person][0] + " " + highScores[person][1]
					+ "\n" + highScores[person][2] + "\n");
		}
		writer.close();

		keyboard.close();
	}

	/**
	 * Generates a random row or column for the board
	 * 
	 * @return random integer between 0 and 3, inclusive
	 */
	public static int randomCoord() {
		return (int) (Math.random() * 4);
	}

	/**
	 * Checks if the board is empty (ie. game has finished)
	 * 
	 * @param board
	 *            the cards array that the game is being played on
	 * @return whether the board is empty or not
	 */
	public static boolean isBoardEmpty(int[][] board) {
		// Iterates through all cells
		for (int[] row : board) {
			for (int column : row) {
				// If any of them are NOT '0', then the game is not over
				if (column != 0)
					return false;
			}
		}
		// If all the searches haven't found anything aside from zeroes, then
		// the game is over
		return true;
	}

	/**
	 * Displays the board for the user
	 * 
	 * @param board
	 *            the game board
	 * @param moves
	 *            An array of the cards to "turn over"
	 */
	public static void printBoard(int[][] board, int[] moves) {
		// Goes through all the cells
		System.out.println("  0 1 2 3");
		System.out.println(" +-------");
		for (int row = 0; row < board.length; row++) {
			System.out.print(row + "|");
			for (int column = 0; column < board[row].length; column++) {
				// Check to see whether or not to reveal the card
				if ((row == moves[0] && column == moves[1])
						|| (row == moves[2] && column == moves[3]))
					System.out.print(board[row][column] + " ");
				else {
					// Depending if the spot is "empty" or not, then will
					// display accordingly
					if (board[row][column] == 0)
						System.out.print("- ");
					else
						System.out.print("* ");
				}
			}
			System.out.println("");
		}
	}

	/**
	 * Sterilizes the input for any coordinate, including if the user has chosen
	 * an empty card
	 * 
	 * @param board
	 *            the arrangement of cards
	 * @param row
	 *            the row inputted
	 * @param column
	 *            the column inputted
	 * @return whether or not the row/column is an appropriate point for the
	 *         user to "turn over" a card
	 */
	public static boolean isInputValid(int[][] board, int row, int column) {
		// Checks if row and column are within limits
		// Also if the selected space would be an empty square
		if (row < 0 || row > 3 || column < 0 || column > 3
				|| board[row][column] == 0)
			return false;
		else
			return true;
	}

	/**
	 * Inserts the high score into the high score array in its appropriate spot
	 * 
	 * @param highScores
	 *            the array of high scores already existing in the file
	 * @param winPairs
	 *            the number of matched pairs by the winning player
	 * @param winFlips
	 *            the number of pairs flipped by the winning player
	 * @param name
	 *            winning player's name
	 */
	public static void insertHighScore(String[][] highScores, int winPairs,
			int winFlips, String name) {
		// Finds the actual position using the edge of the lowest score and the
		// least number of winning flips
		int insertPosition = 0;
		while (Integer.parseInt(highScores[insertPosition][0]) >= winPairs
				&& Integer.parseInt(highScores[insertPosition][1]) < winFlips) {
			insertPosition++;
		}
		// Moves all other indices over by one
		for (int copy = highScores.length - 1; copy > insertPosition; copy--) {
			highScores[copy][0] = highScores[copy - 1][0];
			highScores[copy][1] = highScores[copy - 1][1];
			highScores[copy][2] = highScores[copy - 1][2];
		}
		// Inserts winner's info
		highScores[insertPosition][0] = winPairs + "";
		highScores[insertPosition][1] = winFlips + "";
		highScores[insertPosition][2] = name;
	}
}
