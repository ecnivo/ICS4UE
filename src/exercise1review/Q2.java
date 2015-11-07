package exercise1review;

import java.util.Scanner;

/**
 * A program that asks the user for a postal code and store it in a string. Adds
 * up all the numbers in the postal code and outputs the answer to the screen.
 * 
 * @author Vince Ou
 * @version September 2015
 */
public class Q2 {

	public static void main(String[] args) {
		// Gets a keyboard input reader
		Scanner keyboard = new Scanner(System.in);
		String postCode = keyboard.nextLine();

		// Iterates through postal code, and if character is a number, then adds
		// it to a total value.
		int total = 0;
		int codeLength = postCode.length();
		for (int i = 0; i < codeLength; i++) {
			char workingChar = postCode.charAt(i);
			if (Character.isDigit(workingChar))
				total += Integer.parseInt(workingChar + "");

		}
		// Shows the total value
		System.out.println(total);
	}

}
