package nl.dricus.gameoflife.boundary;

import java.util.Random;

import nl.dricus.gameoflife.entity.Generation;

public class Game {

	private int width;
	private int height;

	private Generation currentGeneration;
	private Generation previousGeneration;

	public Game() {
		this(800, 600);
	}

	public Game(int width, int height) {
		initializeGeneration(width, height);
	}

	public void setDimensions(int width, int height) {
		initializeGeneration(width, height);
	}

	private void initializeGeneration(int width, int height) {
		this.width = width;
		this.height = height;

		currentGeneration = new Generation(width, height);
	}

	public Generation getCurrentGeneration() {
		return currentGeneration;
	}

	public void tick() {
		previousGeneration = currentGeneration;
		currentGeneration = new Generation(currentGeneration);

		populateCurrentGeneration(previousGeneration);
	}

	private void populateCurrentGeneration(Generation previousGeneration) {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				populateCell(row, col);
			}
		}
	}

	private void populateCell(int row, int col) {
		killIfAppropriate(row, col);
		resurrectIfAppropriate(row, col);
	}

	private void killIfAppropriate(int row, int col) {
		if (tooFewLiveNeighbors(row, col) || tooManyLiveNeighbors(row, col))
			currentGeneration.killCell(col, row);
	}

	private void resurrectIfAppropriate(int row, int col) {
		if (shouldBeResurrected(row, col))
			currentGeneration.resurrectCell(col, row);
	}

	private boolean tooFewLiveNeighbors(int row, int col) {
		return previousGeneration.getLiveNeighborCount(col, row) < 2;
	}

	private boolean tooManyLiveNeighbors(int row, int col) {
		return previousGeneration.getLiveNeighborCount(col, row) > 3;
	}

	private boolean shouldBeResurrected(int row, int col) {
		return previousGeneration.getLiveNeighborCount(col, row) == 3;
	}

	public void randomizeCurrentGeneration() {
		Random random = new Random();

		for (int row = 0; row < currentGeneration.getHeight(); row++) {
			for (int col = 0; col < currentGeneration.getWidth(); col++) {
				if (random.nextBoolean())
					currentGeneration.resurrectCell(col, row);
			}
		}
	}

}
