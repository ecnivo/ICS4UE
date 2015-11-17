package unit4_exercise16_sorts_and_complexity;

import java.util.Arrays;

public class Sorts {

	public static void main(String[] args) {
		int[] numbers = generateRandomNumberArray(10);
		long start = System.currentTimeMillis();
		numbers = mergeSort(numbers);
		long end = System.currentTimeMillis();

		for (int i : numbers) {
			System.out.println(i);
		}
		System.out.println("The operation took " + (end - start) + " millis");
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

	public static int[] mergeSort(int[] array) {
		if (array.length > 1) {

			int middle = (int) (array.length / 2);
			int[] front = mergeSort(Arrays.copyOfRange(array, 0, middle));
			int[] back = mergeSort(Arrays.copyOfRange(array, middle,
					array.length - 1));

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

			while (backCounter < back.length - 1) {
				returnArray[i] = back[backCounter];
				i++;
				backCounter++;
			}
			while (frontCounter < front.length - 1) {
				returnArray[i] = front[frontCounter];
				i++;
				frontCounter++;
			}

			return returnArray;
		}
		return array;

	}

}
