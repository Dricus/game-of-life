package nl.dricus.gameoflife.app;

import nl.dricus.gameoflife.presentation.display.DisplayView;

import com.airhacks.afterburner.views.FXMLView;

public class Application extends FXMLApplication {

	@Override
	protected String getTitle() {
		return "Conway's Game of Life";
	}

	@Override
	protected FXMLView createAppRootView() {
		return new DisplayView();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
