package exercise5two_d_arrays;

import java.util.Arrays;
import java.util.Scanner;

public class Q4 {

	public static void main(String[] args) {
		String[][] database = new String[5][3];
		Scanner keyboard = new Scanner(System.in);

		// Data entry
		for (int person = 0; person < database.length; person++) {
			System.out.println("Info for person " + (person + 1)
					+ " in name, phone, email format separated on each line");
			for (int field = 0; field < database[person].length; field++) {
				database[person][field] = keyboard.nextLine();
			}
		}

		database = sortAddresses(database);
		for (int name = 0; name < database.length; name++) {
			System.out.print(database[name][0] + "   ");
		}
		System.out.println("");
		for (int phone = 0; phone < database.length; phone++) {
			System.out.print(database[phone][1] + "   ");
		}
		System.out.println("");
		for (int email = 0; email < database.length; email++) {
			System.out.print(database[email][2] + "   ");
		}
	}

	public static String[][] sortAddresses(String[][] strings) {
		// Goes through all the elements in the word array
		for (int index = 0; index < strings.length; index++) {
			// Finds the "largest" word in the array from that point on
			int maxIndex = index;
			for (int check = index + 1; check < strings.length; check++) {
				if (strings[check][0].compareTo(strings[maxIndex][0]) < 1) {
					maxIndex = check;
				}
			}
			// Swaps the "largest" word with the one being used in the index.
			String temp0 = strings[maxIndex][0];
			String temp1 = strings[maxIndex][1];
			String temp2 = strings[maxIndex][2];

			strings[maxIndex][0] = strings[index][0];
			strings[maxIndex][1] = strings[index][1];
			strings[maxIndex][2] = strings[index][2];

			strings[index][0] = temp0;
			strings[index][1] = temp1;
			strings[index][2] = temp2;
		}
		return strings;
	}

}
