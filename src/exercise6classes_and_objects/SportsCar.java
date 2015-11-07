package exercise6classes_and_objects;

public class SportsCar {
	String manufacturer;
	String model;
	double fuelTankSize;
	double topSpeed;
	double cost;
	double mpg;
	
	public SportsCar(String manufacturer, String model, double fuelTank, double topSpeed, double cost, double mpg) {
		this.manufacturer = manufacturer;
		this.model = model;
		this.fuelTankSize = fuelTank;
		this.topSpeed = topSpeed;
		this.cost = cost;
		this.mpg = mpg;
	}
}
