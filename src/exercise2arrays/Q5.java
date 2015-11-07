package exercise2arrays;

import java.util.Scanner;

/**
 * Queries for ten integers and replaces the even numbers with '0'
 * 
 * @author Vince Ou
 * @version Sept 2015
 *
 */
public class Q5 {

	public static void main(String[] args) {
		// Input
		Scanner keyboard = new Scanner(System.in);
		int[] numbers = new int[10];

		// Puts the numbers into the array, and puts a '0' in if it's even
		for (int i = 0; i < numbers.length; i++) {
			int input = keyboard.nextInt();
			if (input % 2 == 0)
				numbers[i] = 0;
			else
				numbers[i] = input;
		}

		// Prints out values
		for (int value : numbers) {
			System.out.println(value);
		}
	}

}
