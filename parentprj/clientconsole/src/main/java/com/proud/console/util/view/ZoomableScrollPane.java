package com.proud.console.util.view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.transform.Scale;

/**
 * A Zomable scroll pane
 * Created by Fabrizio Faustinoni on 21/09/2016.
 */
public class ZoomableScrollPane extends ScrollPane {
    private Group zoomGroup;
    private Scale scaleTransform;
    private Node content;

    public ZoomableScrollPane(Node content)
    {
        this.content = content;
        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(content);
        setContent(contentGroup);
        scaleTransform = new Scale(5, 5, 0, 0);
        zoomGroup.getTransforms().add(scaleTransform);

    }
  }