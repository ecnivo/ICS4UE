package exercise2arrays;

import java.util.Scanner;

/**
 * Reduces fractions with an integer denominator and numerator.
 * 
 * @author Vince Ou
 * @version September 2015
 */
public class Challenge2 {

	public static void main(String[] args) {
		// Gets input
		Scanner keyboard = new Scanner(System.in);
		System.out.println("What's the numerator?");
		int numerator = keyboard.nextInt();
		System.out.println("What's the denominator?");
		int denom = keyboard.nextInt();

		int[] nFactors = findFactors(numerator);
		int[] dFactors = findFactors(denom);

		int gcf = 1;
		boolean gcfFound = false;
		for (int index = nFactors.length - 1; index > 0 && !gcfFound; index--) {
			for (int df = dFactors.length - 1; df > 0 && !gcfFound; df--) {
				if (nFactors[index] == dFactors[df])
					gcfFound = true;

			}
		}
		
		System.out.println("GCF=" + gcf);
		numerator /= gcf;
		denom /= gcf;
		
		System.out.println(numerator + "/" + denom);
	}

	public static int[] findFactors(int number) {
		boolean[] isFactor = new boolean[number / 2];
		int noOfFactors = 0;
		for (int factor = 1; factor < isFactor.length; factor++) {
			if (number % factor == 0) {
				isFactor[factor] = true;
				noOfFactors++;
			}
		}
		if (noOfFactors == 0)
			return new int[0];
		int[] factors = new int[noOfFactors];
		int lastUsedIndex = 0;
		for (int factor = 0; factor < isFactor.length; factor++) {
			if (isFactor[factor]) {
				factors[lastUsedIndex] = factor;
				lastUsedIndex++;
			}
		}
		return factors;
	}
}
