package com.spencergang.fem;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	//Primary Stage
	private Stage primaryStage;
	//Root Layout
	private BorderPane rootLayout;
	
	//Automatically Called Method by Application
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("File Encryption Module v2.2");
		initRootLayout();
		initApplication();
	}
	
	//Initializes root layout
	private void initRootLayout(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch(IOException e){
			System.out.println("ERROR LOADING RootLayout.fxml");
		}
	}

	//Initializes our application
	private void initApplication(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Application.fxml"));
			AnchorPane application = (AnchorPane) loader.load();
			rootLayout.setCenter(application);
			Controller controller = loader.getController();
			controller.setMainApp(this);
		}catch(IOException e){
			System.out.println("ERROR LOADING Application.fxml");
		}
	}

	//Returns our main stage
	public Stage getPrimaryStage(){
		return primaryStage;
	}
	
	//Main method to begin it all
	public static void main(String[] args){
		launch(args);
	}
}
