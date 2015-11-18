package unit4_exercise17_graphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Friends {

	private static boolean[][] people;

	public static void main(String[] args) throws IOException {
		people = new boolean[49][49];

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
		char command;
		int from = -1;
		int to = -1;
		while (true) {
			String input = br.readLine();
			command = input.charAt(0);

			if (command != 'q') {
				from = Integer.parseInt(br.readLine());
				if (command != 'n' && command != 'f')
					to = Integer.parseInt(br.readLine());
			}

			switch (command) {
			case 'i':
				connect(from, to);
				break;
			case 'd':
				destroy(from, to);
				break;
			case 'n':
				System.out.println(noOfFriends(from));
				break;
			case 'f':
				System.out.println(friendsOfFriends(from));
				break;
			case 's':
				System.out.println(degreeOfSep(from, to));
				break;
			case 'q':
				return;
			}
		}
	}

	public static void connect(int origin, int dest) {
		people[origin][dest] = true;
		people[dest][origin] = true;
	}

	public static void destroy(int origin, int dest) {
		people[origin][dest] = false;
		people[dest][origin] = false;
	}

	public static int noOfFriends(int person) {
		int friends = 0;
		for (int friend = 0; friend < people[person].length; friend++) {
			if (people[person][friend])
				friends++;
		}
		return friends;
	}

	public static int friendsOfFriends(int person) {
		int[] fof = new int[50];
		int fofIdx = 0;
		for (int friend = 0; friend < people[person].length; friend++) {
			if (people[person][friend]) {
				boolean[] possibleFOFs = people[friend];
				for (int pFOF = 0; pFOF < possibleFOFs.length; pFOF++) {
					if (people[friend][pFOF] && pFOF != person
							&& pFOF != friend && !contains(fof, pFOF)
							&& !people[person][pFOF]) {
						fof[fofIdx] = pFOF;
						fofIdx++;
					}
				}
			}
		}

		int fofs = 0;
		for (int fOF : fof) {
			if (fOF != 0)
				fofs++;
		}
		return fofs;
	}

	public static int degreeOfSep(int origin, int dest) {
		int visited[] = new int[50];
		visited[origin] = 1;
		return distance(origin, dest, visited);
	}

	public static int distance(int person, int dest, int[] visited) {
		if (person == dest)
			return 1;

		boolean[] friends = people[person];
		int[] possibleDistances = new int[2500];
		int possDistCounter = 0;
		for (int friend = 0; friend < friends.length; friend++) {
			if (people[person][friend] && !contains(visited, friend)) {
				visited[friend] = 1;
				possibleDistances[possDistCounter] = distance(friend, dest,
						visited);
				possDistCounter++;
			}
		}

		int minDist = Integer.MAX_VALUE;
		for (int distance : possibleDistances) {
			if (distance < minDist)
				minDist = distance;
		}
		return minDist;
	}

	public static boolean contains(int[] array, int target) {
		for (int i : array) {
			if (i == target)
				return true;
		}
		return false;
	}
}
