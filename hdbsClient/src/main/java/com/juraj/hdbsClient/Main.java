package com.juraj.hdbsClient;

import com.juraj.hdbsClient.controllers.ContactServiceWindowController;
import com.juraj.hdbsClient.controllers.MainWindowController;
import com.juraj.hdbsClient.hdbsManagement.HdbsManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Juraj on 23.4.2018..
 */
public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ContactServiceWindow.fxml"));
        ContactServiceWindowController contactServiceWindowController = new ContactServiceWindowController();
        fxmlLoader.setController(contactServiceWindowController);
        Stage contactStage = new Stage();
        Parent root;
        try{
            root = fxmlLoader.load();
            //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/appIcon.png")));
            contactStage.setTitle("Contact the HDBS web service");
            contactStage.setScene(new Scene(root));
            contactStage.setMaximized(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        contactStage.showAndWait();

        if (contactServiceWindowController.isServerContacted()){
            String serverUrl = contactServiceWindowController.getValidServerUrl();
            fxmlLoader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
            MainWindowController mainWindowController = new MainWindowController(serverUrl);
            fxmlLoader.setController(mainWindowController);
            try{
                root = fxmlLoader.load();
                //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/appIcon.png")));
                primaryStage.setTitle("HDBS Client");
                primaryStage.setScene(new Scene(root));
                primaryStage.setMaximized(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            primaryStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Could not reach a valid HDBS web service.\nThe client application will close now.", ButtonType.OK);
            alert.setTitle("No HDBS service");
            alert.showAndWait();
        }




    }

    public static void main(String args[]){
        launch(args);
    }
}
