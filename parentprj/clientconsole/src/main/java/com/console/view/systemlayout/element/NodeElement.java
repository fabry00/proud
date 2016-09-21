package com.console.view.systemlayout.element;

import com.console.domain.AppNode;
import com.console.util.AppImage;
import com.console.util.view.NodeGestures;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
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

    private static final String NODE_CSS = "node.css";
    private static final String NODE_CLASS = "record-sales";
    private static final int WIDTH = 84;
    private static final int HEIGHT = 118;

    private final AppNode node;
    private final VBox panel = new VBox();
    private final Map<SystemElement, Line> connections = new HashMap<>();

    public NodeElement(AppNode node) {
        this.node = node;
    }

    @Override
    public Node draw(double x, double y, final NodeGestures nodeGestures) {
        initNode(x, y, nodeGestures);

        return panel;
    }

    @Override
    public Node geContainer() {
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

        panel.getChildren().add(nodeName);

        ImageView imgView = new ImageView();
        // TODO check node element image dimension
        imgView.setFitHeight(80);
        imgView.setFitWidth(80);
        imgView.setLayoutX(2);
        imgView.setLayoutY(28);
        imgView.pickOnBoundsProperty().setValue(true);
        imgView.setPreserveRatio(true);
        Image img = AppImage.IMG_PC_GREEN_64;
        imgView.setImage(img);
        panel.getChildren().add(imgView);
        panel.addEventFilter(MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        panel.addEventFilter(MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

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
        DoubleProperty y = new SimpleDoubleProperty(panel.translateYProperty().get());
        panel.translateXProperty().addListener((observable, oldValue, newValue) -> {

            x.setValue((double) newValue + panel.widthProperty().get() / 2);
            y.setValue(panel.translateYProperty().get() + panel.heightProperty().get());
            /*if(p2.translateYProperty().get() < p3.translateYProperty().get()) {
                //x.setValue((double) newValue + p2.widthProperty().get() /2);
                y.setValue(p2.translateYProperty().get() +p2.heightProperty().get() );
            } else {
                y.setValue(p2.translateYProperty().get());
            }
            x.setValue((double) newValue + p2.widthProperty().get() /2);*/
        });

        DoubleProperty x2 = new SimpleDoubleProperty(relatedElement.geContainer()
                .translateXProperty().get() + 84 / 2);
        relatedElement.geContainer().translateXProperty().addListener((observable, oldValue, newValue) -> {
            x2.setValue((double) newValue + ((Region) relatedElement.geContainer()).widthProperty().get() / 2);
        });

        return new Connection(x, y, x2, relatedElement.geContainer().translateYProperty());
    }

}
