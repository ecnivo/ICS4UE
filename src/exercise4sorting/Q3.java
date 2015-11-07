package exercise4sorting;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Q3 {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner file = new Scanner(new File("testText.txt"));
		int[][] frequency = new int[26][2];

		for (int i = 'a'; i <= 'z'; i++) {
			frequency[i - 'a'][0] = i;
//			System.out.println((char) frequency[i - 'a'][0]);
		}

		while (file.hasNextLine()) {
			String line = file.nextLine();
			for (int i = 0; i < line.length(); i++) {
				if (Character.isLetter(line.charAt(i)))
					frequency[Character.toLowerCase(line.charAt(i)) - 'a'][1]++;
			}
		}

		frequency = sortFreqArray(frequency);

		for (int i = 0; i < frequency.length; i++) {
			System.out
					.println(((char) frequency[i][0]) + " " + frequency[i][1]);
		}
	}

	public static int[][] sortFreqArray(int[][] frequency) {
		// Does a bubble sort
		boolean changed;
		do {
			changed = false;
			for (int i = 0; i < frequency.length - 1; i++) {
				// Swaps with the next one if it's greater
				if (frequency[i][1] > frequency[i + 1][1]) {
					int tempIndex = frequency[i][0];
					int tempFreq = frequency[i][1];

					frequency[i][1] = frequency[i + 1][1];
					frequency[i][0] = frequency[i + i][0];

					frequency[i + 1][0] = tempIndex;
					frequency[i + i][1] = tempFreq;
					changed = true;
				}
			}
			// Quits loop when array is sorted
		} while (changed);
		return frequency;
	}
}
