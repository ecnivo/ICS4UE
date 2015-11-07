package unit2_exercise8_abstraction;

public class Triangle extends TwoDShape {

	private static Line one, two, three;

	Triangle(Line base, Coordinate third) {
		one = base;
		two = new Line(base.getStart(), third);
		three = new Line(third, base.getEnd());
	}

	public double getArea() {
		double s = one.getLength() + two.getLength() + three.getLength();
		s /= 2;
		return Math.sqrt(s * (s - one.getLength()) * (s - two.getLength())
				* (s - three.getLength()));
	}

	public double getPerimeter() {
		return one.getLength() + two.getLength() + three.getLength();
	}

	public Line[] getLines() {
		return new Line[] { one, two, three };
	}
}
