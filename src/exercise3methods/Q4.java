package exercise3methods;

import java.util.Scanner;

/**
 * Asks for input string and n, and outputs every nth letter as upper case, rest
 * as lower case.
 * 
 * @author Vince Ou
 * @version September 2015
 */
public class Q4 {

	public static void main(String[] args) {
		// Input
		Scanner keyboard = new Scanner(System.in);
		String input = keyboard.nextLine();
		int n = keyboard.nextInt();
		// Error checking, sterilizing input
		if (n == 0 || n > input.length())
			return;

		// Creates output string and outputs
		String output = "";
		for (int i = 0; i < input.length(); i++) {
			if ((i + 1) % n == 0)
				output += Character.toUpperCase(input.charAt(i));
			else
				output += Character.toLowerCase(input.charAt(i));
		}
		System.out.println(output);
	}
}
