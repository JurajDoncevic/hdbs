package com.juraj.hdbsClient.controllers;

import com.juraj.hdbsClient.hdbsManagement.HdbsManager;
import com.juraj.hdbsClient.metamodel.GlobalRelationship;
import com.juraj.hdbsClient.utils.ActionResult;
import com.juraj.hdbsClient.utils.ActionResultType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Created by Juraj on 24.4.2018..
 */
public class GlobalRelationshipWindowController {

    @FXML
    private Button btn_add;

    @FXML
    private TextField tbx_primaryKeyId;

    @FXML
    private TextField tbx_foreignKeyId;

    @FXML
    private Label lbl_errorMessage;

    private HdbsManager hdbsManager;

    public GlobalRelationshipWindowController(HdbsManager hdbsManager) {
        this.hdbsManager = hdbsManager;
    }

    @FXML
    public void initialize(){
        lbl_errorMessage.setText("");
    }

    @FXML
    private void click_btn_add(MouseEvent me){
        lbl_errorMessage.setText("");
        ActionResult actionResult = hdbsManager.addGlobalRelationship(new GlobalRelationship(tbx_primaryKeyId.getText(), tbx_foreignKeyId.getText()));

        if (actionResult.getActionResultType() == ActionResultType.SUCCESS){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, actionResult.toString());
            alert.setTitle("Global relationship added");
            alert.showAndWait();
            ((Stage)btn_add.getScene().getWindow()).close();
        } else {
            lbl_errorMessage.setText(actionResult.toString());
        }
    }


}
