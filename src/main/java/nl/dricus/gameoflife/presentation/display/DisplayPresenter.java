package nl.dricus.gameoflife.presentation.display;

import javafx.animation.Animation.Status;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import javax.inject.Inject;

import nl.dricus.gameoflife.boundary.Game;
import nl.dricus.gameoflife.entity.Generation;

public class DisplayPresenter {

	private static final int SCALE_FACTOR = 16;

	@Inject
	Game game;

	@FXML
	private Canvas canvas;

	@FXML
	private AnchorPane anchorPane;

	private AnimationTimer timer;
	private Timeline timeline;

	@FXML
	public void initialize() {
		initializeGame();

		createAnimation();
		createTimeline();

		adjustCanvasToGameDimensions();
		updateCanvasSizeOnWindowResize();

		changeGameDimensionsWhenCanvasSizeChanges();

		startRendering();
	}

	private void initializeGame() {
		game.setDimensions((int) canvas.getWidth() / SCALE_FACTOR, (int) canvas.getHeight() / SCALE_FACTOR);
		game.randomizeCurrentGeneration();
	}

	private void createAnimation() {
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				render();
			}
		};
	}

	private void createTimeline() {
		timeline = new Timeline();

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(createTickKeyFrame());
	}

	private KeyFrame createTickKeyFrame() {
		return new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				game.tick();
			}
		});
	}

	private void adjustCanvasToGameDimensions() {
		canvas.setWidth(game.getCurrentGeneration().getWidth() * SCALE_FACTOR);
		canvas.setHeight(game.getCurrentGeneration().getHeight() * SCALE_FACTOR);
	}

	private void updateCanvasSizeOnWindowResize() {
		anchorPane.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				canvas.setWidth(newValue.doubleValue());
			}
		});
		anchorPane.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				canvas.setHeight(newValue.doubleValue());
			}
		});
	}

	private void changeGameDimensionsWhenCanvasSizeChanges() {
		canvas.boundsInLocalProperty().addListener(new ChangeListener<Bounds>() {
			@Override
			public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
				game.setDimensions((int) newValue.getWidth() / SCALE_FACTOR, (int) newValue.getHeight() / SCALE_FACTOR);
				game.randomizeCurrentGeneration();
			}
		});
	}

	private void startRendering() {
		timer.start();
	}

	@FXML
	public void onMouseClicked() {
		if (timeline.getStatus() == Status.RUNNING)
			timeline.pause();
		else
			timeline.play();
	}

	private void render() {
		int[] pixels = renderGenerationToPixelArray(game.getCurrentGeneration());

		renderPixelsToCanvas(pixels);
	}

	private int[] renderGenerationToPixelArray(Generation currentGeneration) {
		int scaledHeight = getScaledHeight(currentGeneration);
		int scaledWidth = getScaledWidth(currentGeneration);
		int[] pixels = new int[scaledHeight * scaledWidth];

		for (int row = 0; row < scaledHeight; row++) {
			for (int col = 0; col < scaledWidth; col++) {
				int index = (row * scaledWidth) + col;

				if (currentGeneration.isCellAlive(col / SCALE_FACTOR, row / SCALE_FACTOR))
					pixels[index] = 0xffffffff;
				else
					pixels[index] = 0xff000000;
			}
		}

		return pixels;
	}

	private void renderPixelsToCanvas(int[] pixels) {
		Generation currentGeneration = game.getCurrentGeneration();
		int scaledHeight = getScaledHeight(currentGeneration);
		int scaledWidth = getScaledWidth(currentGeneration);
		PixelWriter pixelWriter = getPixelWriter();

		pixelWriter
				.setPixels(0, 0, scaledWidth, scaledHeight, PixelFormat.getIntArgbInstance(), pixels, 0, scaledWidth);
	}

	private int getScaledHeight(Generation currentGeneration) {
		return currentGeneration.getHeight() * SCALE_FACTOR;
	}

	private int getScaledWidth(Generation currentGeneration) {
		return currentGeneration.getWidth() * SCALE_FACTOR;
	}

	private PixelWriter getPixelWriter() {
		return canvas.getGraphicsContext2D().getPixelWriter();
	}

}
