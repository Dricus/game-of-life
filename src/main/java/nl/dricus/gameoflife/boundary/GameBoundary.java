package nl.dricus.gameoflife.boundary;

import nl.dricus.gameoflife.entity.Generation;

public class GameBoundary {

	private Generation currentGeneration;

	public GameBoundary(int width, int height) {
		currentGeneration = new Generation(width, height);
	}

	public Generation getCurrentGeneration() {
		return currentGeneration;
	}

	public void tick() {
	}

}
