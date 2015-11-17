package unit3_exercise15_trees;

public class BinaryTreeTest {

	public static void main(String[] args) {
		final int ELEMENTS = 25;
		BalancedBinaryTree<Integer> tree = new BalancedBinaryTree<Integer>();

		// boolean[] chosen = new boolean[ELEMENTS];
		//
		// for (int i = 1; i <= ELEMENTS; i++) {
		// int insertNum;
		// do {
		// insertNum = (int) (Math.random() * ELEMENTS);
		// } while (chosen[insertNum] == true);
		// chosen[insertNum] = true;
		// tree.add(insertNum);
		// }

		System.out.println("NEW TREE\n");

//		int[] numbers = { 11, 8, 3, 21, 24, 12, 18, 2, 4, 13, 23, 7, 6, 15, 1,
//				19, 10, 0, 9, 14, 5, 17, 16, 22, 20 };
//		tree.clear();
		int[] simpleNumbers = {11,8,3,2};
		for (int i = 0; i < simpleNumbers.length; i++) {
			tree.addAndBalance(simpleNumbers[i]);
			System.out.println(tree);
		}
		
		System.out.println(tree);
	}
}
