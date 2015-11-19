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
				byte dist = degreeOfSep(from, to);
				if (dist != -1)
					System.out.println(dist);
				else
					System.out.println("Not connected.");
				break;
			case 'q':
				return;
			}
		}
	}

	public static void connect(int firstPerson, int second) {
		people[firstPerson][second] = true;
		people[second][firstPerson] = true;
	}

	public static void destroy(int first, int second) {
		people[first][second] = false;
		people[second][first] = false;
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

	public static byte degreeOfSep(int origin, int dest) {
		byte[] distance = new byte[50];
		for (byte i = 0; i < distance.length; i++) {
			distance[i] = -1;
		}
		distance[origin] = 0;

		while (distance[dest] == -1) {
			// tries to find the closest node to the center
			byte closestFriendDist = Byte.MAX_VALUE;
			byte closestFriend = -1;
			for (byte person = 0; person < distance.length; person++) {
				if (distance[person] != -1) { // visited

					for (byte friend = 0; friend < people[person].length; friend++) {
						if (people[person][friend] && distance[friend] == -1) {
							byte dist = (byte) (distance[person] + 1);
							if (dist < closestFriendDist) {
								closestFriendDist = dist;
								closestFriend = friend;
							}
						}
					}

				}
			}
			if (closestFriend == -1)
				return -1;
			distance[closestFriend] = closestFriendDist;
		}
		return distance[dest];
	}

	public static boolean contains(int[] array, int target) {
		for (int i : array) {
			if (i == target)
				return true;
		}
		return false;
	}
}
