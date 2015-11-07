package exercise2arrays;

import java.util.Scanner;

/**
 * Gets ten numbers and finds their greatest, average.
 * 
 * @author Vince Ou
 * @version September 2015
 */
public class Q3 {

	public static void main(String[] args) {
		// Gets input
		Scanner keyboard = new Scanner(System.in);

		double[] numbers = new double[10];

		// finds the largest and total while adding them to the array
		double highest = Double.NEGATIVE_INFINITY;
		double total = 0;
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = keyboard.nextDouble();
			total += numbers[i];
			if (numbers[i] > highest)
				highest = numbers[i];
		}
		// Calculates and displays results
		double average = total / 10.0;
		System.out.println("Average is: " + average);
		System.out.println("Max is: " + highest);
	}

}
