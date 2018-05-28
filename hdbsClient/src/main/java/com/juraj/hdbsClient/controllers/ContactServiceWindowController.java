package com.juraj.hdbsClient.controllers;

import com.juraj.hdbsClient.hdbsManagement.HdbsManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Created by Juraj on 25.4.2018..
 */
public class ContactServiceWindowController {

    @FXML
    private TextField tbx_serverUrl;

    @FXML
    private Label lbl_errorMessage;

    @FXML
    private Button btn_connect;

    private boolean serverContacted;

    private String validServerUrl;

    public ContactServiceWindowController(){
        serverContacted = false;
        validServerUrl = "";
    }

    @FXML
    public void initialize(){

    }

    @FXML
    private void click_btn_connect(MouseEvent me){
        HdbsManager hdbsManager = new HdbsManager(tbx_serverUrl.getText().trim());
        lbl_errorMessage.setText("");
        if (hdbsManager.contactServer()){
            serverContacted = true;
            validServerUrl = tbx_serverUrl.getText().trim();
            ((Stage)btn_connect.getScene().getWindow()).close();
        } else {
            serverContacted = false;
            validServerUrl = "";
            lbl_errorMessage.setText("Could not reach a HDBS service at given url.");
        }
    }

    public boolean isServerContacted() {
        return serverContacted;
    }

    public String getValidServerUrl() {
        return validServerUrl;
    }
}
