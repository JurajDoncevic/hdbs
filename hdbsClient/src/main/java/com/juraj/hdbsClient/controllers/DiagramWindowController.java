package com.juraj.hdbsClient.controllers;

import com.mindfusion.diagramming.DiagramView;
import com.mindfusion.diagramming.ZoomControl;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Juraj on 25.4.2018..
 */
public class DiagramWindowController {

    private DiagramView diagramView;

    @FXML
    private javafx.scene.control.ScrollPane pn_diagram;

    public DiagramWindowController(DiagramView diagramView){
        this.diagramView = diagramView;
    }

    @FXML
    public void initialize(){
        diagramView.setVisible(true);
        diagramView.setAllowInplaceEdit(false);

        //TODO: zoomer does not appear bcs it is not a javafx comp; problems placing it
        //provide a zoomer for the diagram
        ZoomControl zoomer = new ZoomControl();
        zoomer.setView(diagramView);
        zoomer.setPreferredSize(new Dimension(70, 50));
        zoomer.setVisible(true);




        //use a scroll pane to host large diagrams
        /*JScrollPane _scrollPane = new JScrollPane(diagramView);
        _scrollPane.setVisible(true);
        _scrollPane.setAutoscrolls(true);
        */

        JPanel jPanel = new JPanel();
        jPanel.add(diagramView);
        final SwingNode swingNode = new SwingNode();

        createSwingContent(swingNode, jPanel);

        AnchorPane.setTopAnchor(swingNode, 0.0);
        AnchorPane.setBottomAnchor(swingNode, 0.0);
        AnchorPane.setRightAnchor(swingNode, 0.0);
        AnchorPane.setLeftAnchor(swingNode, 0.0);
        pn_diagram.setContent(swingNode);
    }

    private void createSwingContent(final SwingNode swingNode, JComponent graphComponent) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                swingNode.setContent(graphComponent);
            }
        });
    }
}
