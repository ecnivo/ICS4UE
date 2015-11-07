package unit2_exercise14_queues_and_stacks;


public class PriorityQueueTest {

	public static void main(String[] args) {
		PriorityQueue<String> testQueue = new PriorityQueue<String>();
		for (int i = 0; i < 25; i++) {
			testQueue.enqueue("test" + i, (int) (Math.random() * 100));
		}
		System.out.println(testQueue);
	}

}
