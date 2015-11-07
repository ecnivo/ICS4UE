package unit2_exercise15_trees;

import java.util.ArrayList;

public class BinaryTree<E extends Comparable<E>> {

	private BinaryTreeNode<E> root = null;
	private final static boolean DEBUG = true;
	private final static int PADDING_EACH_SIDE = 1;

	/**
	 * 
	 */
	public BinaryTree() {
	}

	/**
	 * @param data
	 * @return
	 */
	public boolean contains(E data) {
		if (findNode(data, root) != null)
			return true;
		else
			return false;
	}

	/**
	 * @param data
	 * @param node
	 * @return
	 */
	public BinaryTreeNode<E> findNode(E data, BinaryTreeNode<E> node) {
		if (node.getData().equals(data))
			return node;
		else {
			if (node.getLeftChild() != null && node.getRightChild() != null) {
				BinaryTreeNode<E> leftBranch = findNode(data,
						node.getLeftChild());
				BinaryTreeNode<E> rightBranch = findNode(data,
						node.getRightChild());

				if (leftBranch != null)
					return leftBranch;
				else if (rightBranch != null)
					return rightBranch;
				else
					return null;
			} else if (node.getLeftChild() != null
					&& node.getRightChild() == null) {
				return findNode(data, node.getLeftChild());
			} else if (node.getRightChild() != null
					&& node.getLeftChild() == null) {
				return findNode(data, node.getRightChild());
			} else {
				return null;
			}
		}
	}

	/**
	 * @return
	 */
	public int size() {
		return noOfChildren(root);
	}

	/**
	 * @param node
	 * @return
	 */
	private int noOfChildren(BinaryTreeNode<E> node) {
		if (node.getLeftChild() != null && node.getRightChild() != null)
			return 1 + noOfChildren(node.getRightChild())
					+ noOfChildren(node.getLeftChild());
		else if (node.getRightChild() != null && node.getLeftChild() == null)
			return 1 + noOfChildren(node.getRightChild());
		else if ((node.getRightChild() == null && node.getLeftChild() != null))
			return 1 + noOfChildren(node.getLeftChild());
		else
			return 1;
	}

	/**
	 * 
	 */
	public void clear() {
		root = null;
	}

	/**
	 * @param data
	 */
	public BinaryTreeNode<E> add(E data) {
		if (root == null) {
			if (DEBUG)
				System.out.println("creating new root of " + data);
			root = new BinaryTreeNode<E>(data, null, null, null, 0);
			return root;
		} else {
			BinaryTreeNode<E> parent = validParent(data, root);
			BinaryTreeNode<E> newChild = new BinaryTreeNode<E>(data, null,
					null, parent, parent.getDepth() + 1);

			if (parent.getData().compareTo(data) < 0) {
				parent.setRightChild(newChild);
				if (DEBUG)
					System.out.println("inserting " + data
							+ " as right child of  " + parent.getData());
			} else {
				if (DEBUG)
					System.out.println("inserting " + data
							+ " as left child of " + parent.getData());
				parent.setLeftChild(newChild);
			}
			
			return newChild;
		}
	}

	/**
	 * @param data
	 * @param node
	 * @return
	 */
	private BinaryTreeNode<E> validParent(E data, BinaryTreeNode<E> node) {
		if (node.getData().compareTo(data) < 0) { // going right
			if (node.getRightChild() != null) { // if node has right child
				return validParent(data, node.getRightChild());
			} else {
				// no right child
				return node;
			}
		} else { // going left
			if (node.getLeftChild() != null) { // if node has left child
				return validParent(data, node.getLeftChild());
			} else {
				// no left child
				return node;
			}
		}
	}

	/**
	 * @param data
	 * @return
	 */
	public E get(E data) {
		if (contains(data))
			return data;
		else
			return null;
	}

	/**
	 * @param data
	 * @return
	 */
	public boolean remove(E data) {
		// Gets references
		BinaryTreeNode<E> target = findNode(data, root);
		if (target == null)
			return false;
		BinaryTreeNode<E> rightChild = target.getRightChild();
		BinaryTreeNode<E> leftChild = target.getLeftChild();
		BinaryTreeNode<E> parent = target.getParent();
		BinaryTreeNode<E> farthestLeft = target.getRightChild();

		// Sets parent and rightChild relationship
		if (parent != null) {
			if (parent.getLeftChild().equals(target))
				parent.setLeftChild(rightChild);
			else
				parent.setRightChild(rightChild);
		} else {
			// must be root node
			root = rightChild;
		}
		if (rightChild != null)
			rightChild.setParent(parent);

		// Rearranges the left side child
		// Finds out what farthestLeft is
		while (farthestLeft.getLeftChild() != null)
			farthestLeft = farthestLeft.getLeftChild();
		if (leftChild != null)
			leftChild.setParent(farthestLeft);
		if (farthestLeft != null)
			farthestLeft.setLeftChild(leftChild);

		// Changes the depth for all rearranged nodes
		updateDepth(root);

		// Finishes up
		target = null;
		return true;
	}

