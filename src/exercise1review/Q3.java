package exercise1review;

import java.util.Arrays;
import java.util.Scanner;

public class Q3 {

	public static void main(String[] args) {
		// Sets up input
		Scanner keyboard = new Scanner(System.in);
		String input = keyboard.nextLine();

		// Cleans the user input of all characters except letters and spaces
		int inputLength = input.length();
		String inputClean = "";
		for (int i = 0; i < inputLength; i++) {
			char workingChar = input.charAt(i);
			if (Character.isLetter(workingChar) || workingChar == ' ')
				inputClean += String
						.valueOf(Character.toLowerCase(workingChar));
		}
		inputClean = inputClean.trim();

		System.out.println("done cleaning");

		// Finds number of spaces (and therefore words) in the string
		String spaceCounter = inputClean;
		int noOfWords = 1;
		while (spaceCounter.indexOf(' ') != -1) {
			int spaceIndex = spaceCounter.indexOf(' ');
			noOfWords++;
			spaceCounter = spaceCounter.substring(spaceIndex).trim();
		}

		System.out.println("done counting words");

		// Creates a new array for all the words in the input
		String[] words = new String[noOfWords];
		for (int i = 0; i < noOfWords - 1; i++) {
			words[i] = inputClean.substring(0, inputClean.indexOf(' ')).trim();
			inputClean = inputClean.substring(inputClean.indexOf(' ')).trim();
		}
		words[words.length - 1] = inputClean;

		System.out.println("done putting words into the array");

		// Sorts and prints the array of words
		Arrays.sort(words);
		for (int workingWord = 0; workingWord < words.length; workingWord++) {
			System.out.print(words[workingWord] + " ");
		}
	}
}
