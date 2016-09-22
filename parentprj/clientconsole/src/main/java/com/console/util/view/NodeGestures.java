package com.console.util.view;

/**
 * Created by exfaff on 21/09/2016.
 */

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Listeners for making the nodes draggable via left mouse button. Considers if parent is zoomed.
 */
public class NodeGestures {

    private DragContext nodeDragContext = new DragContext();

    PannableCanvas canvas;

    public NodeGestures(PannableCanvas canvas) {
        this.canvas = canvas;

    }

    public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
        return onMousePressedEventHandler;
    }

    public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
        return onMouseDraggedEventHandler;
    }
    
    public EventHandler<MouseEvent> getOnLayerClickedEventHandler() {
        return onLayerClickedEventHandler;
    }

    private final EventHandler<MouseEvent> onMousePressedEventHandler = (MouseEvent event) -> {
        // left mouse button => dragging
        if (!event.isPrimaryButtonDown())
            return;
        
        nodeDragContext.mouseAnchorX = event.getSceneX();
        nodeDragContext.mouseAnchorY = event.getSceneY();
        
        Node node = (Node) event.getSource();
        
        nodeDragContext.translateAnchorX = node.getTranslateX();
        nodeDragContext.translateAnchorY = node.getTranslateY();
    };

    private final EventHandler<MouseEvent> onMouseDraggedEventHandler = (MouseEvent event) -> {
        // left mouse button => dragging
        if (!event.isPrimaryButtonDown())
            return;
        
        double scale = canvas.getScale();
        
        Node node = (Node) event.getSource();
        
        node.setTranslateX(nodeDragContext.translateAnchorX + ((event.getSceneX() - nodeDragContext.mouseAnchorX) / scale));
        node.setTranslateY(nodeDragContext.translateAnchorY + ((event.getSceneY() - nodeDragContext.mouseAnchorY) / scale));
        
        event.consume();
    };
    
    private final EventHandler<MouseEvent> onLayerClickedEventHandler = (MouseEvent event) -> {
        
        if(!event.getButton().equals(MouseButton.PRIMARY)|| event.getClickCount() < 2){
            return;
        }
        
        System.out.println("Doubleclick");
        
    };
}