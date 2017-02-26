package com.bhz.fx.node;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GroupApp extends Application {
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Button btn1 = new Button("Small Button");
		javafx.scene.control.Button btn2 = new Button("This is a big button");
		Group root = new Group();
		root.getChildren().addAll(btn2,btn1);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
