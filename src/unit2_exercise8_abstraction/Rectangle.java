package unit2_exercise8_abstraction;

public class Rectangle extends TwoDShape {

	private Line top, right, bottom, left;

	public Rectangle(Coordinate topLeft, Coordinate bottomRight) {
		Coordinate topRight = new Coordinate(topLeft.getY(), bottomRight.getX());
		Coordinate bottomLeft = new Coordinate(topLeft.getX(),
				bottomRight.getY());
		top = new Line(topLeft, topRight);
		right = new Line(topRight, bottomRight);
		bottom = new Line(bottomRight, bottomLeft);
		left = new Line(bottomLeft, topLeft);
	}

	public double getArea() {
		return top.getLength() * left.getLength();
	}

	public double getPerimeter() {
		return top.getLength() + right.getLength() + bottom.getLength()
				+ left.getLength();
	}

	public Line[] getLines() {
		return new Line[] { top, right, bottom, left };
	}
}
