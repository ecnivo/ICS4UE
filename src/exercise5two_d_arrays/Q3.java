package exercise5two_d_arrays;

import java.util.Scanner;

/**
 * a program the displays a grid (4 rows, 5 columns) on the screen ask the user
 * for a coordinate and then change the corresponding spot on the grid to an 'x'
 * and redisplay. The program should quit when ALL coordinates have become an
 * 'x'.
 * 
 * @author Vince Ou
 * @version Sept 2015
 */
public class Q3 {

	public static void main(String[] args) {
		// Init the grid and Scanner
		char[][] grid = { { '*', '*', '*', '*', '*' },
				{ '*', '*', '*', '*', '*' }, { '*', '*', '*', '*', '*' },
				{ '*', '*', '*', '*', '*' } };
		Scanner keyboard = new Scanner(System.in);

		// Checks if array is full
		while (!isArrayFull(grid)) {
			// Displays array
			System.out.println("Grid currently looks like this:");
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					System.out.print(grid[i][j]);
				}
				System.out.println("");
			}
			// Asks and changes array to user's inputs
			System.out
					.println("What is your next coordinate? row column format");
			grid[keyboard.nextInt()][keyboard.nextInt()] = 'X';
		}
	}

	/**
	 * Finds out if the array is full.
	 * 
	 * @param array
	 *            the 2d char array
	 * @return if the array is covered with 'X' or not
	 */
	public static boolean isArrayFull(char[][] array) {
		// Goes through the array
		for (char[] cs : array) {
			for (char c : cs) {
				// If there are any asterisks, then it's not full
				if (c == '*')
					return false;
			}
		}
		// If all tests are negative, then it's all full.
		return true;
	}
}
