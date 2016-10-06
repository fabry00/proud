package com.proud.console.util.view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * Created by Fabrizio Faustinoni on 21/09/2016.
 * /**
 * Listeners for making the scene's canvas draggable and zoomable
 */
public class SceneGestures {

    private static final double MAX_SCALE = 10.0d;
    private static final double MIN_SCALE = .1d;
    private final EventHandler<MouseEvent> onMouseDraggedEventHandler = (MouseEvent event) -> {
        // right mouse button => panning
        if (!event.isSecondaryButtonDown())
            return;

        /*  canvas.setTranslateX(sceneDragContext.translateAnchorX + event.getSceneX() - sceneDragContext.mouseAnchorX);
        canvas.setTranslateY(sceneDragContext.translateAnchorY + event.getSceneY() - sceneDragContext.mouseAnchorY);*/
        event.consume();
    };


    private PannableCanvas canvas;
    /**
     * Mouse wheel handler: zoom to pivot point
     */
    private final EventHandler<ScrollEvent> onScrollEventHandler = (ScrollEvent event) -> {
        double delta = 1.2;

        double scale = canvas.getScale(); // currently we only use Y, same value is used for X
        double oldScale = scale;

        if (event.getDeltaY() < 0)
            scale /= delta;
        else
            scale *= delta;

        scale = clamp(scale, MIN_SCALE, MAX_SCALE);

        double f = (scale / oldScale) - 1;

        double dx = (event.getSceneX() - (canvas.getBoundsInParent().getWidth() / 2 + canvas.getBoundsInParent().getMinX()));
        double dy = (event.getSceneY() - (canvas.getBoundsInParent().getHeight() / 2 + canvas.getBoundsInParent().getMinY()));

        canvas.setScale(scale);

        // note: pivot value must be untransformed, i. e. without scaling
        canvas.setPivot(f * dx, f * dy);

        event.consume();
    };
    private DragContext sceneDragContext = new DragContext();
    private final EventHandler<MouseEvent> onMousePressedEventHandler = (MouseEvent event) -> {
        //System.out.println("onMousePressedEventHandler");
        // right mouse button => panning
        if (!event.isSecondaryButtonDown())
            return;

        sceneDragContext.mouseAnchorX = event.getSceneX();
        sceneDragContext.mouseAnchorY = event.getSceneY();

        sceneDragContext.translateAnchorX = canvas.getTranslateX();
        sceneDragContext.translateAnchorY = canvas.getTranslateY();
    };

    public SceneGestures(PannableCanvas canvas) {
        this.canvas = canvas;
    }

    private static double clamp(double value, double min, double max) {

        if (Double.compare(value, min) < 0)
            return min;

        if (Double.compare(value, max) > 0)
            return max;

        return value;
    }

    public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
        return onMousePressedEventHandler;
    }

    public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
        return onMouseDraggedEventHandler;
    }

    public EventHandler<ScrollEvent> getOnScrollEventHandler() {
        return onScrollEventHandler;
    }
}
