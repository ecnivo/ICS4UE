package unit1_summative;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * Generates a word search from a list of words
 * 
 * @author Gordon Guan
 * @version September 2015
 */
public class WordSearchGenerator {

	/**
	 * Gets the delta of x and y from a direction, with index 0 being north,
	 * moving counter-clockwise
	 */
	private static final int[][] DIRECTION_TRANSLATE = { { 0, -1 }, { 1, -1 },
			{ 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 }, { -1, 0 }, { -1, -1 } };

	private static final double LENGTH_RECOMMEND_RATIO = 1.6;

	/**
	 * The maximum number of times to regenerate the entire word search from
	 * scratch, if there is an impossibility detected
	 */
	private static final int MAX_REGENERATIONS = 5;

	/**
	 * The maximum number of times to try to add a word into the word search
	 */
	private static long MAX_ITERATIONS;

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		try {

			JOptionPane
					.showMessageDialog(
							null,
							"<html><b>Welcome to the word search generator!</b>\n"
									+ "This program will generate a word search given a list of words and size.",
							"Word Search Generator", JOptionPane.PLAIN_MESSAGE);
			// Get the file paths and the length from the user
			String wordsFilePath = "", outputFilePath = "";
			int size;
			JFileChooser chooser = new JFileChooser();
			chooser.setSelectedFile(new File(System.getProperty("user.dir")));
			JOptionPane
					.showMessageDialog(
							null,
							"You will now select the file containing the list of words to include in the word list.\n"
									+ "Words must each be on their own line.",
							"Word Search Generator",
							JOptionPane.INFORMATION_MESSAGE);
			do {
				int wordsResult = chooser.showOpenDialog(null);
				if (wordsResult == JFileChooser.APPROVE_OPTION)
					wordsFilePath = chooser.getSelectedFile().getPath();
				else
					return;
			} while (!new File(wordsFilePath).exists());

			System.out.println("Inputting from: " + wordsFilePath);
			System.out.println("Outputting to: " + outputFilePath);

			BufferedReader wordListReader = new BufferedReader(new FileReader(
					wordsFilePath));
			// Count the number of lines in the file
			int lineCount = 0;
			while (wordListReader.readLine() != null)
				lineCount++;
			wordListReader.close();
			System.out.println("Counted " + lineCount + " words");

			// Read in the actual words into an array
			long charCount = 0;
			int maxWordLength = 0;
			wordListReader = new BufferedReader(new FileReader(wordsFilePath));
			String[] words = new String[lineCount];
			for (int wordIndex = 0; wordIndex < lineCount; wordIndex++) {
				// Sanitize input
				words[wordIndex] = wordListReader.readLine().toLowerCase()
						.replaceAll("[^a-z]", "").trim();
				charCount += words[wordIndex].length();
				// Update the longest word for later
				if (words[wordIndex].length() > maxWordLength)
					maxWordLength = words[wordIndex].length();
			}
			wordListReader.close();
			System.out.println(lineCount + " words read from file");
			System.out.println(charCount + " characters counted from file");

			// Guess a good dimension, minimum being the longest word's length
			long dimGuess = Math.max(
					(long) (Math.sqrt(charCount * LENGTH_RECOMMEND_RATIO)),
					maxWordLength);
			System.out.println("Guessing " + dimGuess
					+ " as a suitable dimension, " + maxWordLength + " is min");
			size = Integer.parseInt(JOptionPane.showInputDialog(null,
					"Enter the length (must be greater than " + maxWordLength
							+ "): ", dimGuess));
			System.out.println("Board length: " + size);

			// Check to make sure all the words fit
			if (size < maxWordLength) {
				JOptionPane
						.showMessageDialog(
								null,
								"There are words in the word list that would not fit in the dimensions given.\nThe program will now terminate.",
								"Word Search Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Calculate a statistically reasonable number for maximum
			// iterations
			MAX_ITERATIONS = size * size * 8;
			System.out
					.println("Calculated max iterations of " + MAX_ITERATIONS);

			long start = System.currentTimeMillis();
			// Sort words starting by length, so longer words are inserted first
			// This step takes the longest out of the entire operation,
			// especially for large lists of words, since it's a selection sort
			System.out.println("Beginning sort of words by length");
			String[] sortedWords = stringSelectionSortByLength(words);

			System.out.println("Finished sorting by length");

			// Try generating a word search multiple times until success
			// Retrying from the start might be required, since the first word
			// added could block off everything and not allow other words to be
			// added
			char[][] wordSearch = null;
			for (int regen = 0; regen < MAX_REGENERATIONS && wordSearch == null; regen++) {
				wordSearch = generateWordSearch(sortedWords, size);
				System.out.println("Generation " + regen
						+ " is terminated (finished/exceeded)");
			}
			long end = System.currentTimeMillis();
			System.out.println("Took " + ((end - start) / 1000.0)
					+ "s to finish/exceed generation");

			// Check if the word search successfully generated
			if (wordSearch == null) {
				JOptionPane
						.showMessageDialog(
								null,
								"The word search could not be generated.\n"
										+ "Maximum retries of "
										+ (MAX_ITERATIONS * size * size * MAX_REGENERATIONS)
										+ " exceeded.\n"
										+ "Try to use a larger size, or a smaller word list next time,\nor use the "
										+ "recommended size calculated by the program.\n"
										+ "The program will now terminate.",
								"Word Search Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Request save file location
			JOptionPane.showMessageDialog(null,
					"The word search has been generated.\n"
							+ "You will now select where to save the file.",
					"Word Search Generator", JOptionPane.INFORMATION_MESSAGE);
			int outputResult = chooser.showSaveDialog(null);
			if (outputResult == JFileChooser.APPROVE_OPTION)
				outputFilePath = chooser.getSelectedFile().getPath();
			else
				return;

			int resultUseHTML = JOptionPane.showConfirmDialog(null,
					"Use HTML formatting?", "Word Search Generator",
					JOptionPane.YES_NO_OPTION);
			System.out.println("Writing to file...");
			// Write the output into a file
			PrintStream wordSearchOutputStream = new PrintStream(new File(
					outputFilePath));
			if (resultUseHTML == JOptionPane.NO_OPTION) {
				System.out
						.println("Formatting mode set to 'normal' (the one required by the project)");
				// Write all the words first, per the format
				for (String word : words)
					wordSearchOutputStream.println(word);
				System.out.println("Word list write complete!");
				// Write the word search array
				for (char[] charArray : wordSearch) {
					for (int characterIndex = 0; characterIndex < charArray.length; characterIndex++) {
						wordSearchOutputStream.print(charArray[characterIndex]);
						// Don't add an extra space at the end of the line
						if (characterIndex != charArray.length - 1)
							wordSearchOutputStream.print(" ");
					}
					wordSearchOutputStream.println();
				}
				wordSearchOutputStream.close();
			} else {
				// Print out a nicely formatted page in HTML using Bootstrap
				System.out.println("Formatting mode set to 'HTML'");
				// Typing all of this was a pain, especially in Eclipse
				wordSearchOutputStream
						.println(""
								+ "<html>"
								+ "<head>"
								+ "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\">"
								+ "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css\">"
								+ "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js\"></script>"
								+ "<title>Word Search</title>"
								+ "<style>"
								+ "td.cell {"
								+ "    width: 23px;"
								+ "    height: 23px;"
								+ "    text-align: center;"
								+ "}"
								+ "</style>"
								+ "</head>"
								+ "<body>"
								+ "<div class=\"container\">"
								+ "<div class=\"jumbotron\">"
								+ "<h1>Word Search</h1>"
								+ "</div>"
								+ "<div class=\"panel panel-default\">"
								+ "<div class=\"panel-heading\">Word List</div>"
								+ "<div class=\"panel-body\">");
				// Print out the word list
				for (String word : words)
					wordSearchOutputStream.println(word + "<br>");
				wordSearchOutputStream.println("" + "</div></div>"
						+ "<div class=\"panel panel-default\">"
						+ "<div class=\"panel-heading\">Word Search</div>"
						+ "<div class=\"panel-body\">"
						+ "<table border=\"1\" align=\"center\">");
				// Print out the word search
				for (char[] charArray : wordSearch) {
					wordSearchOutputStream.print("<tr>");
					for (int characterIndex = 0; characterIndex < charArray.length; characterIndex++)
						wordSearchOutputStream.print("<td class=\"cell\">"
								+ Character
										.toUpperCase(charArray[characterIndex])
								+ "</td>");
					wordSearchOutputStream.println("</tr>");
				}
				wordSearchOutputStream
						.println("</div></div></table></body></html>");
			}
			System.out.println("File write complete!");
			// System.out.println(arrayToString(wordSearch));

			System.out.println("All tasks completed");
			JOptionPane.showMessageDialog(null,
					"Word search generation is complete.",
					"Word Search Complete", JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage()
					+ "\n" + "Program will now terminate.",
					"Word Search Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Attempts to generate a word search containing 'words' of size n
	 * 
	 * @param sortedWords
	 *            The words for the word search to contain, pre-sorted
	 * @param size
	 *            The size of the word search
	 * @return A word search in a char[][], or null if the attempt failed
	 */
	public static char[][] generateWordSearch(String[] sortedWords, int size) {
		char[][] wordSearch = new char[size][size];

		// Consider every word provided
		for (int wordIndex = 0; wordIndex < sortedWords.length; wordIndex++) {
			String word = sortedWords[wordIndex];
			// Attempt to find a valid arrangement for this word
			int[] arr = generateValidArrangement(wordSearch, word, size);
			if (arr == null) {
				// If no arrangement is found, then return null
				// System.out.println(arrayToString(wordSearch));
				return null;
			}

			// Transfer the word into the word search based on the arrangement
			// (x, y, dir, iterations) generated
			int charsLeft = word.length();
			while (charsLeft > 0) {
				wordSearch[arr[1]][arr[0]] = word.charAt(word.length()
						- charsLeft);
				arr[0] += DIRECTION_TRANSLATE[arr[2]][0];
				arr[1] += DIRECTION_TRANSLATE[arr[2]][1];
				--charsLeft;
			}
		}
		System.out.println("Successful generation complete!");
		// By this point, a valid word search has been generated
		// Fill the empty spots with random letters
		for (int y = 0; y < size; y++)
			for (int x = 0; x < size; x++)
				if (wordSearch[y][x] == Character.UNASSIGNED)
					wordSearch[y][x] = (char) ('a' + (int) (Math.random() * 26));
		System.out.println("Empty fill complete!");
		return wordSearch;
	}

	/**
	 * Brute forces a valid position for the given word to be inside a word
	 * search
	 * 
	 * @param wordSearch
	 *            The word search for the word to be contained in
	 * @param word
	 *            The word to check
	 * @param size
	 *            The size of the word search
	 * @return A valid arrangement with properties given in an array (x, y,
	 *         direction) (I would probably use an object for this)
	 */
	public static int[] generateValidArrangement(char[][] wordSearch,
			String word, int size) {
		// Keep generating positions until a "valid arrangement" is created,
		// or until a maximum number of iterations has been reached
		int dir, x, y;
		int iterations = 0;
		do {
			dir = (int) (Math.random() * 8);
			x = (int) (Math.random() * size);
			y = (int) (Math.random() * size);
			iterations++;
			if (iterations > MAX_ITERATIONS)
				return null;
		} while (!isValidArrangement(wordSearch, word, x, y, dir));
		// Comment out this next line for huge files
		System.out.println("Took " + iterations
				+ " iterations to place word \"" + word + "\"");
		return new int[] { x, y, dir };
	}

	/**
	 * Checks if the given parameters for the insertion of the word are valid to
	 * enter into the word search
	 * 
	 * @param wordSearch
	 *            The word search with words possibly already filled in
	 * @param word
	 *            The word to try adding
	 * @param x
	 *            The x coordinate to start adding
	 * @param y
	 *            The y coordinate to start adding
	 * @param dir
	 *            The direction to add the word in
	 * @return Whether or not the word can fit into the word search with the
	 *         given arrangement
	 */
	public static boolean isValidArrangement(char[][] wordSearch, String word,
			int x, int y, int dir) {
		// Go through every character in the word
		int charsLeft = word.length();
		while (charsLeft > 0) {
			// Make sure the next position is within bounds of the word search
			if (x < 0 || x >= wordSearch.length || y < 0
					|| y >= wordSearch.length)
				return false;
			// Make sure the current character of the word matches up with a
			// character already on the word search (overlap), or there isn't a
			// character there at all yet
			if (wordSearch[y][x] != Character.UNASSIGNED
					&& wordSearch[y][x] != word.charAt(word.length()
							- charsLeft))
				return false;
			// Go to the next position in the direction given
			x += DIRECTION_TRANSLATE[dir][0];
			y += DIRECTION_TRANSLATE[dir][1];
			--charsLeft;
		}
		return true;
	}

	/**
	 * Selection sorts an array of strings by their length
	 * 
	 * @param array
	 *            The array to sort
	 * @return The sorted array
	 */
	private static String[] stringSelectionSortByLength(String[] array) {
		String[] sorted = new String[array.length];
		// Make a copy of the array
		for (int i = 0; i < array.length; i++)
			sorted[i] = array[i];
		// Do a selection sort
		for (int front = 0; front < sorted.length - 1; front++) {
			int smallestIdx = front;
			for (int pos = front + 1; pos < sorted.length; pos++)
				if (sorted[pos].length() > sorted[smallestIdx].length())
					smallestIdx = pos;
			// Do a swap of the front and the smallest
			String temp = sorted[front];
			sorted[front] = sorted[smallestIdx];
			sorted[smallestIdx] = temp;
		}
		return sorted;
	}

	/**
	 * Prints an array for debugging purposes
	 * 
	 * @param array
	 *            The array to print
	 * @return The array as a string
	 */
	public static String arrayToString(char[][] array) {
		String s = "";
		for (char[] ca : array) {
			for (char c : ca)
				s += c + " ";
			s += "\n";
		}
		return s;
	}
}
