package nl.dricus.gameoflife.boundary;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import nl.dricus.gameoflife.entity.Cell;
import nl.dricus.gameoflife.entity.Generation;

import org.junit.Before;
import org.junit.Test;

public class GameBoundaryTest {

	private static final int WIDTH = 100;
	private static final int HEIGHT = 50;
	private static final int TEST_X = 10;
	private static final int TEST_Y = 10;

	private GameBoundary cut;
	private Generation initialGeneration;

	@Before
	public void setUp() {
		cut = new GameBoundary(WIDTH, HEIGHT);
		initialGeneration = cut.getCurrentGeneration();
	}

	@Test
	public void shouldStartWithNewGeneration() {
		assertNotNull(initialGeneration);
	}

	@Test
	public void generationHasCorrectDimensions() {
		assertThat(initialGeneration.getWidth(), is(WIDTH));
		assertThat(initialGeneration.getHeight(), is(HEIGHT));
	}

	@Test
	public void liveCellWithZeroLiveNeighborsShouldDie() {
		initialGeneration.setCell(TEST_X, TEST_Y, Cell.ALIVE);

		cut.tick();

		Generation currentGeneration = cut.getCurrentGeneration();
		assertThat(currentGeneration.getCell(TEST_X, TEST_Y), is(Cell.DEAD));
	}

	@Test
	public void liveCellWithTwoLiveNeighborsShouldLive() {
		createCellWithTwoLiveNeighbors();

		cut.tick();

		Generation currentGeneration = cut.getCurrentGeneration();
		assertThat(currentGeneration.getCell(TEST_X, TEST_Y), is(Cell.ALIVE));
	}

	private void createCellWithTwoLiveNeighbors() {
		initialGeneration.setCell(9, 11, Cell.ALIVE);
		initialGeneration.setCell(11, 11, Cell.ALIVE);
		initialGeneration.setCell(TEST_X, TEST_Y, Cell.ALIVE);
	}

}
