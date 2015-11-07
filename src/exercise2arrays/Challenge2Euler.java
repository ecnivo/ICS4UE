package exercise2arrays;

import java.util.Scanner;

public class Challenge2Euler {

	public static void main(String[] args) {
		// Input
		Scanner keyboard = new Scanner(System.in);
		System.out.println("What's the numerator?");
		int numer = keyboard.nextInt();
		System.out.println("What's the denominator?");
		int denom = keyboard.nextInt();

		// Finds the GCF
		int gcf = findGCF(numer, denom);

		// Reduces fractions
		numer /= gcf;
		denom /= gcf;

		// Prints outputs
		System.out.print("The GCF is " + gcf + " \nand the fraction is ");
		if (denom > 1)
			System.out.println(numer + "/" + denom);
		else
			System.out.println(numer);
	}

	public static int findGCF(int first, int second) {
		// Does Euclid's algorithm to find GCF
		while (first > 0 && second > 0) {
			int remainder = first % second;
			first = second;
			second = remainder;
		}
		return first;
	}

}
