package exercise5two_d_arrays;

import java.util.Scanner;

/**
 * Gets the marks for five students in four categories, then outputs the average
 * for each student and prints it out.
 * 
 * @author Vince Ou
 * @version September 2015
 */
public class Q2 {

	public static void main(String[] args) {
		// Gets input and initializes array
		Scanner keyboard = new Scanner(System.in);
		double[][] marks = new double[5][5];

		// Inputs the marks for each student
		for (int i = 0; i < marks.length; i++) {
			System.out.println("Enter student " + (i + 1) + "'s marks");
			for (int j = 0; j < marks[i].length - 1; j++) {
				// Gets marks and adds total. The fifth index for each student
				// is their average
				double mark = keyboard.nextDouble();
				marks[i][j] = mark;
				marks[i][4] += mark;
			}
			// Gets the average from the total
			marks[i][4] /= 4.0;
		}

		// Output
		for (int i = 0; i < marks.length; i++) {
			System.out.println("Student " + (i + 1) + "'s average is "
					+ marks[i][4]);
		}
	}

}
