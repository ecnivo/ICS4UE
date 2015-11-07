package unit2_exercise15_trees;

public class BinaryTreeNode<E extends Comparable<E>> {

	private E data;
	private BinaryTreeNode<E> leftChild;
	private BinaryTreeNode<E> rightChild;
	private BinaryTreeNode<E> parent;
	private int depth;

	public BinaryTreeNode(E data, BinaryTreeNode<E> leftNode,
			BinaryTreeNode<E> rightNode, BinaryTreeNode<E> parent, int depth) {
		super();
		this.data = data;
		this.leftChild = leftNode;
		this.rightChild = rightNode;
		this.parent = parent;
		this.depth = depth;
	}

	public BinaryTreeNode<E> getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(BinaryTreeNode<E> leftNode) {
		this.leftChild = leftNode;
	}

	public BinaryTreeNode<E> getRightChild() {
		return rightChild;
	}

	public void setRightChild(BinaryTreeNode<E> rightNode) {
		this.rightChild = rightNode;
	}

	public BinaryTreeNode<E> getParent() {
		return parent;
	}

	public void setParent(BinaryTreeNode<E> parent) {
		this.parent = parent;
	}

	public E getData() {
		return data;
	}

	public int getDepth() {
		return this.depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public boolean isLeaf() {
		if (leftChild == null && rightChild == null)
			return true;
		else
			return false;
	}
}
