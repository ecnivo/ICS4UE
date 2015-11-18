package unit4_exercise17_graphs;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Friends {

	private static int[][] people;

	public static void main(String[] args) {
		people = new int[49][49];

		connect(2, 6);
		connect(1, 6);
		connect(6, 7);
		connect(7, 8);
		connect(8, 9);
		connect(9, 10);
		connect(10, 11);
		connect(11, 12);
		connect(9, 12);
		connect(12, 13);
		connect(13, 15);
		connect(3, 15);
		connect(3, 4);
		connect(4, 5);
		connect(3, 5);
		connect(3, 6);
		connect(4, 6);
		connect(5, 6);
		connect(13, 14);
		connect(16, 18);
		connect(16, 17);
		connect(17, 18);

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (br.readLine())
	}

	public static void connect(int origin, int dest) {
		people[origin][dest] = 1;
		people[dest][origin] = 1;
	}

	public static void destroy(int origin, int dest) {
		people[origin][dest] = 0;
		people[dest][origin] = 0;
	}

	public static int noOfFriends(int person) {
		int friends = 0;
		for (int friend = 0; friend < people[person].length; friend++) {
			if (people[person][friend] == 1)
				friends++;
		}
		return friends;
	}

	public static int friendsOfFriends(int person) {
		int fOF = 0;
		for (int friend = 0; friend < people[person].length; friend++) {
			if (people[person][friend] == 1) {
				fOF += noOfFriends(friend);
			}
		}
		return fOF;
	}

	public static int degreeOfSep(int origin, int dest) {
		int[] visited = shortestPath(new int[people.length], origin, dest);
		for (int dist = 0; dist < visited.length; dist++) {
			if (visited[dist] == 0)
				return dist - 1;
		}
		return visited.length;
	}

	public static int[] shortestPath(int[] visited, int origin, int dest) {
		if (contains(visited, dest))
			return visited;

		int[] friends = people[origin];
		for (int friend = 0; friend < friends.length; friend++) {
			if (people[origin][friend] == 1 && !contains(visited, friend)) {
				visited = shortestPath(visited, friend, dest);
			}
		}
		return visited;
	}

	public static boolean contains(int[] array, int target) {
		for (int i : array) {
			if (i == target)
				return true;
		}
		return false;
	}
}
