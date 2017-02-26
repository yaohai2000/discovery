package com.bhz.fx.intro;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloFXApp extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Hello JavaFX Application");
		Label nameLbl = new Label("Enter your name: ");
		TextField nametf = new TextField();
		
		Label msg = new Label();
		msg.setStyle("-fx-text-fill:blue;");
		
		Button sayHelloBtn = new Button("Say Hello");
		Button exitBtn = new Button("Exit");
		
		sayHelloBtn.setOnAction(event -> {
			String name = nametf.getText();
			if(name.trim().length() > 0){
				msg.setText("Hello " + name);
			}else{
				msg.setText("Hello there");
			}
		});
		
		exitBtn.setOnAction(event->{
			Platform.exit();
		});
		VBox root = new VBox();
		root.setSpacing(5);
		
		root.getChildren().addAll(nameLbl,nametf,msg,sayHelloBtn,exitBtn);
		Scene s = new Scene(root,350,150);
		primaryStage.setScene(s);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

}
