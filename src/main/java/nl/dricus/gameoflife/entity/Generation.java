package nl.dricus.gameoflife.entity;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Generation {

	private boolean[] alive;
	private int[] aliveNeighborCount;
	private int width;
	private int height;

	public Generation(int width, int height) {
		this.width = width;
		this.height = height;

		alive = new boolean[width * height];
		aliveNeighborCount = new int[width * height];
	}

	public boolean isCellAlive(int x, int y) {
		return alive[arrayIndex(x, y)];
	}

	public void resurrectCell(int x, int y) {
		if (!isCellAlive(x, y)) {
			alive[arrayIndex(x, y)] = true;

			updateAliveNeighborCountsForNeighborsOf(x, y, 1);
		}
	}

	public void killCell(int x, int y) {
		if (isCellAlive(x, y)) {
			alive[arrayIndex(x, y)] = false;

			updateAliveNeighborCountsForNeighborsOf(x, y, -1);
		}
	}

	private void updateAliveNeighborCountsForNeighborsOf(int x, int y, int add) {
		for (int row = max(y - 1, 0); row <= min(y + 1, height - 1); row++) {
			for (int col = max(x - 1, 0); col <= min(x + 1, width - 1); col++) {
				aliveNeighborCount[arrayIndex(col, row)] += add;
			}
		}
		aliveNeighborCount[arrayIndex(x, y)] -= add;
	}

	private int arrayIndex(int x, int y) {
		return x + (y * width);
	}

	public int getLiveNeighborCount(int x, int y) {
		return aliveNeighborCount[arrayIndex(x, y)];
	}

}
