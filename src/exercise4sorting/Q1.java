package exercise4sorting;

import java.util.Scanner;

/**
 * Program accepts ten numbers, then sorts them using a bubble sort.
 * 
 * @author Vince Ou
 * @version September 2015
 */
public class Q1 {

	public static void main(String[] args) {
		// Initialize
		Scanner keyboard = new Scanner(System.in);
		System.out.println("enter ten numbers");
		int[] numbers = { keyboard.nextInt(), keyboard.nextInt(), keyboard.nextInt(), keyboard.nextInt(),
				keyboard.nextInt(), keyboard.nextInt(), keyboard.nextInt(), keyboard.nextInt(), keyboard.nextInt(),
				keyboard.nextInt() };

		numbers = sortNumbers(numbers);

		// Output
		for (int i = 0; i < numbers.length; i++) {
			System.out.println(numbers[i]);
		}
	}

	/**
	 * Bubble sorts the array
	 * 
	 * @param numbers
	 *            array of however many integer needed.
	 * @return the sorted array
	 */
	public static int[] sortNumbers(int[] numbers) {
		// Does a bubble sort
		boolean changed;
		do {
			changed = false;
			for (int i = 0; i < numbers.length - 1; i++) {
				// Swaps with the next one if it's greater
				if (numbers[i] > numbers[i + 1]) {
					int temp = numbers[i];
					numbers[i] = numbers[i + 1];
					numbers[i + 1] = temp;
					changed = true;
				}
			}
			// Quits loop when array is sorted
		} while (changed);
		return numbers;
	}

}
