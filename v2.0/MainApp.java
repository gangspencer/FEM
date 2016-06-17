package com.spencergang.fem;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("File Encryption Module");
		initRootLayout();
		initApplication();
	}
	
	public void initRootLayout(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch(IOException e){
			System.out.println("ROOT LOADER FAILURE");
		}
	}
	
	public void initApplication(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Application.fxml"));
			AnchorPane application = (AnchorPane) loader.load();
			rootLayout.setCenter(application);
			Controller controller = loader.getController();
			controller.setMainApp(this);
		}catch(IOException e){
			System.out.println("APPLICATION LOADER FAILURE");
		}
	}
	
	public Stage getPrimaryStage(){
		return primaryStage;
	}
	
	public static void main(String[] args){
		launch(args);
	}
}
