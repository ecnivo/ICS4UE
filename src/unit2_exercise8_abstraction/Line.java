package unit2_exercise8_abstraction;

public class Line {

	private final Coordinate start, end;

	Line(Coordinate start, Coordinate end) {
		this.start = start;
		this.end = end;
	}

	public double getLength() {
		return Math.hypot(start.getY() - end.getY(),
				start.getX() - start.getY());
	}

	public Coordinate getStart() {
		return start;
	}

	public Coordinate getEnd() {
		return end;
	}
}
