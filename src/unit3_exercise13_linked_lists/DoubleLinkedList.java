package unit3_exercise13_linked_lists;


public class DoubleLinkedList<E> {

	private DoubleNode<E> head;
	private DoubleNode<E> tail;

	/**
	 * Creates a new, blank, linked list.
	 */
	public DoubleLinkedList() {
		head = null;
		tail = null;
	}

	/**
	 * Gets the last node in the list
	 * 
	 * @return the last DoubleNode<E>
	 */
	private DoubleNode<E> getLastNode() {
		return tail;
	}

	/**
	 * Adds an element at the end of the list
	 * 
	 * @param e
	 *            the element to add
	 */
	public void add(E e) {
		if (tail == null) {
			tail = new DoubleNode<E>(e, null, null);
		} else {
			tail.setNextNode(new DoubleNode<E>(e, null, tail));
			tail = tail.getNextNode();
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
			DoubleNode<E> currNode = head;
			for (int i = 0; i < index; i++) {
				currNode = currNode.getNextNode();
			}
			return currNode.getData();
		}
	}

	/**
	 * Gets a particular DoubleNode<E> in the array
	 * 
	 * @param index
	 *            the target node
	 * @return the DoubleNode<E>
	 */
	private DoubleNode<E> getNode(int index) {
		// Parameter sanitization
		if (head == null || index < 0 || index > size() - 1) {
			return null;
		} else {
			// Loops through to get the desired node
			DoubleNode<E> currNode = head;
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
		DoubleNode<E> currNode = head;
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
				head.getNextNode().setPrevNode(null);
				head = head.getNextNode();
			} else if (index == size() - 1) {
				tail.getPrevNode().setNextNode(null);
				tail = tail.getPrevNode();
			} else {
				DoubleNode<E> prev = getNode(index - 1);
				DoubleNode<E> curr = getNode(index);
				DoubleNode<E> next = getNode(index + 1);

				prev.setNextNode(next);
				next.setPrevNode(prev);
				curr.setNextNode(null);
				curr.setPrevNode(null);
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
		tail = null;
	}

	public int size() {
		if (head == null) {
			return 0;
		} else {
			DoubleNode<E> currNode = head;
			int noOfNodes = 1;
			while (currNode.getNextNode() != null) {
				currNode = currNode.getNextNode();
				noOfNodes++;
			}
			return noOfNodes;
		}
	}

	public String toString() {
		if (head == null)
			return "[]";
		DoubleNode<E> currNode = head;
		String output = "[";
		while (currNode != null) {
			output += currNode.getData() + " ";
			currNode = currNode.getNextNode();
		}
		output += output.trim() + "]";
		return output;
	}

	static class DoubleNode<E> {
		private E data;
		private DoubleNode<E> nextNode;
		private DoubleNode<E> prevNode;

		/**
		 * @param e
		 */
		private DoubleNode(E e) {
			this.data = e;
			nextNode = null;
		}

		/**
		 * @param e
		 * @param nextNode
		 */
		private DoubleNode(E e, DoubleNode<E> nextNode, DoubleNode<E> prevNode) {
			this.data = e;
			this.nextNode = nextNode;
		}

		/**
		 * @param nextNode
		 */
		private void setNextNode(DoubleNode<E> nextNode) {
			this.nextNode = nextNode;
		}

		/**
		 * @param nextNode
		 */
		private void setPrevNode(DoubleNode<E> prevNode) {
			this.prevNode = prevNode;
		}

		/**
		 * @return
		 */
		private DoubleNode<E> getPrevNode() {
			return prevNode;
		}

		/**
		 * @return
		 */
		private DoubleNode<E> getNextNode() {
			return nextNode;
		}

		/**
		 * @param data
		 */
		private void setData(E data) {
			this.data = data;
		}

		/**
		 * @return
		 */
		private E getData() {
			return data;
		}
	}
}