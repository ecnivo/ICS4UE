package unit1_summative;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class RandomWordChooser {

	public static void main(String[] args) throws IOException {
		String inputFile = "dictionary.txt";
		Scanner keyboard = new Scanner(System.in);
		System.out.println("What is your output file?");
		String outFile = keyboard.nextLine();

		Scanner lineRead = new Scanner(new File(inputFile));
		int noOfLines = 0;
		while (lineRead.hasNextLine()) {
			lineRead.nextLine();
			noOfLines++;
		}
		lineRead.close();
		String[] words = new String[noOfLines];
		Scanner wordRead = new Scanner(new File(inputFile));
		for (int i = 0; i < words.length; i++) {
			words[i] = wordRead.nextLine();
		}
		wordRead.close();

		System.out.println("How many words do you want?");
		int noOfWords = keyboard.nextInt();
		keyboard.close();

		String[] outWords = new String[noOfWords];
		for (int i = 0; i < outWords.length; i++) {
			outWords[i] = words[(int) (Math.random() * words.length)];
		}

		FileWriter writer = new FileWriter(new File(outFile));
		for (int i = 0; i < outWords.length; i++) {
			writer.write(outWords[i] + "\n");
		}
		writer.close();
		
		System.out.println("Done!");
	}

}
