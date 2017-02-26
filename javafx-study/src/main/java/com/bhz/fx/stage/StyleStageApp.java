package com.bhz.fx.stage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StyleStageApp extends Application {
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Label styleLabel = new Label("Stage Style");
		Button closeButton = new Button("Close");
		closeButton.setOnAction(e->Platform.exit());
		VBox root = new VBox();
		root.getChildren().addAll(styleLabel,closeButton);
		Scene scene = new Scene(root,100,70);
		primaryStage.setScene(scene);
		
		primaryStage.setTitle("The Style of a Stage");
		
		this.show(primaryStage, styleLabel, StageStyle.DECORATED);
		
//		this.show(primaryStage, styleLabel, StageStyle.UNDECORATED);
//		this.show(primaryStage, styleLabel, StageStyle.TRANSPARENT);
//		this.show(primaryStage, styleLabel, StageStyle.UNIFIED);
//		this.show(primaryStage, styleLabel, StageStyle.UTILITY);
		
	}
	
	private void show(Stage stage,Label styleLabel,StageStyle style){
		styleLabel.setText(style.toString());
		
		stage.initStyle(style);
		if(style==StageStyle.TRANSPARENT){
			stage.getScene().setFill(null);
			stage.getScene().getRoot().setStyle("-fx-background-color:transparent");
		}else if(style==StageStyle.UNIFIED){
			stage.getScene().setFill(Color.TRANSPARENT);
		}
		stage.show();
		
	}

}
