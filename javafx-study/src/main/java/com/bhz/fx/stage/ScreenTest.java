package com.bhz.fx.stage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ScreenTest extends Application{
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ObservableList<Screen> screens = Screen.getScreens();
		System.out.println("Total screen is " + screens.size());
		for(Screen s:screens){
			printScreen(s);
		}
		Platform.exit();
	}
	
	private void printScreen(Screen s){
		System.out.println(s);
		System.out.println("The Screen DPI: " + s.getDpi());
	}
	
}
