package unit3_exercise12_ArrayLists;

import java.awt.Point;

public class Matrix<E> {

	private int row;
	private int column;
	private E[][] matrix;

	@SuppressWarnings("unchecked")
	public Matrix(int row, int column) {
		this.row = row;
		this.column = column;
		matrix = (E[][]) new Object[row][column];
	}

	public int getSize() {
		return this.row * this.column;
	}

	public int getRowSize(int rowNumber) {
		return row;
	}

	public int getColSize(int colNumber) {
		return column;
	}

	public E getItem(Point point) {
		return matrix[point.y][point.x];
	}

	public Point indexOf(E item) {
		for (E[] rows : matrix) {
			for (E cell : rows) {
				if (cell.equals(item))
					return new Point(column, row);
			}
		}
		return null;
	}

	public boolean isEmpty(Point point) {
		if (matrix[point.y][point.x] == null)
			return true;
		return false;
	}

	public boolean remove(Point point) {
		if (matrix[point.y][point.x] != null) {
			matrix[point.y][point.x] = null;
			return true;
		}
		return false;
	}

	public boolean remove(E item) {
		Point point = this.indexOf(item);
		if (point != null) {
			matrix[point.y][point.x] = null;
			return true;
		}
		return false;
	}

	public boolean contains(E item) {
		if (this.indexOf(item) != null)
			return true;
		return false;
	}

	public boolean set(E item, Point location) {
		try {
			matrix[location.y][location.x] = item;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public E[] getRow(int rowNum) {
		if (rowNum < matrix.length) {
			return matrix[rowNum];
		}
		return null;
	}

	public E[] getCol(int colNum) {
		if (colNum < matrix[0].length) {
			E[] column = (E[]) new Object[row];
			for (int row = 0; row < matrix.length; row++) {
				column[row] = matrix[row][colNum];
			}
			return column;
		}
		return null;
	}

	public void transpose() {
		E[][] transposed = (E[][]) new Object[row][column];

		for (int row = 0; row < matrix.length; row++) {
			for (int column = 0; column < matrix[row].length; column++) {
				transposed[row][column] = matrix[column][row];
			}
		}
		matrix = transposed;
	}
}
