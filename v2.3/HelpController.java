package com.spencergang.fem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HelpController {
	
	private Stage dialogStage;
	
	@FXML
	private Button okay;
	
	@FXML
	private void initialize(){
	}
	
	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}
	
	@FXML
	private void handleOkay(){
		dialogStage.close();
	}
}
