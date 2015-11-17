package unit3_exercise14_queues_and_stacks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PostfixCalc {

	public static void main(String[] args) throws IOException {
		String input = new BufferedReader(new InputStreamReader(System.in))
				.readLine().trim();

		StacksExercise<Double> calcStack = new StacksExercise<Double>();

		for (int i = 0; i < input.length(); i++) {
			char nextChar = input.charAt(i);
			// if it's a number... keep looking for numbers
			if (Character.isDigit(nextChar)) {
				int nextSpaceIndex = input.indexOf(' ', i);
				double number;
				try {
					number = Double.parseDouble(input.substring(i,
							nextSpaceIndex));
				} catch (StringIndexOutOfBoundsException e) {
					System.err
							.println("INVALID INPUT. CANNOT END WITH NUMBER.");
					return;
				}

				calcStack.push(number);

				i = nextSpaceIndex;
			}
			// if it's an operator
			else {
				switch (nextChar) {
				case '+':
					calcStack.push((calcStack.pop() + calcStack.pop()));
					break;
				case '-':
					double tempSub = calcStack.pop();
					calcStack.push(calcStack.pop() - tempSub);
					break;
				case '*':
					calcStack.push((calcStack.pop() * calcStack.pop()));
					break;
				case '/':
					double tempDiv = calcStack.pop();
					calcStack.push(calcStack.pop() / tempDiv);
					break;
				case '%':
					double tempMod = calcStack.pop();
					calcStack.push(calcStack.pop() / tempMod);
					break;
				case '^':
					double tempExp = calcStack.pop();
					calcStack.push(Math.pow(tempExp, calcStack.pop()));
					break;
				default:
					return;
				}
				i++;
			}
		}
		System.out.println(calcStack.pop());
	}
}
