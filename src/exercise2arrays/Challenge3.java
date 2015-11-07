package exercise2arrays;


public class Challenge3 {

	public static boolean isMagicSquare(int[][] square) {
		// Each number should appear only once
		boolean[] usedNumber = new boolean[26];
		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square[i].length; j++) {
				if (square[i][j] <= 25 && square[i][j] >= 1) {
					if (usedNumber[square[i][j]])
						return false;
					else
						usedNumber[square[i][j]] = true;
				}
			}
		}
		usedNumber[0] = true;

		for (int i = 0; i < usedNumber.length; i++) {
			if (!usedNumber[i]) {
				return false;
			}
		}
		
		// Checks each row and column is the same
		int numberTotal = 0;
		int rowTotal = 0;
		int columnTotal = 0;
		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square[i].length; j++) {
				
			}
		}
		
		return false; // get rid of this when you're done
	}

}
