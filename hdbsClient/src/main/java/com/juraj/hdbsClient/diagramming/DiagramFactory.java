package com.juraj.hdbsClient.diagramming;



import com.mindfusion.common.Orientation;
import com.mindfusion.diagramming.*;
import com.mindfusion.drawing.Align;
import com.mindfusion.drawing.SolidBrush;
import com.mindfusion.drawing.TextFormat;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Juraj on 25.4.2018..
 */
public class DiagramFactory {

    private String schemaXML;
    private Diagram tableDiagram;
    private DiagramView diagramView;

    public DiagramFactory(String schemaXML) {
        this.schemaXML = schemaXML;
    }

    public DiagramView getTableDiagramView() {

        generateTableDiagram();
        return diagramView;
    }

    private void generateTableDiagram() {
        tableDiagram = new Diagram();
        tableDiagram.setAutoResize(AutoResize.RightAndDown);

        //initialize a diagramView that will render the diagram.
        diagramView = new DiagramView(tableDiagram);
        diagramView.setVisible(true);


        //diagram settings
        tableDiagram.setTableColumnCount(4);
        tableDiagram.setTableRowHeight(10f);
        tableDiagram.setShadowsStyle(ShadowsStyle.None);
        tableDiagram.setEnableStyledText(true);

        SAXBuilder sb = new SAXBuilder();

        Document doc = null;
        try {
            doc = sb.build(new ByteArrayInputStream(schemaXML.getBytes(StandardCharsets.UTF_8)));
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //item lists
        List<Element> allTables = new ArrayList<>();
        List<Element> allRelationships = new ArrayList<>();
        List<Element> allGlobalRelationships = new ArrayList<>();

        doc.getRootElement().getChild("databases").getChildren("database").forEach(database -> {
            allTables.addAll(database.getChild("tables").getChildren("table"));
        });

        doc.getRootElement().getChild("databases").getChildren("database").forEach(database -> {
            allRelationships.addAll(database.getChild("relationships").getChildren("relationship"));
        });

        allGlobalRelationships.addAll(doc.getRootElement().getChild("globalRelationships").getChildren("globalRelationship"));

        //set tables
        for (Element table : allTables) {

            Dimension tableSize = new Dimension(50, 30);
            int columnCount = table.getChild("columns").getChildren("column").size();
            TableNode _table = tableDiagram.getFactory()
                    .createTableNode(10, 10, 50, columnCount * 8, 4, columnCount);

            _table.setCaption("<b>" + table.getAttributeValue("id") + "</b>");
            _table.setId(table.getAttributeValue("id"));

            //set style
            _table.setCaptionFormat(new TextFormat(Align.Center, Align.Center));
            _table.setCaptionHeight(7f);
            _table.setRowHeight(10f);
            _table.setAllowResizeColumns(true);
            _table.getColumns().get(0).setWidth(22f);
            _table.setShape(SimpleShape.RoundedRectangle);
            _table.setBrush(new SolidBrush(new Color((int) 153, (int) 179, (int) 255)));

            int rowIndex = 0;
            for (Element column : table.getChild("columns").getChildren("column")) {
                _table.getCell(1, rowIndex).setText("<b>" + column.getAttributeValue("name") + "</b>");
                _table.getCell(2, rowIndex).setText(column.getAttributeValue("dataType"));
                _table.getCell(3, rowIndex).setText(column.getAttributeValue("dataSize"));

                //if the column is a primary key - set an image. If it's a foreign key - set and image
                if (column.getAttributeValue("isPrimaryKey").equals("true")) {
                    try {

                        Image image = ImageIO.read(getClass().getResourceAsStream("/pkey.png"));
                        _table.getCell(0, rowIndex).setImage(image);


                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else if (column.getAttributeValue("isForeignKey").equals("true")) {
                    try {

                        Image image = ImageIO.read(getClass().getResourceAsStream("/fkey.png"));
                        _table.getCell(0, rowIndex).setImage(image);


                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                rowIndex++;
            }
            //make everything fit
            _table.resizeToFitText(true);
            Rectangle2D.Float t_size = _table.getBounds();
            _table.getColumns().get(0).setWidth(7);
            _table.resize(t_size.width + 7, t_size.height);
            _table.resizeToFitImage();
        }


        //set relationships
        for (Element relationship : allRelationships) {
            String[] split = relationship.getAttributeValue("pkColumnId").split("\\.");
            String pkTableNodeId = split[0] + "." + split[1];
            split = relationship.getAttributeValue("fkColumnId").split("\\.");
            String fkTableNodeId = split[0] + "." + split[1];

            TableNode source = (TableNode) tableDiagram.findNodeById(fkTableNodeId);
            TableNode destination = (TableNode) tableDiagram.findNodeById(pkTableNodeId);

            if (source != null && destination != null) {
                int pk_index = -1;
                int fk_index = -1;

                int rowCount = source.getRowCount();

                for (int i = 0; i < rowCount; i++) {
                    Cell cell = source.getCell(1, i);
                    //TODO does not work for multi-relations
                    if (cell.getText().equals("<b>" + relationship.getAttributeValue("pkColumnId").split("\\.")[2] + "</b>")) {
                        pk_index = i;
                        break;
                    }
                }

                rowCount = destination.getRows().size();

                for (int i = 0; i < rowCount; i++) {
                    Cell cell = destination.getCell(1, i);
                    if (cell.getText().equals("<b>" + relationship.getAttributeValue("fkColumnId").split("\\.")[2]+ "</b>")) {
                        fk_index = i;
                        break;
                    }
                }

                DiagramLink link = tableDiagram.getFactory().createDiagramLink(source, pk_index, destination, fk_index);
                link.setBaseShape(ArrowHeads.RevWithLine);
                link.setBaseShapeSize(3f);
                link.setHeadShapeSize(3f);
                link.setShape(LinkShape.Cascading);
            }
        }

        //set global relationships
        for (Element relationship : allGlobalRelationships) {
            String[] split = relationship.getAttributeValue("pkColumnId").split("\\.");
            String pkTableNodeId = split[0] + "." + split[1];
            split = relationship.getAttributeValue("fkColumnId").split("\\.");
            String fkTableNodeId = split[0] + "." + split[1];

            TableNode source = (TableNode) tableDiagram.findNodeById(fkTableNodeId);
            TableNode destination = (TableNode) tableDiagram.findNodeById(pkTableNodeId);

            if (source != null && destination != null) {
                int pk_index = -1;
                int fk_index = -1;

                int rowCount = source.getRowCount();

                for (int i = 0; i < rowCount; i++) {
                    Cell cell = source.getCell(1, i);
                    //TODO does not work for multi-relations
                    if (cell.getText().equals("<b>" + relationship.getAttributeValue("pkColumnId").split("\\.")[2] + "</b>")) {
                        pk_index = i;
                        break;
                    }
                }

                rowCount = destination.getRows().size();

                for (int i = 0; i < rowCount; i++) {
                    Cell cell = destination.getCell(1, i);
                    if (cell.getText().equals("<b>" + relationship.getAttributeValue("fkColumnId").split("\\.")[2]+ "</b>")) {
                        fk_index = i;
                        break;
                    }
                }

                DiagramLink link = tableDiagram.getFactory().createDiagramLink(source, pk_index, destination, fk_index);
                link.setBaseShape(ArrowHeads.RevWithLine);
                link.setBaseShapeSize(3f);
                link.setHeadShapeSize(3f);
                link.setShape(LinkShape.Cascading);
            }
        }

        arrangeTableDiagram();

    }

    private void arrangeTableDiagram() {
        //use LayeredLayout with some initial customization
        LayeredLayout layout = new LayeredLayout(Orientation.Horizontal, 30f, 25f, 5f, 5f);
        layout.arrange(tableDiagram);

        //adjust link position
        for (DiagramLink link : tableDiagram.getLinks()) {

            if (link.getOrigin().getBounds().getX() < link.getDestination().getBounds().getX())
                link.setEndPoint(new Point2D.Float(link.getDestination().getBounds().x, link.getEndPoint().y));
        }
        //re-route all links
        tableDiagram.setLinkRouter(new GridRouter());
        tableDiagram.routeAllLinks();

        //resize the diagram after the layout to fit all items
        tableDiagram.resizeToFitItems(20);
        diagramView.scrollTo(tableDiagram.getBounds().width / 2, 0);

        //customize the links
        tableDiagram.setLinkCrossings(LinkCrossings.Arcs);
        tableDiagram.setRoundedLinks(true);
        tableDiagram.setRoundedLinksRadius(3);
        //redraw the control
        tableDiagram.repaint();
    }

}
