package nl.dricus.gameoflife.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GenerationTest {

	private Generation sut;

	@Test
	public void oneByOneInitialState() {
		createGeneration(1, 1);

		assertThat(sut.isCellAlive(0, 0), is(false));
	}

	@Test
	public void oneByOneResurrect() {
		createGeneration(1, 1);

		sut.resurrectCell(0, 0);

		assertThat(sut.isCellAlive(0, 0), is(true));
	}

	@Test
	public void twoByOneResurrect() {
		createGeneration(2, 1);

		sut.resurrectCell(1, 0);

		assertThat(sut.isCellAlive(0, 0), is(false));
		assertThat(sut.isCellAlive(1, 0), is(true));
	}

	@Test
	public void oneByTwoResurrect() {
		createGeneration(1, 2);

		sut.resurrectCell(0, 1);

		assertThat(sut.isCellAlive(0, 0), is(false));
		assertThat(sut.isCellAlive(0, 1), is(true));
	}

	@Test
	public void twoByTwoResurrect() {
		createGeneration(2, 2);

		sut.resurrectCell(1, 1);

		assertThat(sut.isCellAlive(0, 0), is(false));
		assertThat(sut.isCellAlive(0, 1), is(false));
		assertThat(sut.isCellAlive(1, 0), is(false));
		assertThat(sut.isCellAlive(1, 1), is(true));
	}

	@Test
	public void threeByThreeResurrect() {
		createGeneration(3, 3);

		sut.resurrectCell(1, 2);

		assertThat(sut.isCellAlive(2, 1), is(false));
		assertThat(sut.isCellAlive(1, 2), is(true));
	}

	@Test
	public void zeroLiveNeighbors() {
		createGeneration(3, 3);

		assertThat(sut.getLiveNeighborCount(1, 1), is(0));
	}

	@Test
	public void northWestLiveNeighbor() {
		createGeneration(3, 3);

		sut.resurrectCell(0, 0);

		assertThat(sut.getLiveNeighborCount(1, 1), is(1));
	}

	@Test
	public void northWestLiveNeighborShiftRight() {
		createGeneration(3, 3);

		sut.resurrectCell(1, 0);

		assertThat(sut.getLiveNeighborCount(2, 1), is(1));
	}

	@Test
	public void northWestLiveNeighborShiftDown() {
		createGeneration(3, 3);

		sut.resurrectCell(0, 1);

		assertThat(sut.getLiveNeighborCount(1, 2), is(1));
	}

	@Test
	public void northLiveNeighbor() {
		createGeneration(3, 3);

		sut.resurrectCell(1, 0);

		assertThat(sut.getLiveNeighborCount(1, 1), is(1));
	}

	@Test
	public void northEastLiveNeighbor() {
		createGeneration(3, 3);

		sut.resurrectCell(2, 0);

		assertThat(sut.getLiveNeighborCount(1, 1), is(1));
	}

	@Test
	public void eastLiveNeighbor() {
		createGeneration(3, 3);

		sut.resurrectCell(0, 1);

		assertThat(sut.getLiveNeighborCount(1, 1), is(1));
	}

	@Test
	public void westLiveNeighbor() {
		createGeneration(3, 3);

		sut.resurrectCell(2, 1);

		assertThat(sut.getLiveNeighborCount(1, 1), is(1));
	}

	@Test
	public void southWestLiveNeighbor() {
		createGeneration(3, 3);

		sut.resurrectCell(0, 2);

		assertThat(sut.getLiveNeighborCount(1, 1), is(1));
	}

	@Test
	public void southLiveNeighbor() {
		createGeneration(3, 3);

		sut.resurrectCell(1, 2);

		assertThat(sut.getLiveNeighborCount(1, 1), is(1));
	}

	@Test
	public void southEastLiveNeighbor() {
		createGeneration(3, 3);

		sut.resurrectCell(2, 2);

		assertThat(sut.getLiveNeighborCount(1, 1), is(1));
	}

	@Test
	public void eightLiveNeighbors() {
		createGeneration(3, 3);

		sut.resurrectCell(0, 0);
		sut.resurrectCell(1, 0);
		sut.resurrectCell(2, 0);
		sut.resurrectCell(0, 1);
		sut.resurrectCell(2, 1);
		sut.resurrectCell(0, 2);
		sut.resurrectCell(1, 2);
		sut.resurrectCell(2, 2);

		assertThat(sut.getLiveNeighborCount(1, 1), is(8));
	}

	@Test
	public void getLiveNeighborsAtBorders() {
		createAllAliveGeneration(3, 3);

		assertThat(sut.getLiveNeighborCount(0, 0), is(3));
		assertThat(sut.getLiveNeighborCount(1, 0), is(5));
		assertThat(sut.getLiveNeighborCount(2, 0), is(3));
		assertThat(sut.getLiveNeighborCount(0, 1), is(5));
		assertThat(sut.getLiveNeighborCount(2, 1), is(5));
		assertThat(sut.getLiveNeighborCount(0, 2), is(3));
		assertThat(sut.getLiveNeighborCount(1, 2), is(5));
		assertThat(sut.getLiveNeighborCount(2, 2), is(3));
	}

	@Test
	public void doubleResurrect() {
		createGeneration(2, 1);

		sut.resurrectCell(1, 0);
		sut.resurrectCell(1, 0);

		assertThat(sut.getLiveNeighborCount(0, 0), is(1));
	}

	@Test
	public void killCell() {
		createAllAliveGeneration(1, 1);

		sut.killCell(0, 0);

		assertThat(sut.isCellAlive(0, 0), is(false));
	}

	@Test
	public void killCellUpdatesLiveNeighborCounts() {
		createAllAliveGeneration(3, 3);

		sut.killCell(1, 1);

		assertThat(sut.getLiveNeighborCount(0, 0), is(2));
		assertThat(sut.getLiveNeighborCount(1, 0), is(4));
		assertThat(sut.getLiveNeighborCount(2, 0), is(2));
		assertThat(sut.getLiveNeighborCount(0, 1), is(4));
		assertThat(sut.getLiveNeighborCount(1, 1), is(8));
		assertThat(sut.getLiveNeighborCount(2, 1), is(4));
		assertThat(sut.getLiveNeighborCount(0, 2), is(2));
		assertThat(sut.getLiveNeighborCount(1, 2), is(4));
		assertThat(sut.getLiveNeighborCount(2, 2), is(2));
	}

	@Test
	public void doubleKill() {
		createAllAliveGeneration(2, 1);

		sut.killCell(1, 0);
		sut.killCell(1, 0);

		assertThat(sut.getLiveNeighborCount(0, 0), is(0));
	}

	@Test
	public void copyConstructorShouldCopyGivenGeneration() {
		createAllAliveGeneration(3, 3);

		Generation copy = new Generation(sut);

		for (int row = 0; row < sut.getHeight(); row++) {
			for (int col = 0; col < sut.getWidth(); col++) {
				assertThat(copy.isCellAlive(col, row), is(sut.isCellAlive(col, row)));
			}
		}
	}

	private void createAllAliveGeneration(int width, int height) {
		createGeneration(width, height);

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				sut.resurrectCell(col, row);
			}
		}
	}

	private void createGeneration(int width, int height) {
		sut = new Generation(width, height);
	}

}
