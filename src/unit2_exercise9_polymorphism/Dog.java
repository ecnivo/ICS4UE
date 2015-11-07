package unit2_exercise9_polymorphism;


public class Dog extends Pet {

	Dog(double weight, String name) {
		super(name, weight);
	}
	
	public void wagTail() {
		System.out.println("I'm wagging my tail!");
	}
	
	public void goForWalk() {
		System.out.println(name + " is going for a walk.");
	}
}
