package unit2_exercise9_polymorphism;


public class Cat extends Pet {

	Cat(String name, double weight) {
		super(name, weight);
	}

	public void purr() {
		System.out.println("PURR");
	}

	public void useLitterBox(double mass) {
		super.defecate(mass);
	}
}
