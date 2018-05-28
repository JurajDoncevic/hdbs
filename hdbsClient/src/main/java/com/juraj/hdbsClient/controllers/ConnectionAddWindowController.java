package com.juraj.hdbsClient.controllers;

import com.juraj.hdbsClient.hdbsManagement.HdbsManager;
import com.juraj.hdbsClient.utils.ActionResult;
import com.juraj.hdbsClient.utils.DbVendor;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


/**
 * Created by Juraj on 24.4.2018..
 */
public class ConnectionAddWindowController {

    private HdbsManager hdbsManager;

    @FXML
    private PasswordField psf_password;

    @FXML
    private ComboBox cbx_dbVendor;

    @FXML
    private Button btn_cancel;

    @FXML
    private TextField tbx_serverURL;

    @FXML
    private Button btn_connect;

    @FXML
    private TextField tbx_dbName;

    @FXML
    private TextField tbx_username;

    @FXML
    private Label lbl_message;

    public ConnectionAddWindowController(HdbsManager hdbsManager){
        this.hdbsManager = hdbsManager;
    }

    @FXML
    public void initialize(){
        cbx_dbVendor.setItems(FXCollections.observableArrayList(DbVendor.values()));
        cbx_dbVendor.getSelectionModel().selectFirst();
        lbl_message.setText("");
    }

    @FXML
    private void click_btn_cancel(MouseEvent me){
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void click_btn_connect(MouseEvent me){
        lbl_message.setText("");
        boolean success = hdbsManager.addConnection(tbx_serverURL.getText(), tbx_dbName.getText(),
                tbx_username.getText(), psf_password.getText(), (DbVendor) cbx_dbVendor.getSelectionModel().getSelectedItem());
        if (success){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Connection established.", ButtonType.OK);
            alert.setTitle("Connection established");
            alert.showAndWait();

            Stage stage = (Stage) btn_connect.getScene().getWindow();
            stage.close();
        } else {
            lbl_message.setText("Could not connect to database.");
        }
    }

}
