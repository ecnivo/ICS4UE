package unit4_exercise16_sorts_and_complexity;

public class Sorts {

	static int[] numbers;

	public static void main(String[] args) {
		 numbers = generateRandomNumberArray(1000);
//		numbers = new int[] { 6, 2, 5, 0, 4, 3, 1 };
		long start = System.nanoTime();
		mergeSort();
		long end = System.nanoTime();

		printArray(numbers);
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

	public static void mergeSort() {
		mergeSort(0, numbers.length - 1);
	}

	public static void mergeSort(int front, int back) {
		if (front >= back)
			return;

		int middle = front + (back - front) / 2;

		mergeSort(front, middle);
		mergeSort(middle + 1, back);

		int[] copy = new int[numbers.length];
		for (int i = front; i <= back; i++) {
			copy[i] = numbers[i];
		}

		int frontI = front;
		int backI = middle + 1;
		int insI = front;
		while (frontI <= middle && backI <= back) {
			if (copy[frontI] <= copy[backI]) {
				numbers[insI] = copy[frontI];
				frontI++;
			} else {
				numbers[insI] = copy[backI];
				backI++;
			}
			insI++;
		}
		while (frontI <= middle) {
			numbers[insI] = copy[frontI];
			insI++;
			frontI++;
		}
	}

	public static int[] insertSort(int[] array) {
		for (int curr = 1; curr < array.length; curr++) {
			int compare = curr;
			while (array[compare] >= array[curr] && compare >= 1) {
				compare--;
				System.out.println("comparing " + array[curr] + " with "
						+ array[compare]);
			}
			int moving = array[curr];

			System.out.println("inserting " + array[curr] + " at " + compare);
			for (int shift = curr; shift > compare; shift--) {
				array[shift] = array[shift - 1];
			}
			array[compare] = moving;
		}
		return array;
	}

	public static int[] quickSort(int[] array) {
		return quickSort(array, array.length);
	}

	public static int[] quickSort(int[] array, int usableLength) {
		if (usableLength == 2) {
			if (array[0] > array[1])
				return new int[] { array[1], array[0] };
			else
				return array;
		} else if (usableLength <= 1) {
			return array;
		}

		int pivot = array[usableLength / 2];
		int pivotOccur = 1;

		int[] front = new int[array.length];
		int[] back = new int[array.length];

		int frontI = 0;
		int backI = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] < pivot) {
				front[frontI] = array[i];
				frontI++;
			} else if (array[i] > pivot) {
				back[backI] = array[i];
				backI++;
			} else
				pivotOccur++;
		}

		front = quickSort(front, frontI);
		back = quickSort(back, backI);

		int i = 0;
		while (i < array.length && i < front.length) {
			array[i] = front[i];
			i++;
		}
		int q = 0;
		while (q < pivotOccur) {
			array[i] = pivot;
			i++;
			q++;
		}
		int middle = i;
		i = 0;
		while (i < back.length) {
			array[middle + i] = back[i];
		}

		return array;
	}

	public static void printArray(int[] array) {
		for (int i : array) {
			System.out.print(i + " ");
		}
		System.out.println("");
	}

}
