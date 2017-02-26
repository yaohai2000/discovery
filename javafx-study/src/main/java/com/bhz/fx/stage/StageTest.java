package com.bhz.fx.stage;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class StageTest extends Application{
	@Override
	public void start(Stage stage) throws InterruptedException{
		Rectangle2D bound = Screen.getPrimary().getVisualBounds();
		System.out.println("Screen : MinX" + bound.getMinX());
		stage.setTitle("Stage with an empty Scene");
		Scene scene = new Scene(new Group(new Button("Hello")));
		stage.setScene(scene);
		stage.show();
		System.out.println("Stage Size: " + stage.getWidth() + "\n Stage Height: " + stage.getHeight()
		+ "\n Stage X Position: " + stage.getX() + "\n Stage Y Position: " + stage.getY());
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
