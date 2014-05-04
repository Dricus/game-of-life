package nl.dricus.gameoflife.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

public class GenerationTest {

	private static final int WIDTH = 100;
	private static final int HEIGHT = 50;

	private Generation cut;

	@Before
	public void setUp() {
		cut = new Generation(WIDTH, HEIGHT);
	}

	@Test
	public void initializeWithDimensions() {
		assertThat(cut.getHeight(), is(HEIGHT));
		assertThat(cut.getWidth(), is(WIDTH));
	}

	@Test
	public void initialCellStateShouldBeDead() {
		assertThat(cut.getCell(0, 0), is(Cell.DEAD));
		assertThat(cut.getCell(10, 10), is(Cell.DEAD));
	}

	@Test
	public void setCellStateAlive() {
		cut.setCell(0, 0, Cell.ALIVE);
		cut.setCell(10, 10, Cell.ALIVE);

		assertThat(cut.getCell(0, 0), is(Cell.ALIVE));
		assertThat(cut.getCell(10, 10), is(Cell.ALIVE));
	}

	@Test
	public void getCellStateOutsideUniverseShouldReturnDead() {
		makeAllCellsAlive();

		assertThat(cut.getCell(-1, -1), is(Cell.DEAD));
		assertThat(cut.getCell(WIDTH, HEIGHT), is(Cell.DEAD));
		assertThat(cut.getCell(10, HEIGHT), is(Cell.DEAD));
		assertThat(cut.getCell(WIDTH, 10), is(Cell.DEAD));
	}

	private void makeAllCellsAlive() {
		IntStream.range(0, HEIGHT).forEach(y -> {
			IntStream.range(0, WIDTH).forEach(x -> cut.setCell(x, y, Cell.ALIVE));
		});
	}

	@Test
	public void shouldHaveZeroLiveNeighbors() {
		assertThat(cut.getLiveNeighorCount(10, 10), is(0));
	}

	@Test
	public void shouldSeeWestLiveNeighbor() {
		cut.setCell(9, 10, Cell.ALIVE);

		assertThat(cut.getLiveNeighorCount(10, 10), is(1));
	}

	@Test
	public void shouldSeeEastLiveNeighbor() {
		cut.setCell(11, 10, Cell.ALIVE);

		assertThat(cut.getLiveNeighorCount(10, 10), is(1));
	}

	@Test
	public void shouldSeeEastAndWestLiveNeighbor() {
		cut.setCell(9, 10, Cell.ALIVE);
		cut.setCell(11, 10, Cell.ALIVE);

		assertThat(cut.getLiveNeighorCount(10, 10), is(2));
	}

	@Test
	public void shouldSeeNorthLiveNeighbor() {
		cut.setCell(10, 11, Cell.ALIVE);

		assertThat(cut.getLiveNeighorCount(10, 10), is(1));
	}

	@Test
	public void shouldSeeSouthLiveNeighbor() {
		cut.setCell(10, 9, Cell.ALIVE);

		assertThat(cut.getLiveNeighorCount(10, 10), is(1));
	}

	@Test
	public void shouldSeeNorthAndSouthLiveNeighbor() {
		cut.setCell(10, 9, Cell.ALIVE);
		cut.setCell(10, 11, Cell.ALIVE);

		assertThat(cut.getLiveNeighorCount(10, 10), is(2));
	}

	@Test
	public void shouldSeeNorthWestLiveNeighbor() {
		cut.setCell(9, 11, Cell.ALIVE);

		assertThat(cut.getLiveNeighorCount(10, 10), is(1));
	}

	@Test
	public void shouldSeeNorthEastLiveNeighbor() {
		cut.setCell(11, 11, Cell.ALIVE);

		assertThat(cut.getLiveNeighorCount(10, 10), is(1));
	}

	@Test
	public void shouldSeeSouthWestLiveNeighbor() {
		cut.setCell(9, 9, Cell.ALIVE);

		assertThat(cut.getLiveNeighorCount(10, 10), is(1));
	}

	@Test
	public void shouldSeeSouthEastLiveNeighbor() {
		cut.setCell(11, 9, Cell.ALIVE);

		assertThat(cut.getLiveNeighorCount(10, 10), is(1));
	}

	@Test
	public void shouldSeeAllLiveNeighbors() {
		cut.setCell(9, 11, Cell.ALIVE);
		cut.setCell(10, 11, Cell.ALIVE);
		cut.setCell(11, 11, Cell.ALIVE);
		cut.setCell(9, 10, Cell.ALIVE);
		cut.setCell(11, 10, Cell.ALIVE);
		cut.setCell(9, 9, Cell.ALIVE);
		cut.setCell(10, 9, Cell.ALIVE);
		cut.setCell(11, 9, Cell.ALIVE);

		assertThat(cut.getLiveNeighorCount(10, 10), is(8));
	}

	@Test
	public void neighborsAtEdges() {
		assertThat(cut.getLiveNeighorCount(0, 0), is(0));
		assertThat(cut.getLiveNeighorCount(10, 0), is(0));
		assertThat(cut.getLiveNeighorCount(WIDTH - 1, 0), is(0));
		assertThat(cut.getLiveNeighorCount(WIDTH - 1, 10), is(0));
		assertThat(cut.getLiveNeighorCount(WIDTH - 1, HEIGHT - 1), is(0));
		assertThat(cut.getLiveNeighorCount(10, HEIGHT - 1), is(0));
		assertThat(cut.getLiveNeighorCount(0, HEIGHT - 1), is(0));
		assertThat(cut.getLiveNeighorCount(0, 10), is(0));
	}

}
