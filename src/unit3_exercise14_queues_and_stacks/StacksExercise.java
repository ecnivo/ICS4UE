package unit3_exercise14_queues_and_stacks;

public class StacksExercise<E> {

	private StacksNode<E> head;

	public StacksExercise() {
		head = null;
	}

	public void push(E element) {
		if (head == null) {
			head = new StacksNode<E>(element, null);
		} else {
			StacksNode<E> tempNode = new StacksNode<E>(element);
			tempNode.setNextNode(head);
			head = tempNode;
		}
	}

	public E pop() {
		StacksNode<E> returnNode = head;
		head = head.getNextNode();
		return returnNode.getData();
	}

	public String toString() {
		if (head == null)
			return "[]";
		StacksNode<E> currNode = head;
		String output = "[";
		while (currNode != null) {
			output += currNode.getData() + " ";
			currNode = currNode.getNextNode();
		}
		output += "]";
		return output;
	}

	class StacksNode<E> {

		private E data;
		private StacksNode<E> nextNode;

		private StacksNode(E data) {
			this.data = data;
		}

		private StacksNode(E data, StacksNode<E> nextNode) {
			this.data = data;
			this.nextNode = nextNode;
		}

		private E getData() {
			return data;
		}

		/**
		 * @return the nextNode
		 */
		public StacksNode<E> getNextNode() {
			return nextNode;
		}

		/**
		 * @param nextNode
		 *            the nextNode to set
		 */
		public void setNextNode(StacksNode<E> nextNode) {
			this.nextNode = nextNode;
		}
	}
}
