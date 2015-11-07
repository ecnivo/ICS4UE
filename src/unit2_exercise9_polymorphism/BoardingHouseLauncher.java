package unit2_exercise9_polymorphism;

import java.util.Scanner;

public class BoardingHouseLauncher {

	public static void main(String[] args) {
		BoardingHouse house = new BoardingHouse(10);
		Scanner keyboard = new Scanner(System.in);

		char exit;
		do {
			String input;
			System.out
					.println("What do you want to do? (Action, board, remove) (a/b/r)");
			input = keyboard.next();
			if (input.charAt(0) == 'a' || input.charAt(0) == 'A') {
				System.out.println("Which cage number?");
				int cageNumber = keyboard.nextInt();
				if (house.isValidCage(cageNumber)) {
					System.out
							.print("What action? Speak, eat, sleep, go to the washroom");
					if (house.getCages()[cageNumber] instanceof Dog)
						System.out.println(", wag tail or go for a walk?");
					else
						System.out.println(", purr or use litter box?");
					String action = keyboard.next();
					switch (action) {
					case "speak":
						System.out.println("What do you want them to say?");
						house.getCages()[cageNumber].speak(keyboard.nextLine());
						break;
					case "eat":
						System.out
								.println("How much do you want to feed them?");
						house.getCages()[cageNumber].eat(keyboard.nextDouble());
						break;
					case "sleep":
						house.getCages()[cageNumber].sleep();
						break;
					case "washroom":
						System.out.println("How much mass do they lose?");
						house.getCages()[cageNumber].defecate(keyboard
								.nextDouble());
						break;
					case "wag":
						((Dog) (house.getCages()[cageNumber])).wagTail();
						break;
					case "go":
						((Dog) (house.getCages()[cageNumber])).goForWalk();
						break;
					case "purr":
						((Cat) (house.getCages()[cageNumber])).purr();
						break;
					case "use":
						System.out.println("How much do they lose in weight?");
						((Cat) (house.getCages()[cageNumber]))
								.useLitterBox(keyboard.nextDouble());
						break;
					}
				} else
					System.out.println("Not a valid cage number.");
			} else if (input.charAt(0) == 'r' || input.charAt(0) == 'R') {
				System.out.println("What cage number?");
				int cageNumber = keyboard.nextInt();
				if (house.isValidCage(cageNumber)) {
					System.out.println(house.getCages()[cageNumber].getName()
							+ " has been removed from cage " + cageNumber);
					house.remove(cageNumber);
				} else
					System.out.println("Not a valid cage number.");
			} else if (input.charAt(0) == 'b' || input.charAt(0) == 'B') {
				System.out.println("What is the pet name?");
				String name = keyboard.nextLine();
				System.out.println("Cat or dog?");
				String type = keyboard.nextLine();
				boolean isCat;
				if (type.equalsIgnoreCase("cat")) {
					isCat = true;
				} else
					isCat = false;
				System.out.println("What is the pet's weight?");
				double weight = keyboard.nextDouble();

				if (isCat) {
					Cat cat = new Cat(name, weight);
					house.board(cat);
				} else {
					Dog dog = new Dog(weight, name);
					house.board(dog);
				}
			}
			System.out
					.println("Would you like to perform any other actions? (y/n)");
			exit = keyboard.next().charAt(0);
			System.out.println("\n\n\n\n\n");
		} while (exit != 'y' || exit != 'Y');
	}

}
