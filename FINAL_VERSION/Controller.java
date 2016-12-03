package com.spencergang.fem;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class Controller {

	//Reference to MainApp
	private MainApp mainApp;
	
	//Cryptographer
	Cryptographer crypto = new Cryptographer();
	
///////////////////////////////////////////////
//////////Variables Encryption/////////////////
///////////////////////////////////////////////
	
	File inputFileEncryption;
	File destinationFileEncryption;
	String destinationFilePathEncryption;
	String selectedKeyEncryption;
	String encryptionPath;
	String encryptionFileName;
	
///////////////////////////////////////////////
/////////Encryption FXML Items/////////////////
///////////////////////////////////////////////

	//Encryption Tab Items
	@FXML
	private Button fileBrowseEncryption;
	@FXML
	private Button helpEncryption;
	@FXML
	private Button beginEncryption;
	@FXML
	private TextField filePathEncryption;
	@FXML
	private TextField destinationPathEncryption;
	@FXML
	private TextField encryptionKey;

///////////////////////////////////////////////
/////////Helper Methods Encryption/////////////
///////////////////////////////////////////////	

	private boolean checkFieldsEncryption(){
		if(filePathEncryption.getText().trim().isEmpty() || destinationPathEncryption.getText().trim().isEmpty() || encryptionKey.getText().trim().isEmpty()){
			return false;
		}else{
			return true;
		}
	}

	private void destinationFileAppendEncryption(){
		destinationFilePathEncryption += destinationPathEncryption.getText();
		destinationFileEncryption = new File(destinationFilePathEncryption);
	}

	private void grabKeyEncryption(){
		selectedKeyEncryption = encryptionKey.getText();
	}
	
	private void emptyFieldsEncryption(){
			filePathEncryption.setText("");
			destinationPathEncryption.setText("");
			encryptionKey.setText("");
	}
	
///////////////////////////////////////////////
/////////Handler Methods Encryption////////////
///////////////////////////////////////////////

	@FXML
	private void handleFileBrowseEncryption(){
		//Pop-Up FileChooser
		FileChooser fileChooser = new FileChooser();
		File file;
		fileChooser.setTitle("Select File");
		file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

		//Grab Path and Name
		encryptionPath = file.getAbsolutePath();
		encryptionFileName = file.getName();

		//Set UI Text
		filePathEncryption.setText(encryptionPath);

		//Set Destination File Path w/o file name appended yet
		destinationFilePathEncryption = encryptionPath.replace(encryptionFileName, "");

		//Set corresponding variables
		inputFileEncryption = file;
	}

	@FXML
	private void handleBeginEncryption() throws Exception{
		if(checkFieldsEncryption() == true){
			destinationFileAppendEncryption();
			grabKeyEncryption();
			crypto.encrypt(inputFileEncryption, destinationFileEncryption, selectedKeyEncryption);
			emptyFieldsEncryption();
			successfulEncryptionAlert();
		}else{
			emptyFieldAlert();
		}
	}

///////////////////////////////////////////////
/////////Variables Decryption//////////////////
///////////////////////////////////////////////	
	
	File inputFileDecryption;
	File destinationFileDecryption;
	File saltFileDecryption;
	File ivFileDecryption;
	String decryptionPath;
	String decryptionFileName;
	String selectedKeyDecryption;
	String destinationFilePathDecryption;
	
///////////////////////////////////////////////
/////////Decryption FXML Items/////////////////
///////////////////////////////////////////////

	//Decryption Tab Items
	@FXML
	private Button fileBrowseDecryption;
	@FXML
	private Button helpDecryption;
	@FXML
	private Button beginDecryption;
	@FXML
	private TextField filePathDecryption;
	@FXML
	private TextField destinationPathDecryption;
	@FXML
	private TextField decryptionKey;
	
///////////////////////////////////////////////
/////////Helper Methods Decryption/////////////
///////////////////////////////////////////////

	private boolean checkFieldsDecryption(){
		if(filePathDecryption.getText().trim().isEmpty() || destinationPathDecryption.getText().trim().isEmpty() || decryptionKey.getText().trim().isEmpty()){
			return false;
		}else{
			return true;
		}
	}
	
	private void emptyFieldsDecryption(){
		filePathDecryption.setText("");
		destinationPathDecryption.setText("");
		decryptionKey.setText("");
	}
	
///////////////////////////////////////////////
////////Handler Methods Decryption/////////////
///////////////////////////////////////////////
	
	@FXML
	private void handleFileBrowseDecryption(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File");
		File file;
		file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		
		//Grab our path and filename
		decryptionPath = file.getAbsolutePath();
		decryptionFileName = file.getName();
		
		//Set UI Text
		filePathDecryption.setText(decryptionPath);
		
		//Get our file path for decrypted file
		destinationFilePathDecryption = decryptionPath.replace(decryptionFileName, "");
		
		//Assign Variables
		inputFileDecryption = file;
	}
	
	@FXML
	private void handleBeginDecryption() throws Exception{
		if(checkFieldsDecryption() == true){
			destinationFilePathDecryption += destinationPathDecryption.getText();
			destinationFileDecryption = new File(destinationFilePathDecryption);
			selectedKeyDecryption = decryptionKey.getText();
			crypto.decrypt(inputFileDecryption, destinationFileDecryption, selectedKeyDecryption);
			emptyFieldsDecryption();
			successfulDecryptionAlert();
		}else{
			emptyFieldAlert();
		}
	}
	
///////////////////////////////////////////////
////Universal Errors and Confirmations/////////
///////////////////////////////////////////////
	
	private void emptyFieldAlert(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("ERROR");
		alert.setHeaderText("Empty Fields");
		alert.setContentText("Please fill out all fields!");
		alert.showAndWait();
	}
	
	private void successfulEncryptionAlert(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("SUCCESS");
		alert.setHeaderText("Encryption Complete");
		alert.setContentText("Your file was successfully encrypted!");
		alert.showAndWait();
	}
	
	private void successfulDecryptionAlert(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("SUCCESS");
		alert.setHeaderText("Decryption Complete");
		alert.setContentText("Your file was successfully decrypted!");
		alert.showAndWait();
	}
	
///////////////////////////////////////////////
//////////////Help Button//////////////////////
///////////////////////////////////////////////
	
	@FXML
	private void handleHelpButton() {
		mainApp.showHelp();
	}
	
///////////////////////////////////////////////
//////////////Default Methods//////////////////
///////////////////////////////////////////////
	
	//Constructor
	public Controller(){
	}
	
	//Initializes the Controller
	@FXML
	private void initialize(){
	}
	
	//Called by MainApp to give reference
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}
}
