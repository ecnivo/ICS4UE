package unit4_exercise16_sorts_and_complexity;

import java.util.Arrays;

public class Sorts {

	public static void main(String[] args) {
		int[] numbers = generateRandomNumberArray(10);
		long start = System.nanoTime();
		numbers = mergeSort(numbers, 0, numbers.length);
		long end = System.nanoTime();

		for (int i : numbers) {
			System.out.print(i + " ");
		}
		System.out.println("The operation took " + (end - start) + " nanos");
	}

	public static int[] generateRandomNumberArray(int length) {
		boolean[] selected = new boolean[length];
		int[] returnArray = new int[length];
		for (int i = 0; i < length; i++) {
			int randomNum;
			do {
				randomNum = (int) (Math.random() * length);
			} while (selected[randomNum] == true);
			selected[randomNum] = true;
			returnArray[i] = randomNum;
		}
		return returnArray;
	}

	public static int[] mergeSort(int[] array, int start, int end) {
		if (end - start <= 1)
			return array;

		// Finds middle and splits
		int middle = (start + end) / 2;
		mergeSort(array, start, middle);
		mergeSort(array, middle, end);

		// Prepares for merging
		int[] returnArray = new int[array.length];
		int frontCounter = 0;
		int backCounter = middle;
		int i = 0;
		// Merges
		while (i < returnArray.length && frontCounter < middle
				&& backCounter < end) {
			int nextVal = Integer.MAX_VALUE;
			if (array[frontCounter] < array[backCounter]) {
				nextVal = array[frontCounter];
				frontCounter++;
			} else {
				nextVal = array[backCounter];
				backCounter++;
			}
			returnArray[i] = nextVal;
			i++;
		}

		// Merges the rest of the array if not covered
		while (frontCounter < middle) {
			returnArray[i] = array[frontCounter];
			i++;
			frontCounter++;
		}
		while (backCounter < end) {
			returnArray[i] = array[backCounter];
			i++;
			backCounter++;
		}

		return returnArray;
	}

	public static int[] mergeSort(int[] array) {
		// Splits array
		if (array.length > 1) {
			int middle = (int) (array.length / 2);
			int[] front = mergeSort(Arrays.copyOfRange(array, 0, middle));
			int[] back = mergeSort(Arrays.copyOfRange(array, middle,
					array.length - 1));

			// Finds the larger number in each of the arrays to put in the
			// return
			int[] returnArray = new int[array.length];
			int frontCounter = 0;
			int backCounter = 0;
			int i = 0;
			while (i < returnArray.length && frontCounter < front.length
					&& backCounter < back.length) {
				int nextVal;
				if (front[frontCounter] < back[backCounter]) {
					nextVal = front[frontCounter];
					frontCounter++;
				} else {
					nextVal = back[backCounter];
					backCounter++;
				}
				returnArray[i] = nextVal;
				i++;
			}

			// Copies the remaining arrays over
			while (frontCounter < front.length - 1) {
				returnArray[i] = front[frontCounter];
				i++;
				frontCounter++;
			}
			while (backCounter < back.length - 1) {
				returnArray[i] = back[backCounter];
				i++;
				backCounter++;
			}

			return returnArray;
		}
		// Returns the single number if not applicable
		return array;

	}

}
