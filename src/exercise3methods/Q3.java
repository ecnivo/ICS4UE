package exercise3methods;

public class Q3 {

	/**
	 * Adds numbers together according to an operation
	 * @param numbers has to be a ten number long index of integers
	 * @param operation either "add" "subtract" "multiply" or "divide"
	 * @return the ten numbers used the operator
	 */
	public static int operate(int[] numbers, String operation) {
		int total = 0;
		if (operation.equals("add")) {
			for (int i = 0; i < numbers.length; i++) {
				total += i;
			}
		} else if (operation.equals("subtract")) {
			total = numbers[0];
			for (int i = 1; i < numbers.length; i++) {
				total -= i;
			}
		} else if (operation.equals("multiply")) {
			total = 1;
			for (int i = 0; i < numbers.length; i++) {
				total *= i;
			}
		} else if (operation.equals("add")) {
			total = numbers[0];
			for (int i = 1; i < numbers.length; i++) {
				total /= i;
			}
		}
		return total;
	}

}
