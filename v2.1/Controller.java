package com.spencergang.fem;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class Controller {
	@FXML
	private TextField path;
	@FXML
	private TextField destination;
	@FXML
	private TextField key;
	@FXML
	private CheckBox encryption;
	@FXML
	private CheckBox decryption;
	@FXML
	private Button begin;
	@FXML 
	private Button browse;
	
	private MainApp mainApp;
	
	int selectedKey;
	String filePath;
	String fileName;
	String destinationPath;
	String destinationFile;
	String finalDestinationPath;
	
	//Default Constructor;
	public Controller(){
	}
	
	@FXML
	private void initalize(){
	}
	
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////ENCRYPTION AND DECRYPTION METHODS/////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////
	
	private void handleEncryption(){
		if(checkFields() == true){	
			int c;
			int selectedKey = Integer.parseInt(key.getText());
			/*String filePath = path.getText();
			String destinationPath = destination.getText();*/
		
		
			try{
				FileReader inputStream = new FileReader(filePath);
				FileWriter outputStream = new FileWriter(finalDestinationPath);
			
				while((c = inputStream.read()) != -1){
					c += selectedKey;
					outputStream.write(c);
				}
				inputStream.close();
				outputStream.close();
				encryptionAlert();
			}catch(IOException e){
				System.out.println("FILE NOT FOUND");
				failureAlert();
			}
		}else{
			fieldAlert();
		}
	}
	
	private void handleDecryption(){
		if(checkFields() == true){
			int c;
			int selectedKey = Integer.parseInt(key.getText());
			/*String filePath = path.getText();
			String destinationPath = destination.getText();*/
		
			try{
				FileReader inputStream = new FileReader(filePath);
				FileWriter outputStream = new FileWriter(finalDestinationPath);
			
				while((c = inputStream.read()) != -1){
					c -= selectedKey;
					outputStream.write(c);
				}
				inputStream.close();
				outputStream.close();
				decryptionAlert();
			}catch(IOException e){
				failureAlert();
				System.out.println("FILE NOT FOUND");
			}
		}else{
			fieldAlert();
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////BUTTON HANDLER METHODS////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////	
	
	@FXML
	private void handleBegin(){
		
		if(!(destination.getText().trim().isEmpty() || destination.getText() == "" || destination.getText() == null)){
			finalDestinationPath = destinationPath + destination.getText();
		}
		
		if(checkBoxes() == 1){
			handleEncryption();
		}else if(checkBoxes() == 2){
			handleDecryption();
		}else if(checkBoxes() == 3){
			doubleCheckAlert();
		}else if(checkBoxes() == 4){
			noCheckAlert();
		}
	}
	
	@FXML
	private void handleBrowse(){
		FileChooser fileChooser = new FileChooser();
		File file;
		fileChooser.setTitle("Select File");
		file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		filePath = file.getAbsolutePath();
		fileName = file.getName();
		System.out.println(fileName);
		path.setText(filePath);
		destinationPath = filePath.replace(fileName, "");
		System.out.println(destinationPath);
	}
	
////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////HELPER METHODS////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////
	
	private boolean checkFields(){
		if(path.getText().trim().isEmpty() || destination.getText().trim().isEmpty() || key.getText().trim().isEmpty() || destination.getText() == "" || key.getText() == "" || path.getText() == null || destination.getText() == null || key.getText() == null){
			return false;
		}else{
			return true;
		}
	}
	
	private int checkBoxes(){
		if(encryption.isSelected() && decryption.isSelected()){
			return 3;
		}else if(decryption.isSelected()){
			return 2;
		}else if(encryption.isSelected()){
			return 1;
		}else{
			return 4;
		}
	}
		
////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////ERROR AND CONFIRMATION ALERTS/////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////
	
	private void encryptionAlert(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle("Encryption Successful");
		alert.setHeaderText("File Encrypted");
		alert.setContentText("Your file has been successfully encrypted!");
		alert.showAndWait();
	}
	
	private void fieldAlert(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle("ERROR");
		alert.setHeaderText("Empty Field(s)");
		alert.setContentText("Check that you have filled out all fields");
		alert.showAndWait();
	}
	
	private void decryptionAlert(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle("Decryption Successful");
		alert.setHeaderText("File Decrypted");
		alert.setContentText("Your file has been successfully decrypted!");
		alert.showAndWait();
	}
	
	private void failureAlert(){
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle("ERROR");
		alert.setHeaderText("File Not Found");
		alert.setContentText("Please check your path values");
		alert.showAndWait();
	}
	
	private void doubleCheckAlert(){
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle("ERROR");
		alert.setHeaderText("Selection Error");
		alert.setContentText("Please select only one option");
		alert.showAndWait();
	}
	
	private void noCheckAlert(){
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle("ERROR");
		alert.setHeaderText("Selection Error");
		alert.setContentText("Please select an option");
		alert.showAndWait();
	}
}
