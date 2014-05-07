package nl.dricus.gameoflife.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GenerationTest {

	private Generation sut;

	@Test
	public void testOneByOneInitialState() {
		createGeneration(1, 1);

		assertThat(sut.isCellAlive(0, 0), is(false));
	}

	@Test
	public void testOneByOneResurrect() {
		createGeneration(1, 1);

		sut.resurrectCell(0, 0);

		assertThat(sut.isCellAlive(0, 0), is(true));
	}

	@Test
	public void testTwoByOneResurrect() {
		createGeneration(2, 1);

		sut.resurrectCell(1, 0);

		assertThat(sut.isCellAlive(0, 0), is(false));
		assertThat(sut.isCellAlive(1, 0), is(true));
	}

	@Test
	public void testOneByTwoResurrect() {
		createGeneration(1, 2);

		sut.resurrectCell(0, 1);

		assertThat(sut.isCellAlive(0, 0), is(false));
		assertThat(sut.isCellAlive(0, 1), is(true));
	}

	@Test
	public void testTwoByTwoResurrect() {
		createGeneration(2, 2);

		sut.resurrectCell(1, 1);

		assertThat(sut.isCellAlive(0, 0), is(false));
		assertThat(sut.isCellAlive(0, 1), is(false));
		assertThat(sut.isCellAlive(1, 0), is(false));
		assertThat(sut.isCellAlive(1, 1), is(true));
	}

	@Test
	public void testThreeByThreeResurrect() {
		createGeneration(3, 3);

		sut.resurrectCell(1, 2);

		assertThat(sut.isCellAlive(2, 1), is(false));
		assertThat(sut.isCellAlive(1, 2), is(true));
	}

	@Test
	public void testZeroLiveNeighbors() {
		createGeneration(3, 3);

		assertThat(sut.getLiveNeighborCount(1, 1), is(0));
	}

	@Test
	public void testNorthWestLiveNeighbor() {
		createGeneration(3, 3);

		sut.resurrectCell(0, 0);

		assertThat(sut.getLiveNeighborCount(1, 1), is(1));
	}

	@Test
	public void testNorthWestLiveNeighborShiftRight() {
		createGeneration(3, 3);

		sut.resurrectCell(1, 0);

		assertThat(sut.getLiveNeighborCount(2, 1), is(1));
	}

	@Test
	public void testNorthWestLiveNeighborShiftDown() {
		createGeneration(3, 3);

		sut.resurrectCell(0, 1);

		assertThat(sut.getLiveNeighborCount(1, 2), is(1));
	}

	@Test
	public void testNorthLiveNeighbor() {
		createGeneration(3, 3);

		sut.resurrectCell(1, 0);

		assertThat(sut.getLiveNeighborCount(1, 1), is(1));
	}

	@Test
	public void testNorthEastLiveNeighbor() {
		createGeneration(3, 3);

		sut.resurrectCell(2, 0);

		assertThat(sut.getLiveNeighborCount(1, 1), is(1));
	}

	@Test
	public void testEastLiveNeighbor() {
		createGeneration(3, 3);

		sut.resurrectCell(0, 1);

		assertThat(sut.getLiveNeighborCount(1, 1), is(1));
	}

	@Test
	public void testWestLiveNeighbor() {
		createGeneration(3, 3);

		sut.resurrectCell(2, 1);

		assertThat(sut.getLiveNeighborCount(1, 1), is(1));
	}

	@Test
	public void testSouthWestLiveNeighbor() {
		createGeneration(3, 3);

		sut.resurrectCell(0, 2);

		assertThat(sut.getLiveNeighborCount(1, 1), is(1));
	}

	@Test
	public void testSouthLiveNeighbor() {
		createGeneration(3, 3);

		sut.resurrectCell(1, 2);

		assertThat(sut.getLiveNeighborCount(1, 1), is(1));
	}

	@Test
	public void testSouthEastLiveNeighbor() {
		createGeneration(3, 3);

		sut.resurrectCell(2, 2);

		assertThat(sut.getLiveNeighborCount(1, 1), is(1));
	}

	@Test
	public void testEightLiveNeighbors() {
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
	public void testGetLiveNeighborsAtBorders() {
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
	public void testDoubleResurrect() {
		createGeneration(2, 1);

		sut.resurrectCell(1, 0);
		sut.resurrectCell(1, 0);

		assertThat(sut.getLiveNeighborCount(0, 0), is(1));
	}

	@Test
	public void testKillCell() {
		createAllAliveGeneration(1, 1);

		sut.killCell(0, 0);

		assertThat(sut.isCellAlive(0, 0), is(false));
	}

	@Test
	public void testKillCellUpdatesLiveNeighborCounts() {
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
	public void testDoubleKill() {
		createAllAliveGeneration(2, 1);

		sut.killCell(1, 0);
		sut.killCell(1, 0);

		assertThat(sut.getLiveNeighborCount(0, 0), is(0));
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
