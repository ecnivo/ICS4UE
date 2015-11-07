package unit2_exercise9_polymorphism;

public class BoardingHouse {

	private Pet[] cages;

	BoardingHouse(int noOfPets) {
		cages = new Pet[noOfPets];
	}

	public int board(Pet pet) {
		for (int cage = 0; cage < cages.length; cage++) {
			if (cages[cage] == null) {
				cages[cage] = pet;
				return cage;
			}
		}
		return -1;
	}

	public boolean remove(int cageNumber) {
		if (cages[cageNumber] == null)
			return false;
		else {
			cages[cageNumber] = null;
			return true;
		}
	}

	public boolean isValidCage(int number) {
		if (number <= cages.length)
			return true;
		else
			return false;
	}

	public Pet[] getCages() {
		return cages;
	}
}
