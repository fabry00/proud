package com.proud.console.util;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Fabrizio Faustinoni
 */
public class NodeUtil {

    public void anchorToPane(Node node, Double val) {
        AnchorPane.setBottomAnchor(node, val);
        AnchorPane.setLeftAnchor(node, val);
        AnchorPane.setRightAnchor(node, val);
        AnchorPane.setTopAnchor(node, val);
    }

    public void anchorToPaneTop(Node node, Double val) {
        AnchorPane.setTopAnchor(node, val);
    }

    public void anchorToPaneLeft(Node node, Double val) {
        AnchorPane.setLeftAnchor(node, val);
    }

    public void anchorToPaneRight(Node node, Double val) {
        AnchorPane.setRightAnchor(node, val);
    }

    public void anchorToPaneBottom(Node node, Double val) {
        AnchorPane.setBottomAnchor(node, val);
    }
}
