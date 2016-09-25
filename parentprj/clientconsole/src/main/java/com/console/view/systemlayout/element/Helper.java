package com.console.view.systemlayout.element;

import com.console.util.view.DragContext;
import com.console.util.view.PannableCanvas;
import com.console.view.systemlayout.ISystemLayoutManager;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

/**
 *
 * @author fabry
 */
class Helper {

    public boolean changeLayour(MouseEvent event, ISystemLayoutManager layoutManager, ISystemElement elementClicked) {
        if (!event.getButton().equals(MouseButton.PRIMARY) || event.getClickCount() < 2) {
            return false;
        }
        layoutManager.changeLayout(Arrays.asList(elementClicked));
        return true;
    }

    public boolean showKpiLayout(MouseEvent event, ISystemLayoutManager layoutManager,
            String title, List<ISystemElement> elems) {
        if (!event.getButton().equals(MouseButton.PRIMARY) || event.getClickCount() < 2) {
            return false;
        }
        layoutManager.showKpiLayout(title, elems);
        return true;
    }

    public void onMouseDraggedEventHandler(MouseEvent event, ISystemLayoutManager layoutManager,
            PannableCanvas canvasContainer, ISystemElement node, DragContext nodeDragContext) {

        // left mouse button => dragging
        if (!event.isPrimaryButtonDown() || layoutManager.isLayoutLocked()) {
            return;
        }

        double scale = canvasContainer.getScale();
        Region panel = (Region) node.getContainer();
        double oldX = panel.getTranslateX();
        double oldY = panel.getTranslateY();
        double newX = nodeDragContext.translateAnchorX + ((event.getSceneX() - nodeDragContext.mouseAnchorX) / scale);
        double newY = nodeDragContext.translateAnchorY + ((event.getSceneY() - nodeDragContext.mouseAnchorY) / scale);
        panel.setTranslateX(newX);
        panel.setTranslateY(newY);

        if (node.getParent() == null) {
            return;
        }

        Region parentNode = (Region) node.getParent().getContainer();

        if (!parentNode.getBoundsInParent().contains(panel.getBoundsInParent())) {
            panel.translateXProperty().set(oldX);
            panel.translateYProperty().set(oldY);
        }

        event.consume();
    }

    public void onMousePressedEventHandler(MouseEvent event, ISystemLayoutManager layoutManager,
            DragContext nodeDragContext) {

        // left mouse button => dragging
        if (!event.isPrimaryButtonDown() || layoutManager.isLayoutLocked()) {
            return;
        }

        nodeDragContext.mouseAnchorX = event.getSceneX();
        nodeDragContext.mouseAnchorY = event.getSceneY();

        Node node = (Node) event.getSource();

        nodeDragContext.translateAnchorX = node.getTranslateX();
        nodeDragContext.translateAnchorY = node.getTranslateY();
    }

    public double getAnchorY(Region source, Region target) {
        if (source.translateYProperty().get() < target.translateYProperty().get()) {
            return source.translateYProperty().get() + source.heightProperty().get();
        } else {
            return source.translateYProperty().get();
        }

    }

    public double getAnchorX(Region source, Region target, Number newValue) {

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
