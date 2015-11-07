package unit2_exercise14_queues_and_stacks;


public class PriorityQueue<E> {

	private PriorityNode<E> head;

	/**
	 * Creates a new, blank, linked list.
	 */
	public PriorityQueue() {
		head = null;
	}

	public boolean isEmpty() {
		if (head == null)
			return true;
		else
			return false;
	}

	public boolean enqueue(E element, int priority) {
		if (priority < 0) {
			return false;
		}

		if (head == null) {
			head = new PriorityNode<E>(element, null, priority);
			return true;
		}

		PriorityNode<E> insertNode = head;
		if (priority > head.getPriority()) {
			PriorityNode<E> tempNode = new PriorityNode<E>(element, head,
					priority);
			head = tempNode;
			return true;
		}

		while (insertNode.getNextNode() != null
				&& insertNode.getNextNode().getPriority() > priority) {
			insertNode = insertNode.getNextNode();
		}
		if (insertNode.getNextNode() != null) {
			insertNode.setNextNode(new PriorityNode<E>(element, insertNode
					.getNextNode(), priority));
		} else {
			insertNode
					.setNextNode(new PriorityNode<E>(element, null, priority));
		}
		return true;
	}

	public E dequeue() {
		E tempData = head.getData();
		head = head.getNextNode();
		return tempData;
	}

	/**
	 * Makes a clear array
	 */
	public void clear() {
		head = null;
	}

	public String toString() {
		if (head == null)
			return "[]";
		PriorityNode<E> currNode = head;
		String output = "[";
		while (currNode != null) {
			output += currNode.toString() + " ";
			currNode = currNode.getNextNode();
		}
		output += "]";
		return output;
	}

	static class PriorityNode<E> {
		private E data;
		private PriorityNode<E> nextNode;
		private int priority;

		/**
		 * @param e
		 */
		private PriorityNode(E e, int priority) {
			this.data = e;
			nextNode = null;
			this.priority = priority;
		}

		/**
		 * @param e
		 * @param nextNode
		 */
		private PriorityNode(E e, PriorityNode<E> nextNode, int priority) {
			this.data = e;
			this.nextNode = nextNode;
			this.priority = priority;
		}

		/**
		 * @param nextNode
		 */
		private void setNextNode(PriorityNode<E> nextNode) {
			this.nextNode = nextNode;
		}

		/**
		 * @return
		 */
		private PriorityNode<E> getNextNode() {
			return nextNode;
		}

		/**
		 * @return
		 */
		private E getData() {
			return data;
		}

		private int getPriority() {
			return priority;
		}

		public String toString() {
			return (String) data + '|' + priority;
		}
	}
}
