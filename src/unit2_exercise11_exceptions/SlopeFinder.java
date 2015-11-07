package unit2_exercise11_exceptions;

public class SlopeFinder {

	public double slope(double x1, double x2, double y1, double y2)
			throws VerticalLineException {
		if (x1 - x2 == 0)
			throw new VerticalLineException();
		return (y1 - y2) / (x1 - x2);
	}

	static class VerticalLineException extends Exception {
		public VerticalLineException() {
			
		}
	}
}
