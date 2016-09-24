package com.console.view.systemlayout;

/**
 * Created by exfaff on 21/09/2016.
 */
import com.console.util.view.DragContext;
import com.console.util.view.PannableCanvas;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import org.apache.log4j.Logger;

/**
 * Listeners for making the nodes draggable via left mouse button. Considers if
 * parent is zoomed.
 */
public class NodeGestures {

    //private final Logger logger = Logger.getLogger(NodeGestures.class);
    private DragContext nodeDragContext = new DragContext();

    private PannableCanvas canvas;
    private ISystemLayoutManager layoutManager;

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

        node.setTranslateX(nodeDragContext.translateAnchorX + ((event.getSceneX() - nodeDragContext.mouseAnchorX) / scale));
        node.setTranslateY(nodeDragContext.translateAnchorY + ((event.getSceneY() - nodeDragContext.mouseAnchorY) / scale));

        event.consume();
    };
}