	/**
	 * @param node
	 */
	private void updateDepth(BinaryTreeNode<E> node) {
		if (node.getParent() != null)
			node.setDepth(node.getParent().getDepth() + 1);
		else
			node.setDepth(0);

		if (node.getRightChild() != null && node.getLeftChild() != null) {
			updateDepth(node.getRightChild());
			updateDepth(node.getLeftChild());
		} else if (node.getRightChild() != null && node.getLeftChild() == null) {
			updateDepth(node.getRightChild());
		} else if (node.getRightChild() == null && node.getLeftChild() != null) {
			updateDepth(node.getLeftChild());
		}
	}

	/**
	 * @return
	 */
	public boolean isEmpty() {
		if (root == null)
			return true;
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		int maxDepth = getMaxDepth(root);
		int maxNodeWidth = getMaxStringLength(root) + PADDING_EACH_SIDE * 2;
		int width = (int) (Math.pow(2, maxDepth) * maxNodeWidth);
		
		String returnString = "";

		for (int line = 0; line <= maxDepth; line++) {
			ArrayList<String> nodes = getNodesAtDepth(root, 0, line);
			returnString += nodes.toString() + "\n";
//			int noOfNodes = nodes.size();
//			int spacesBetweenNodes = (width - (noOfNodes * maxNodeWidth)) / noOfNodes + 1;
//			for (int node = 0; node < noOfNodes; node++) {
//				returnString += thisManySpaces(spacesBetweenNodes + PADDING_EACH_SIDE);
//				returnString += nodes.get(node);
//				returnString += thisManySpaces(PADDING_EACH_SIDE);
//			}
//			returnString += thisManySpaces(spacesBetweenNodes) + "\n\n";

			// adds the second line with slashes connecting
		}
		return returnString;
	}

	/**
	 * @param spaces
	 * @return
	 */
	private String thisManySpaces(int spaces) {
		String returnString = "";
		for (int i = 0; i < spaces; i++) {
			returnString += " ";
		}
		return returnString;
	}

	/**
	 * @param node
	 * @param searchDepth
	 * @param targetDepth
	 * @return
	 */
	private ArrayList<String> getNodesAtDepth(BinaryTreeNode<E> node,
			int searchDepth, int targetDepth) {
		if (node.getDepth() < targetDepth) {
			if (node.getLeftChild() != null && node.getRightChild() != null) {
				// has two branches
				ArrayList<String> leftSide = getNodesAtDepth(
						node.getLeftChild(), searchDepth + 1, targetDepth);
				ArrayList<String> rightSide = getNodesAtDepth(
						node.getRightChild(), searchDepth + 1, targetDepth);
				leftSide.addAll(rightSide);
				return leftSide;
			} else if (node.getLeftChild() != null
					&& node.getRightChild() == null) {
				// only left branch
				return getNodesAtDepth(node.getLeftChild(), searchDepth + 1,
						targetDepth);
			} else if (node.getLeftChild() == null
					&& node.getRightChild() != null) {
				// only right branch
				return getNodesAtDepth(node.getRightChild(), searchDepth + 1,
						targetDepth);
			} else {
				// leaf
				ArrayList<String> tempArrayList = new ArrayList<String>();
				tempArrayList.add(thisManySpaces(PADDING_EACH_SIDE));
				return tempArrayList;
			}
		} else {
			ArrayList<String> tempArrayList = new ArrayList<String>();
			tempArrayList.add(node.getData().toString());
			return tempArrayList;
		}
	}

	/**
	 * @param node
	 * @return
	 */
	protected int getMaxDepth(BinaryTreeNode<E> node) {
		if (node.getRightChild() != null && node.getLeftChild() != null) {
//			System.out.println("node is " + node.getData() + " and right child is " + node.getRightChild().getData());
			int maxRightDepth = getMaxDepth(node.getRightChild());
			int maxLeftDepth = getMaxDepth(node.getLeftChild());
			return Math.max(maxRightDepth, maxLeftDepth);
		} else if (node.getRightChild() != null && node.getLeftChild() == null){
			return getMaxDepth(node.getRightChild());}
		else if (node.getLeftChild() != null && node.getRightChild() == null){
//			System.out.println("node is " + node.getData() + " and left child is " + node.getLeftChild().getData());
			return getMaxDepth(node.getLeftChild());}
		else
			return node.getDepth();
	}

	/**
	 * @param node
	 * @return
	 */
	private int getMaxStringLength(BinaryTreeNode<E> node) {
		if (node.getRightChild() != null && node.getLeftChild() != null) {
			int leftMaxLength = getMaxStringLength(node.getLeftChild());
			int rightMaxLength = getMaxStringLength(node.getRightChild());
			return Math.max(leftMaxLength, rightMaxLength);
		} else if (node.getRightChild() != null && node.getLeftChild() == null) {
			return getMaxStringLength(node.getRightChild());
		} else if (node.getLeftChild() != null && node.getRightChild() == null) {
			return getMaxStringLength(node.getLeftChild());
		} else {
			return node.getData().toString().length();
		}
	}

	/**
	 * @return
	 */
	protected BinaryTreeNode<E> getRoot() {
		return root;
	}

	/**
	 * @param root
	 */
	protected void setRoot(BinaryTreeNode<E> root) {
		this.root = root;
	}
}