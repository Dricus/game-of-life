package nl.dricus.gameoflife.boundary;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import nl.dricus.gameoflife.entity.Generation;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class GameTest {

	private Game game;
	private Generation currentGeneration;

	@Test
	public void newGameShouldHaveInitialGeneration() {
		createGame(1, 1);

		assertThat(currentGeneration, notNullValue());
	}

	@Test
	public void generationDimensionsShouldMatchConstructorParameters() {
		createGame(1, 2);

		assertThat(currentGeneration.getWidth(), is(1));
		assertThat(currentGeneration.getHeight(), is(2));
	}

	@Test
	public void tickCreatesNewGeneration() {
		createGame(1, 1);

		Generation initialGeneration = currentGeneration;
		tick();

		assertThat(currentGeneration, CoreMatchers.not(sameInstance(initialGeneration)));
	}

	@Test
	public void liveCellWithNoLiveNeighborDies() {
		createGame(1, 1);

		currentGeneration.resurrectCell(0, 0);
		tick();

		assertThat(currentGeneration.isCellAlive(0, 0), is(false));
	}

	@Test
	public void deadCellWithNoLiveNeighborStaysDead() {
		createGame(1, 1);

		tick();

		assertThat(currentGeneration.isCellAlive(0, 0), is(false));
	}

	@Test
	public void liveCellWithTwoLiveNeighborsStaysAlive() {
		createGame(2, 2);

		setRow(0, "++");
		setRow(1, "-+");

		tick();

		assertThat(currentGeneration.isCellAlive(0, 0), is(true));
	}

	@Test
	public void liveCellWithThreeLiveNeighborsStaysAlive() {
		createGame(2, 2);

		setRow(0, "++");
		setRow(1, "++");

		tick();

		assertThat(currentGeneration.isCellAlive(0, 0), is(true));
	}

	@Test
	public void twoLiveCellsHorizontalShouldBothDie() {
		createGame(2, 1);

		setRow(0, "++");

		tick();

		assertThat(currentGeneration.isCellAlive(0, 0), is(false));
		assertThat(currentGeneration.isCellAlive(1, 0), is(false));
	}

	@Test
	public void twoLiveCellsVerticalShouldBothDie() {
		createGame(1, 2);

		setRow(0, "+");
		setRow(1, "+");

		tick();

		assertThat(currentGeneration.isCellAlive(0, 0), is(false));
		assertThat(currentGeneration.isCellAlive(1, 0), is(false));
	}

	@Test
	public void liveCellWithFourLiveNeighborsDies() {
		createGame(3, 3);

		setRow(0, "++-");
		setRow(1, "-+-");
		setRow(2, "++-");

		tick();

		assertThat(currentGeneration.isCellAlive(1, 1), is(false));
	}

	@Test
	public void liveCellWithFiveLiveNeighborsDies() {
		createGame(3, 3);

		setRow(0, "++-");
		setRow(1, "-++");
		setRow(2, "++-");

		tick();

		assertThat(currentGeneration.isCellAlive(1, 1), is(false));
	}

	@Test
	public void liveCellsWithTwoLiveNeighborsStaysAlive() {
		createGame(3, 3);

		setRow(0, "++-");
		setRow(1, "-+-");
		setRow(2, "++-");

		tick();

		assertThat(currentGeneration.isCellAlive(0, 2), is(true));
		assertThat(currentGeneration.isCellAlive(1, 2), is(true));
	}

	@Test
	public void deadCellWithThreeLiveNeighborsBecomesAlive() {
		createGame(3, 3);

		setRow(0, "+--");
		setRow(1, "---");
		setRow(2, "++-");

		tick();

		assertThat(currentGeneration.isCellAlive(1, 1), is(true));
	}

	private void setRow(int row, String cells) {
		for (int col = 0; col < currentGeneration.getWidth(); col++) {
			if (cells.charAt(col) == '-')
				currentGeneration.killCell(col, row);
			else
				currentGeneration.resurrectCell(col, row);
		}
	}

	private void createGame(int width, int height) {
		game = new Game(width, height);

		currentGeneration = game.getCurrentGeneration();
	}

	private void tick() {
		game.tick();

		currentGeneration = game.getCurrentGeneration();
	}

}
