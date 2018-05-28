package com.juraj.hdbsClient.controllers;

import com.juraj.hdbsClient.diagramming.DiagramFactory;
import com.juraj.hdbsClient.hdbsManagement.HdbsManager;
import com.juraj.hdbsClient.metamodel.GlobalRelationship;
import com.juraj.hdbsClient.utils.ActionResult;
import com.juraj.hdbsClient.utils.ActionResultType;
import com.juraj.hdbsClient.utils.DataSet;
import com.juraj.hdbsClient.utils.ToObservableList2D;
import com.mindfusion.diagramming.DiagramView;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Juraj on 24.4.2018..
 */
public class MainWindowController{

    @FXML
    private Button btn_removeGlobalRelationship;

    @FXML
    private Button btn_viewDiagram;

    @FXML
    private Button btn_addGlobalRelationship;

    @FXML
    private Button btn_addConnection;

    @FXML
    private Button btn_removeConnection;

    @FXML
    private Button btn_sendQuery;

    @FXML
    private TableView tbv_queryResults;

    @FXML
    private ListView lst_connections;

    @FXML
    private  ListView lst_globalRelationships;

    @FXML
    private TextArea txa_queryText;

    @FXML
    private Label lbl_queryMessage;

    @FXML
    private Button btn_getSchemaData;

    @FXML
    private Button btn_checkRelationshipConsistency;


    private HdbsManager hdbsManager;

    private ObservableList<GlobalRelationship> globalRelationships;
    private ObservableList<String> connections;

    public MainWindowController(String hdbsUrl) {
        hdbsManager = new HdbsManager(hdbsUrl);
    }

    @FXML
    public void initialize(){
        lbl_queryMessage.setText("");
    }

    @FXML
    private void click_btn_getSchemaData(MouseEvent me){
        loadSchemaData();

    }

    private void loadSchemaData(){
        List<String> connectedDbs = hdbsManager.getConnectedDatabases();
        List<GlobalRelationship> globalRelationshipsList = hdbsManager.getGlobalRelationships();

        globalRelationships = FXCollections.observableList(globalRelationshipsList);
        connections = FXCollections.observableList(connectedDbs);

        lst_connections.setItems(connections);
        lst_globalRelationships.setItems(globalRelationships);
        lst_connections.refresh();
        lst_globalRelationships.refresh();
    }

    @FXML
    private void click_btn_sendQuery(MouseEvent me){
        String queryText = txa_queryText.getText().trim();
        ActionResult actionResult = hdbsManager.sendQuery(queryText);

        if (actionResult.getActionResultType() == ActionResultType.SUCCESS){
            tbv_queryResults.getItems().clear();
            tbv_queryResults.getColumns().clear();

            lbl_queryMessage.setText("");
            DataSet dataSet = new DataSet(actionResult.getData());

            ObservableList<ObservableList<Object>> data;
            List<String> columnNames = dataSet.getColumnNames();

            int i = 0;
            for(String columnName: columnNames){
                final int j = i;
                TableColumn _column = new TableColumn(columnName);

                _column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>() {

                    public ObservableValue call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(String.valueOf(param.getValue().get(j)));
                    }
                });
                i++;
                tbv_queryResults.getColumns().addAll(_column);
            }

            data = ToObservableList2D.convertFromDataSet(dataSet);

            tbv_queryResults.setItems(data);
            //set columns
            /*List<TableColumn<String, String>> tableColumnList = new ArrayList<>();
            for (String columnName : dataSet.getColumnNames()){
                tableColumnList.add(new TableColumn<>(columnName));
            }
            ObservableList<TableColumn<String,String>> tableColumns = FXCollections.observableList(tableColumnList);


            tbv_queryResults.getColumns().addAll(tableColumns);
            */

            //set data for each column


            tbv_queryResults.refresh();
        } else {
            lbl_queryMessage.setText(actionResult.getMessage());
        }
    }

    @FXML
    private void click_btn_removeConnection(MouseEvent me){
        if (lst_connections.getSelectionModel().getSelectedItem() != null) {
            String selectedConnection = lst_connections.getSelectionModel().getSelectedItem().toString();
            hdbsManager.removeConnection(selectedConnection);
        }
        loadSchemaData();

    }

    @FXML
    private void click_btn_removeGlobalRelationship(MouseEvent me){
        if (lst_globalRelationships.getSelectionModel().getSelectedItem() != null){
            GlobalRelationship selectedGlobalRelationship = (GlobalRelationship) lst_globalRelationships.getSelectionModel().getSelectedItem();
            hdbsManager.removeGlobalRelationship(selectedGlobalRelationship);
        }
        loadSchemaData();
    }

    @FXML
    private void click_btn_addGlobalRelationship(MouseEvent me){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GlobalRelationshipAddWindow.fxml"));
        GlobalRelationshipWindowController globalRelationshipWindowController = new GlobalRelationshipWindowController(hdbsManager);
        fxmlLoader.setController(globalRelationshipWindowController);
        Parent root;
        Stage childStage = new Stage();
        try{
            root = fxmlLoader.load();
            //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/appIcon.png")));
            childStage.setTitle("Add global relationship");
            childStage.setScene(new Scene(root));
            childStage.setMaximized(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        childStage.showAndWait();

        loadSchemaData();
    }

    @FXML
    private void click_btn_addConnection(MouseEvent me){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ConnectionAddWindow.fxml"));
        ConnectionAddWindowController connectionAddWindowController = new ConnectionAddWindowController(hdbsManager);
        fxmlLoader.setController(connectionAddWindowController);
        Parent root;
        Stage childStage = new Stage();
        try{
            root = fxmlLoader.load();
            //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/appIcon.png")));
            childStage.setTitle("Add connection");
            childStage.setScene(new Scene(root));
            childStage.setMaximized(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        childStage.showAndWait();

        loadSchemaData();
    }

    @FXML
    private void click_btn_checkRelationshipConsistency(MouseEvent me){
        if (lst_globalRelationships.getSelectionModel().getSelectedItem() != null){
            ActionResult actionResult = hdbsManager.isRelationshipConsistent((GlobalRelationship) lst_globalRelationships.getSelectionModel().getSelectedItem());
            Alert alert;
            if (actionResult.getActionResultType() == ActionResultType.SUCCESS){
                if (actionResult.getData().equals("true")){
                    alert = new Alert(Alert.AlertType.CONFIRMATION, actionResult.getMessage());
                    alert.setTitle("Relationship consistent");
                } else {
                    alert = new Alert(Alert.AlertType.WARNING, actionResult.getMessage());
                    alert.setTitle("Relationship inconsistent");
                }

            } else {
                alert = new Alert(Alert.AlertType.ERROR, actionResult.getMessage());
                alert.setTitle("Error");
            }
            alert.showAndWait();
        }
    }

    @FXML
    private void click_btn_viewDiagram(MouseEvent me){
        //TODO: open window, pass xml
        String schemaXML = hdbsManager.getGlobalSchema();

        DiagramFactory diagramFactory = new DiagramFactory(schemaXML);

        DiagramView diagramView = diagramFactory.getTableDiagramView();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DiagramWindow.fxml"));
        DiagramWindowController diagramWindowController = new DiagramWindowController(diagramView);
        fxmlLoader.setController(diagramWindowController);
        Parent root;
        Stage childStage = new Stage();
        try{
            root = fxmlLoader.load();
            //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/appIcon.png")));
            childStage.setTitle("Current schema diagram");
            childStage.setScene(new Scene(root));
            childStage.setMaximized(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        childStage.show();

        loadSchemaData();

    }
}
