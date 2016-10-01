package com.console.util.view;

import com.console.view.systemlayout.ISystemLayoutManager;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Listeners for making the nodes draggable via left mouse button. Considers if
 * parent is zoomed.
 * Created by Fabrizio Faustinoni on 21/09/2016.
 */
public class NodeGestures {

    //private final Logger logger = Logger.getLogger(NodeGestures.class);
    private DragContext nodeDragContext = new DragContext();

    private PannableCanvas canvas;
    private ISystemLayoutManager layoutManager;
    private final EventHandler<MouseEvent> onMousePressedEventHandler = (MouseEvent event) -> {
        // left mouse button => dragging
        if (!event.isPrimaryButtonDown() || this.layoutManager.isLayoutLocked()) {
            return;
        }

        nodeDragContext.mouseAnchorX = event.getSceneX();
        nodeDragContext.mouseAnchorY = event.getSceneY();

        Node node = (Node) event.getSource();

        nodeDragContext.translateAnchorX = node.getTranslateX();
        nodeDragContext.translateAnchorY = node.getTranslateY();
    };
    private final EventHandler<MouseEvent> onMouseDraggedEventHandler = (MouseEvent event) -> {
        // left mouse button => dragging
        if (!event.isPrimaryButtonDown() || this.layoutManager.isLayoutLocked()) {
            return;
        }

        double scale = canvas.getScale();

        Node node = (Node) event.getSource();

        //double oldX = node.getTranslateX();
        //double oldY = node.getTranslateY();
        double newX = nodeDragContext.translateAnchorX + ((event.getSceneX() - nodeDragContext.mouseAnchorX) / scale);
        double newY = nodeDragContext.translateAnchorY + ((event.getSceneY() - nodeDragContext.mouseAnchorY) / scale);
        node.setTranslateX(newX);
        node.setTranslateY(newY);

        /*if (node instanceof ISystemElement) {
            ISystemElement element = (ISystemElement) node;
            Region currentNode = (Region) element.getContainer();
            Region parentNode = (Region) element.getParent().getContainer();
            if (!parentNode.getBoundsInParent().contains(currentNode.getBoundsInParent())) {
                currentNode.translateXProperty().set(oldX);
                currentNode.translateYProperty().set(oldY);
            }
        }*/

        event.consume();
    };

    public NodeGestures(PannableCanvas canvas, ISystemLayoutManager layoutManager) {
        this.canvas = canvas;
        this.layoutManager = layoutManager;
    }

    public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
        return onMousePressedEventHandler;
    }

    public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
        return onMouseDraggedEventHandler;
    }
}
