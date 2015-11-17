package unit3_exercise13_linked_lists;

public class SimpleLinkedList<E> {

	private Node<E> head;

	/**
	 * Creates a new, blank, linked list.
	 */
	public SimpleLinkedList() {
		head = null;
	}

	/**
	 * Gets the last node in the list
	 * 
	 * @return the last Node<E>
	 */
	private Node<E> getLastNode() {
		// Check if the list is empty
		if (head == null) {
			return null;
		}

		// Goes through the list until the end
		Node<E> currNode = head;
		while (currNode.getNextNode() != null) {
			currNode = currNode.getNextNode();
		}
		return currNode;
	}

	/**
	 * Adds an element at the end of the list
	 * 
	 * @param e
	 *            the element to add
	 */
	public void add(E e) {
		// Puts it at the front if list is empty to begin with
		if (head == null) {
			head = new Node<E>(e, null);

		}
		// Puts it elsewhere if not empty
		else {
			getLastNode().setNextNode(new Node<E>(e, null));
		}
	}

	/**
	 * Gets an element based on its index
	 * 
	 * @param index
	 *            the index of the target
	 * @return the element in said index
	 */
	public E get(int index) {
		// Parameter sanitization
		if (head == null || index < 0 || index > size() - 1) {
			return null;
		} else {
			// Goes through array until it finds the element
			Node<E> currNode = head;
			for (int i = 0; i < index; i++) {
				currNode = currNode.getNextNode();
			}
			return currNode.getData();
		}
	}

	/**
	 * Gets a particular Node<E> in the array
	 * 
	 * @param index
	 *            the target node
	 * @return the Node<E>
	 */
	public Node<E> getNode(int index) {
		// Parameter sanitization
		if (head == null || index < 0 || index > size() - 1) {
			return null;
		} else {
			// Loops through to get the desired node
			Node<E> currNode = head;
			for (int i = 0; i < index; i++) {
				currNode = currNode.getNextNode();
			}
			return currNode;
		}
	}

	/**
	 * Gets the index of a particular element
	 * 
	 * @param e
	 *            The element to look for
	 * @return the index of the element in the list. Returns -1 if it is not
	 *         present
	 */
	public int indexOf(E e) {
		int index = 0;
		Node<E> currNode = head;
		// Finds the element and its index
		while (!currNode.getData().equals(e)) {
			if (currNode.getNextNode() != null) {
				index++;
				currNode = currNode.getNextNode();
			} else {
				// If the element doesn't exist
				return -1;
			}
		}
		return index;
	}

	/**
	 * Gets rid of an element in the list based on index
	 * 
	 * @param index
	 *            the index of the element
	 * @return the element removed
	 */
	public E remove(int index) {
		// If the index isn't there
		if (head == null || index < 0 || index > size() - 1) {
			return null;
		} else {
			// Gets the element and reassigns references
			E tempData = get(index);

			if (index == 0) {
				head = getNode(1);
			} else if (getNode(index + 1) != null) {
				getNode(index - 1).setNextNode(getNode(index + 1));
			} else {
				getNode(index - 1).setNextNode(null);
			}

			return tempData;
		}
	}

	/**
	 * Removes an element based on its contents
	 * 
	 * @param element
	 *            the element to get rid of
	 * @return the element
	 */
	public void remove(E element) {
		if (head == null) {
			return;
		} else {
			remove(indexOf(element));
		}
	}

	/**
	 * Makes a clear array
	 */
	public void clear() {
		head = null;
	}

	public int size() {
		if (head == null) {
			return 0;
		} else {
			Node<E> currNode = head;
			int noOfNodes = 1;
			while (currNode.getNextNode() != null) {
				currNode = currNode.getNextNode();
				noOfNodes++;
			}
			return noOfNodes;
		}
	}

	public String toString() {
		String output = "[";
		for (int i = 0; i < size() - 1; i++) {
			output += get(i).toString() + ", ";
		}
		output += getLastNode().getData() + "]";
		return output;
	}

	public void swapWithNext(int index) {
		int size = size();

		if (index > 0 && index < size - 2) {
			Node<E> prevNode = getNode(index - 1);
			Node<E> currNode = getNode(index);
			Node<E> nextNode = getNode(index + 1);
			Node<E> twoNodesAfter = getNode(index + 2);

			// Sets previous to next one
			prevNode.setNextNode(nextNode);
			// Sets next to current
			nextNode.setNextNode(currNode);
			// Sets current to two after
			currNode.setNextNode(twoNodesAfter);
		} else if (index == 0) {
			Node<E> oldHead = getNode(0);

			head = getNode(1);
			oldHead.setNextNode(head.getNextNode());
			head.setNextNode(oldHead);
		} else if (index == size - 2) {
			Node<E> prevNode = getNode(index - 1);
			Node<E> currNode = getNode(index);
			Node<E> tail = getNode(index + 1);

			prevNode.setNextNode(tail);
			currNode.setNextNode(null);
			tail.setNextNode(currNode);
		} else {
			return;
		}
	}

	static class Node<E> {
		private E data;
		private Node<E> nextNode;

		/**
		 * @param e
		 */
		private Node(E e) {
			this.data = e;
			nextNode = null;
		}

		/**
		 * @param e
		 * @param nextNode
		 */
		private Node(E e, Node<E> nextNode) {
			this.data = e;
			this.nextNode = nextNode;
		}

		/**
		 * @param nextNode
		 */
		private void setNextNode(Node<E> nextNode) {
			this.nextNode = nextNode;
		}

		/**
		 * @return
		 */
		public Node<E> getNextNode() {
			return nextNode;
		}

		/**
		 * @param data
		 */
		public void setData(E data) {
			this.data = data;
		}

		/**
		 * @return
		 */
		public E getData() {
			return data;
		}
	}
}