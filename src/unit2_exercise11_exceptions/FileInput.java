package unit2_exercise11_exceptions;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileInput {
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("test.txt"));
			String line;
			while ((line = br.readLine()) != null)
				System.out.println(line);
		} catch (Exception e) {
			System.out.println("YOU DUMB, THE FILE DOESN'T WORK");
		}
	}
}
