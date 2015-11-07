package exercise4sorting;

import java.util.Scanner;

/**
 * Uses a selection sort to sort an array of ten words
 * 
 * @author Vince Ou
 * @version September 2015
 */
public class Q2 {

	public static void main(String[] args) {
		// Initialize and get inputs
		Scanner keyboard = new Scanner(System.in);
		System.out.println("enter ten words");
		String[] words = { keyboard.next(), keyboard.next(), keyboard.next(), keyboard.next(), keyboard.next(),
				keyboard.next(), keyboard.next(), keyboard.next(), keyboard.next(), keyboard.next() };

		words = selectSortStrings(words);

		for (String word : words)
			System.out.println(word);
	}

	/**
	 * Uses a seletion sort to sort an array of Strings
	 * 
	 * @param words
	 *            must be an array of Strings
	 * @return the words in sorted order
	 */
	public static String[] selectSortStrings(String[] words) {
		// Goes through all the elements in the word array
		for (int index = 0; index < words.length; index++) {
			// Finds the "largest" word in the array from that point on
			int maxIndex = index;
			for (int check = index + 1; check < words.length; check++) {
				if (words[check].compareTo(words[maxIndex]) < 1) {
					maxIndex = check;
				}
			}
			// Swaps the "largest" word with the one being used in the index.
			String temp = words[maxIndex];
			words[maxIndex] = words[index];
			words[index] = temp;
		}
		return words;
	}
}
