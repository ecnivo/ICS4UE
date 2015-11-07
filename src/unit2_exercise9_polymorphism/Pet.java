package unit2_exercise9_polymorphism;

public class Pet {

	double weight;
	String name;

	Pet(String name, double weight) {
		this.weight = weight;
		this.name = name;
	}

	public void speak(String stuff) {
		System.out.println(stuff);
	}

	public void eat(double massOfFood) {
		weight += massOfFood;
	}

	public void sleep() {
		System.out.println(name + " is sleeping!");
	}

	public void defecate(double mass) {
		weight -= mass;
	}

	public double getWeight() {
		return weight;
	}

	public String getName() {
		return name;
	}
}
