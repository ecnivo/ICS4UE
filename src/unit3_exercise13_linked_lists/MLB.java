package unit3_exercise13_linked_lists;

public class MLB extends SimpleLinkedList<BaseballTeam> {

	public MLB() {

	}

	public void sort() {
		boolean sorted = true;
		int iterations = size() - 1;
		do {
			sorted = true;
			for (int index = 0; index < iterations; index++) {
				if (get(index).getWins() - get(index + 1).getWins() < 0) {
					swapWithNext(index);
					sorted = false;
				}
			}
		} while (!sorted);
	}
}
