package nl.dricus.gameoflife.entity;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Generation {

	private final int width;
	private final int height;

	public Cell[][] cells;

	public Generation(int width, int height) {
		this.width = width;
		this.height = height;

		initializeCellStates();
	}

	private void initializeCellStates() {
		cells = new Cell[height][width];

		initializeAllCellsToDead();
	}

	private void initializeAllCellsToDead() {
		IntStream.range(0, height).forEach(y -> {
			IntStream.range(0, width).forEach(x -> setCell(x, y, Cell.DEAD));
		});
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setCell(int x, int y, Cell cellState) {
		cells[y][x] = cellState;
	}

	public Cell getCell(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Cell.DEAD;

		return cells[y][x];
	}

	public int getLiveNeighorCount(int x, int y) {
		try (Stream<Cell> neighbors = getNeighbors(x, y)) {
			return neighbors.mapToInt(state -> state == Cell.ALIVE ? 1 : 0).sum();
		}
	}

	private Stream<Cell> getNeighbors(int x, int y) {
		return Stream.of(getCell(x - 1, y), getCell(x + 1, y), getCell(x, y - 1), getCell(x, y + 1),
				getCell(x - 1, y + 1), getCell(x + 1, y + 1), getCell(x - 1, y - 1), getCell(x + 1, y - 1));
	}

}
