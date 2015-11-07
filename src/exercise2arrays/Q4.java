package exercise2arrays;

import java.util.Scanner;

/**
 * Displays a random name from ten entries
 * 
 * @author Vince Ou
 * @version September 2015
 */
public class Q4 {

	public static void main(String[] args) {
		// Sets up input
		Scanner keyboard = new Scanner(System.in);
		String[] names = new String[10];

		// Name entry
		for (int i = 0; i < names.length; i++) {
			names[i] = keyboard.next();
		}

		// Generates a random number between 1-10 and prints out the element in
		// that index
		int randomIndex = (int) Math.floor(Math.random() * 10);
		System.out.println(names[randomIndex]);
	}

}
