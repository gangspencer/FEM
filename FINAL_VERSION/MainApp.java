package com.spencergang.fem;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	//Primary Stage
	private Stage primaryStage;
	//Root Layout
	private BorderPane rootLayout;
	
	/*
	 * (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 * 
	 * 		Our start method is required as our MainApp extends Application, which requires the start method to assign our primary stage.
	 * 		Within we also initialize our root and application layout. See the reference above to acquire more knowledge on the 
	 * 		javafx.application.Application package plus its dependencies and requirements.
	 */
	@Override
	public void start(Stage primaryStage) {
		//Automatically included variable, set accordingly
		this.primaryStage = primaryStage;
		//Set the title of our application (Seen at top of app)
		this.primaryStage.setTitle("File Encryption Module");
		//Prevent the resizing of our application
		this.primaryStage.setResizable(false);
		//Init our root and application layouts
		initRootLayout();
		initApplication();
	}
	
	/*
	 *  Root Layout:
	 *  	Our root layouts purpose is to set the backdrop scene for our layout
	 *  	essentially becoming our 'root' layout. None of the application is physically located on 
	 *  	our root layout. However our root layout acts as a container to display our application
	 *  	layout.
	 */
	private void initRootLayout(){
		try{
			//Gives us the ability to load '.fxml' files
			FXMLLoader loader = new FXMLLoader();
			//Give our loader our file url (found within local package, no directory searching required)
			loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
			//Set our rootLayout to our loaded fxml file (casted as BorderPane)
			rootLayout = (BorderPane) loader.load();
			//Set the scene (ooooooohhhhhhhhhh)
			Scene scene = new Scene(rootLayout);
			//Assign our scene to primaryStage
			primaryStage.setScene(scene);
			//Show our primary stage
			primaryStage.show();
		}catch(IOException e){
			//ERROR CHECKING
			System.out.println("ERROR LOADING RootLayout.fxml");
		}
	}

	/*
	 * Application Layout:
	 * 		Our application layouts purpose is to load our application fxml file, assign our application
	 * 		layout, place our application layout inside the root layout, and assign a controller to our 
	 * 		application layout. The application layout is where our buttons/tabs/textfields/fxml attributes 
	 * 		are located. Our application layout uses our 'Cryptographer' class to make use of AES ciphering.
	 */
	private void initApplication(){
		try{
			//Gives us the ability to load '.fxml' files
			FXMLLoader loader = new FXMLLoader();
			//Give our loader our file url (found within local package, no directory searching required)
			loader.setLocation(MainApp.class.getResource("Application.fxml"));
			//Set our applicaton to our loaded fxml file (casted as AnchorPane)
			AnchorPane application = (AnchorPane) loader.load();
			//Place our application in our RootLayout
			rootLayout.setCenter(application);
			//Set the controller for our application layout
			Controller controller = loader.getController();
			//For reference by the controller
			controller.setMainApp(this);
		}catch(IOException e){
			//ERROR CHECKING
			System.out.println("ERROR LOADING Application.fxml");
		}
	}
	
	/*
	 * Help Layout:
	 * 		Our help layouts purpose is to load our help fxml layout. To do this as an unattached pop-up to
	 * 		our main application we must create a new scene. Within this scene we must make sure that this scene
	 * 		cannot affect the rest of our application. To do so we set our Modality to WINDOW_MODAL blocking
	 * 		access of this scene to the rest of our application. The help screen receives it's own controller.
	 */
	public void showHelp(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Help.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Help");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			HelpController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			
			dialogStage.showAndWait();
		}catch(IOException e){
			System.out.println("ERROR LOADING Help.fxml");
		}
	}

	//Returns our main stage
	public Stage getPrimaryStage(){
		return primaryStage;
	}
	
	//Main method
	public static void main(String[] args){
		launch(args);
	}
}
