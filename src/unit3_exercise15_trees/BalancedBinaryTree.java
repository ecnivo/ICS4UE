package unit3_exercise15_trees;

public class BalancedBinaryTree<E extends Comparable<E>> extends BinaryTree<E> {

	public BalancedBinaryTree() {
		super();
	}

	public boolean rotateRight(BinaryTreeNode<E> pivotRoot) {
		BinaryTreeNode<E> pivot = pivotRoot.getRightChild();
		if (pivotRoot == null || pivot == null)
			return false;
		System.out.println(pivotRoot.getData() + " is rotating right with "
				+ pivot.getData());
		BinaryTreeNode<E> parent = pivotRoot.getParent();
		BinaryTreeNode<E> leftChild = pivot.getLeftChild();

		if (parent != null) {
			// sets pivot parent
			pivot.setParent(parent);

			// sets parent child
			if (parent.getLeftChild() == pivotRoot)
				parent.setLeftChild(pivot);
			else
				parent.setRightChild(pivot);
		} else
			setRoot(pivot);

		// moves things up and down
		pivot.setLeftChild(pivotRoot);
		pivotRoot.setParent(pivot);

		// sets leftChild and parent
		if (leftChild != null) {
			leftChild.setParent(pivotRoot);
			pivotRoot.setRightChild(leftChild);
		}

		return true;
	}

	public boolean rotateLeft(BinaryTreeNode<E> pivotRoot) {
		BinaryTreeNode<E> pivot = pivotRoot.getLeftChild();
		if (pivotRoot == null || pivot == null)
			return false;
		System.out.println(pivotRoot.getData() + " is rotating  left with "
				+ pivot.getData());
		BinaryTreeNode<E> parent = pivotRoot.getParent();
		BinaryTreeNode<E> rightChild = pivot.getRightChild();

		if (parent != null) {
			// sets pivot parent
			pivot.setParent(parent);

			// sets parent child
			if (parent.getLeftChild() == pivotRoot)
				parent.setLeftChild(pivot);
			else
				parent.setRightChild(pivot);
		} else
			setRoot(pivot);

		// moves things up and down
		pivot.setRightChild(pivotRoot);
		pivotRoot.setParent(pivot);

		// sets leftChild and parent
		if (rightChild != null) {
			rightChild.setParent(pivotRoot);
			pivotRoot.setLeftChild(rightChild);
		}

		return true;
	}

	private void balanceUp(BinaryTreeNode<E> node) {
		if (node.getParent() == null)
			return;
		else {
			// Checks to see if any particular node from the bottom to the root
			// is balanced
			if (isBalanced(node) >= 2) {
				rotateLeft(node);
			} else if (isBalanced(node) <= -2) {
				rotateRight(node);
			}

			balanceUp(node.getParent());
		}
	}

	private int isBalanced(BinaryTreeNode<E> node) {
		int leftDepth, rightDepth;

		if (node.getLeftChild() != null) {
			leftDepth = getMaxDepth(node.getLeftChild()) - node.getDepth();
		} else {
			leftDepth = 0;
		}

		if (node.getRightChild() != null) {
			rightDepth = getMaxDepth(node.getRightChild()) - node.getDepth();
		} else {
			rightDepth = 0;
		}

		return leftDepth - rightDepth;
	}

	public void addAndBalance(E data) {
		balanceUp(super.add(data));
	}
}
