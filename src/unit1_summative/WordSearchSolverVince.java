package unit1_summative;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Solves a word search given a list of words
 * 
 * @author Vince Ou (partnered with Gordon Guan)
 * @version September 2015
 */
public class WordSearchSolverVince {

	final static public boolean DEBUG = false;

	public static char[][] wordSearch;

	public static void main(String[] args) throws IOException {
		// Gets the user to input the location of the word search file
		String fileName;
		do {
			fileName = JOptionPane.showInputDialog(null,
					"What is the word search input file?", "File Name",
					JOptionPane.DEFAULT_OPTION);
		} while (fileName == null);
		// Initializes a timer
		long startTime = System.nanoTime();

		// Counts the number of words to look for
		Scanner lineCounter = new Scanner(new File(fileName));
		int noOfWords = 0;
		String currentWord = "";
		do {
			currentWord = lineCounter.nextLine();
			noOfWords++;
		} while (currentWord.indexOf(' ') == -1);
		noOfWords--;
		// Shows the number of words
		if (DEBUG)
			System.out.println(noOfWords + " words");

		// Gets the number of lines of the word search
		int noOfWordSearchLines = 1;
		do {
			noOfWordSearchLines++;
			lineCounter.nextLine();
		} while (lineCounter.hasNextLine());
		if (DEBUG)
			System.out.println(noOfWordSearchLines + " lines\n");
		lineCounter.close();

		wordSearch = new char[noOfWordSearchLines][noOfWordSearchLines];

		// Reads the words from the file and adds them into an array
		String[] words = new String[noOfWords];
		Scanner wordReader = new Scanner(new File(fileName));
		for (int word = 0; word < words.length; word++) {
			words[word] = wordReader.nextLine();
		}
		if (DEBUG) {
			System.out.println("List of Words:");
			for (String word : words) {
				System.out.println(word);
			}
		}

		// Reads the word search from the file and puts it into an array
		for (int row = 0; row < wordSearch.length; row++) {
			String currentRow = wordReader.nextLine();
			for (int column = 0; column < wordSearch[row].length; column++) {
				wordSearch[row][column] = currentRow.charAt(column * 2);
			}
		}
		wordReader.close();

		// Shows the array if in debug mode
		if (DEBUG) {
			System.out.println("");
			for (char[] cs : wordSearch) {
				for (char c : cs) {
					System.out.print(c + " ");
				}
				System.out.println("");
			}
			System.out.println("");
		}

		// Finds each word in the grid
		for (String word : words) {
			int[] wordData = findWord(word);
			// Gives the user an error message if the word cannot be found,
			// and stops the program.

			if (wordData[0] == -1 && wordData[1] == -1 && wordData[2] == -1) {
				JOptionPane.showMessageDialog(null,
						"Word not found!\nProgram will now quit.", "ERROR",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else {
				int insertRow = wordData[0];
				int insertColumn = wordData[1];
				int direction = wordData[2];
				// Capitalizes the first letter
				wordSearch[insertRow][insertColumn] = Character
						.toUpperCase(wordSearch[insertRow][insertColumn]);
				// Capitalizes the rest of the word, depending on the
				// direction
				for (int character = 1; character < word.length(); character++) {
					switch (direction) {
					case 0:
						insertRow--;
						break;
					case 1:
						insertColumn++;
						insertRow--;
						break;
					case 2:
						insertColumn++;
						break;
					case 3:
						insertColumn++;
						insertRow++;
						break;
					case 4:
						insertRow++;
						break;
					case 5:
						insertRow++;
						insertColumn--;
						break;
					case 6:
						insertColumn--;
						break;
					case 7:
						insertColumn--;
						insertRow--;
					}
					wordSearch[insertRow][insertColumn] = Character
							.toUpperCase(wordSearch[insertRow][insertColumn]);
				}
			}
		}

		// Prints out the word search at the end when it is done, with proper
		// capitalizations
		if (DEBUG) {
			System.out
					.println("\nWill now be printing out the found words in the array:");
			for (char[] ds : wordSearch) {
				for (char c : ds) {
					if (Character.isUpperCase(c))
						System.out.print(c + " ");
					else
						System.out.print("  ");
				}
				System.out.println("");
			}
		}

		// Re-writes the solved word search into the file
		FileWriter output = new FileWriter("solved" + fileName);
		for (String word : words) {
			output.write(word + "\n");
		}
		for (char[] rows : wordSearch) {
			for (char cell : rows) {
				output.write(cell + " ");
			}
			output.write("\n");
		}
		output.close();

		// Shows end message and statistics
		System.out.println("All done");
		long endTime = System.nanoTime();
		double milliseconds = (endTime - startTime) / 1000000;
		System.out.println("Solving done in " + milliseconds + " milliseconds");
		JOptionPane
				.showMessageDialog(null, "Word search solved in " + milliseconds
						+ " milliseconds.", "Operation Completed",
						JOptionPane.PLAIN_MESSAGE);

	}

	/**
	 * Finds a word in a 2-D array
	 * 
	 * @param word
	 *            the word to find
	 * @return
	 */
	public static int[] findWord(String word) {
		if (DEBUG)
			System.out.print("Searching for " + word);

		// Makes the if statements a lot less disgusting
		int maxRowColumn = wordSearch.length - 1;

		// Iterates through every row and column
		for (int row = 0; row < wordSearch.length; row++) {
			for (int column = 0; column < wordSearch[row].length; column++) {
				// Only "stops" at an element if the first letter is matching
				if (Character.toLowerCase(wordSearch[row][column]) == word
						.charAt(0)) {
					// Checks all eight directions (0 is up, goes clockwise, 6
					// is left, etc.)
					for (byte direction = 0; direction < 8; direction++) {
						boolean correctDirection = true;
						int compareRow = row;
						int compareColumn = column;

						// Checks every single letter in the word in that
						// direction
						for (short letter = 1; letter < word.length()
								&& correctDirection; letter++) {

							boolean advancedAhead = false;
							// Checks direction and protection against
							// ArrayIndexOutOfBoundsException
							if (direction == 0 && compareRow > 0) {
								compareRow--;
								advancedAhead = true;
							} else if (direction == 1 && compareRow > 0
									&& compareColumn < maxRowColumn) {
								compareRow--;
								compareColumn++;
								advancedAhead = true;
							} else if (direction == 2
									&& compareColumn < maxRowColumn) {
								compareColumn++;
								advancedAhead = true;
							} else if (direction == 3
									&& compareColumn < maxRowColumn
									&& compareRow < maxRowColumn) {
								compareColumn++;
								compareRow++;
								advancedAhead = true;
							} else if (direction == 4
									&& compareRow < maxRowColumn) {
								compareRow++;
								advancedAhead = true;
							} else if (direction == 5
									&& compareRow < maxRowColumn
									&& compareColumn > 0) {
								compareRow++;
								compareColumn--;
								advancedAhead = true;
							} else if (direction == 6 && compareColumn > 0) {
								compareColumn--;
								advancedAhead = true;
							} else if (direction == 7 && compareColumn > 0
									&& compareRow > 0) {
								compareRow--;
								compareColumn--;
								advancedAhead = true;
							}

							// If the direction is not correct, then skip over
							// and move onto the next direction
							if (Character
									.toLowerCase(wordSearch[compareRow][compareColumn]) != word
									.charAt(letter)
									|| !advancedAhead) {
								correctDirection = false;
							}
						}
						// Finished the check of all of the letters in one
						// direction
						if (correctDirection) {
							if (DEBUG)
								System.out.print(" ...has been found at " + row
										+ "/" + column + " facing direction "
										+ direction);
							return new int[] { row, column, direction };
						}
					}
					// Finished checking all directions for one starting letter
				}
			}
		}
		// If a word cannot be found in the array
		System.out.println(" ... HAS NOT BEEN FOUND");
		return new int[] { -1, -1, -1 };
	}
}
