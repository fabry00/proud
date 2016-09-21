package com.console.view.systemlayout.element;

import com.console.domain.AppNode;
import com.console.util.AppImage;
import com.console.util.NodeUtil;
import com.console.util.view.NodeGestures;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author fabry
 */
public class NodeElement implements SystemElement {

    private final AppNode node;

    public NodeElement(AppNode node) {
        this.node = node;
    }

    @Override
    public Node draw(double x, double y, final NodeGestures nodeGestures) {
        VBox panel = new VBox();
        panel.setPrefWidth(84);
        panel.setPrefHeight(118);
        panel.getStylesheets().add("node.css");
        panel.getStyleClass().add("record-sales");
        panel.setTranslateX(x);
        panel.setTranslateY(y);

        Label nodeName = new Label(node.getNode());
        nodeName.prefHeightProperty().bind(panel.prefHeightProperty());
        nodeName.prefWidthProperty().bind(panel.prefWidthProperty());
        nodeName.setWrapText(true);
        nodeName.setTextAlignment(TextAlignment.CENTER);

        panel.getChildren().add(nodeName);

        ImageView imgView = new ImageView();
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
        return panel;
    }

}
