package unit2_exercise13_linked_lists;

public class LinkedListTest {

	public static void main(String[] args) {
		MLB majorLeagueBaseball = new MLB();
		for (int i = 0; i < 150; i++) {
			majorLeagueBaseball.add(new BaseballTeam("team" + i, (int) (Math
					.random() * 100), (int) (Math.random() * 100),
					(int) (Math.random() * 100)));
		}
		System.out.println(majorLeagueBaseball);

		long startTime = System.nanoTime();
		majorLeagueBaseball.sort();
		long endTime = System.nanoTime();
		System.out.println("Completed in " + (double) ((endTime - startTime) / 1000000) + " milliseconds");
		System.out.println(majorLeagueBaseball);

		// SimpleLinkedList<String> testList = new SimpleLinkedList<String>();
		// for (int i = 0; i < 10; i++) {
		// testList.add("Node" + i);
		// }
		// System.out.println(testList);
		// System.out.println(testList.size());
		//
		// testList.swapWithNext(0);
		// System.out.println(testList);

	}
}
