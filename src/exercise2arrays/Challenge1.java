package exercise2arrays;

import java.util.Scanner;

/**
 * Asks user for a series of numbers between 0-100 until they enter '0', after
 * which the frequency of each number entered is displayed
 * 
 * @author Vince Ou
 * @version Sept 2015
 */
public class Challenge1 {

	public static void main(String[] args) {
		// Input
		Scanner keyboard = new Scanner(System.in);
		int[] frequencies = new int[100];

		// Starts getting numbers, and stops when they enter a '0'
		int input;
		do {
			input = keyboard.nextInt();
			frequencies[input]++;
		} while (input != 0);

		// Prints outputs and such as necessary.
		for (int i = 0; i < frequencies.length; i++) {
			System.out.println(i + " occurred " + frequencies[i] + " times.");
		}
	}

}
