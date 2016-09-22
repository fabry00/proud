package com.console.view.systemlayout.element;

import com.console.domain.AppElement;
import com.console.domain.ElementInfo;
import com.console.util.AppImage;
import com.console.util.view.NodeGestures;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author fabry
 */
public class NodeElement implements SystemElement {

    private static final String NODE_CSS = "com/console/view/systemlayout/element/node.css";
    private static final String NODE_CLASS = "system-node";
    private static final int WIDTH = 84;
    private static final int HEIGHT = 118;

    private final AppElement node;
    private final VBox panel = new VBox();
    private final Map<SystemElement, Line> connections = new HashMap<>();

    public NodeElement(AppElement node) {
        this.node = node;
    }

    @Override
    public Node draw(double x, double y, final NodeGestures nodeGestures) {
        initNode(x, y, nodeGestures);

        return panel;
    }

    @Override
    public Node getContainer() {
        return panel;
    }

    private void initNode(double x, double y, final NodeGestures nodeGestures) {
        panel.setPrefWidth(WIDTH);
        panel.setPrefHeight(HEIGHT);
        panel.getStylesheets().add(NODE_CSS);
        panel.getStyleClass().add(NODE_CLASS);
        panel.setTranslateX(x);
        panel.setTranslateY(y);

        Label nodeName = new Label(node.getName());
        nodeName.prefHeightProperty().bind(panel.prefHeightProperty());
        nodeName.prefWidthProperty().bind(panel.prefWidthProperty());
        nodeName.setWrapText(true);
        nodeName.setTextAlignment(TextAlignment.CENTER);
        nodeName.setAlignment(Pos.CENTER);

        panel.getChildren().add(nodeName);

        ImageView imgView = new ImageView();
        // TODO check node element image dimension
     /*   imgView.setFitHeight(80);
        imgView.setFitWidth(80);
        imgView.setLayoutX(2);
        imgView.setLayoutY(28);*/
        imgView.pickOnBoundsProperty().setValue(true);
        imgView.setPreserveRatio(true);
        Image img = AppImage.IMG_PC_GREEN_64;
        imgView.setImage(img);
        panel.getChildren().add(imgView);
        panel.addEventFilter(MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        panel.addEventFilter(MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        String sep = System.getProperty("line.separator");
        String tooltipText = "Node info:"+sep;
        tooltipText+="IP Address: ";
        tooltipText+= node.getInfo().get(ElementInfo.Type.IP).getValue();
        
        Tooltip tooltip = new Tooltip(tooltipText);

        Tooltip.install(panel, tooltip);

    }

    @Override
    public void createConnections(List<SystemElement> relatedElements) {
        relatedElements.stream().forEach((e) -> {
            connections.put(e, createConnection(e));
        });
    }

    @Override
    public Collection<Line> getConnections() {
        return connections.values();
    }
    

    private Line createConnection(SystemElement relatedElement) {
        DoubleProperty x = new SimpleDoubleProperty(panel.translateXProperty().get() + 84 / 2);
        //DoubleProperty y = new SimpleDoubleProperty(panel.translateYProperty().get());
        DoubleProperty y = new SimpleDoubleProperty(getAnchorY(panel, (Region) relatedElement.getContainer()));

        DoubleProperty x2 = new SimpleDoubleProperty(relatedElement.getContainer().translateXProperty().get() + 84 / 2);
        DoubleProperty y2 = new SimpleDoubleProperty(getAnchorY((Region) relatedElement.getContainer(), panel));

        panel.translateXProperty().addListener((observable, oldValue, newValue) -> {
            // Source node
            y.setValue(getAnchorY(panel, (Region) relatedElement.getContainer()));
            x.setValue(getAnchorX(panel, (Region) relatedElement.getContainer(), newValue));

            // TargetNode
            y2.setValue(getAnchorY((Region) relatedElement.getContainer(), panel));
        });

        relatedElement.getContainer().translateXProperty().addListener((observable, oldValue, newValue) -> {
            // Source node            
            x2.setValue(getAnchorX((Region) relatedElement.getContainer(), panel, newValue));
            y2.setValue(getAnchorY((Region) relatedElement.getContainer(), panel));

            // TargetNode
            y.setValue(getAnchorY(panel, (Region) relatedElement.getContainer()));
        });

        return new Connection(x, y, x2, y2);
    }
    
    @Override
    public AppElement.Type getType() {
        return node.getType();
    }

    @Override
    public String toString() {
        return node.getName();
    }

    private double getAnchorY(Region source, Region target) {
        if (source.translateYProperty().get() < target.translateYProperty().get()) {
            return source.translateYProperty().get() + source.heightProperty().get();
        } else {
            return source.translateYProperty().get();
        }

    }

    private double getAnchorX(Region source, Region target, Number newValue) {

        // TODO get anchor X as anchor y
        /*if (source.translateXProperty().get() + source.layoutXProperty().get()
         < target.translateXProperty().get()) {
      
         return source.translateXProperty().get() + source.layoutXProperty().get();
         } else {
         //   return source.translateYProperty().get();
         }*/
        return (double) newValue + source.widthProperty().get() / 2;
    }

}