package com.console.util.view;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

/**
 * BORROWED FROM:
 * http://andrewtill.blogspot.com/2012/12/dragging-to-resize-javafx-region.html
 * Modified to support all side resizing (no edges)
 *
 * <p>
 * <p>
 * {@link DragResizer} can be used to add mouse listeners to a {@link Region}
 * and make it resizable by the user by clicking and dragging the border in the
 * same way as a window.
 * <p>
 * Only height resizing is currently implemented. Usage:
 * <pre>DragResizer.makeResizable(myAnchorPane);</pre>
 *
 * @author atill, hitchcock9307
 */
public class DragResizer {

    /**
     * The margin around the control that a user can click in to start resizing
     * the region.
     */
    private final int RESIZE_MARGIN = 2;

    private final Region region;

    private double y, x;

    private boolean initMinHeight;

    private short dragging = 0;

    private final short NOTDRAGGING = 0;
    private final short NORTH = 1;
    private final short SOUTH = 2;
    private final short EAST = 3;
    private final short WEST = 4;
    private final Cursor defaultCursor;
    private final ILayoutManager layoutManager;
    private double minWith;
    private double minHeight;

    public DragResizer(Region aRegion, Cursor defaultCursor, ILayoutManager layoutManager) {
        region = aRegion;
        this.defaultCursor = defaultCursor;
        this.layoutManager = layoutManager;
    }

    public void makeResizable(double minWith, double minHeight) {
        this.minWith = minWith;
        this.minHeight = minHeight;
        region.setOnMousePressed((MouseEvent event) -> {
            mousePressed(event);
        });
        region.setOnMouseDragged((MouseEvent event) -> {
            mouseDragged(event);
        });
        region.setOnMouseMoved((MouseEvent event) -> {
            mouseOver(event);
        });
        region.setOnMouseReleased((MouseEvent event) -> {
            mouseReleased(event);
        });
    }

    protected void mouseReleased(MouseEvent event) {
        initMinHeight = false; //Reset each time
        dragging = NOTDRAGGING;
        region.setCursor(Cursor.DEFAULT);
    }

    protected void mouseOver(MouseEvent event) {
        if (layoutManager.isLayoutLocked()) {
            region.setCursor(this.defaultCursor);
            return;
        }

        if (isInDraggableZoneS(event) || dragging == SOUTH) {
            region.setCursor(Cursor.S_RESIZE);
        } else if (isInDraggableZoneE(event) || dragging == EAST) {
            region.setCursor(Cursor.E_RESIZE);
        } else if (isInDraggableZoneN(event) || dragging == NORTH) {
            region.setCursor(Cursor.N_RESIZE);
        } else if (isInDraggableZoneW(event) || dragging == WEST) {
            region.setCursor(Cursor.W_RESIZE);
        } else {
            region.setCursor(this.defaultCursor);
        }
    }

    private boolean isInDraggableZoneN(MouseEvent event) {
        return event.getY() < RESIZE_MARGIN;
    }

    private boolean isInDraggableZoneW(MouseEvent event) {
        return event.getX() < RESIZE_MARGIN;
    }

    private boolean isInDraggableZoneS(MouseEvent event) {
        return event.getY() > (region.getHeight() - RESIZE_MARGIN);
    }

    private boolean isInDraggableZoneE(MouseEvent event) {
        return event.getX() > (region.getWidth() - RESIZE_MARGIN);
    }

    private void mouseDragged(MouseEvent event) {
        if (layoutManager.isLayoutLocked()) {
            return;
        }
        if (dragging == SOUTH) {
            region.setMinHeight(event.getY());
        } else if (dragging == EAST) {
            region.setMinWidth(event.getX());
        } else if (dragging == NORTH) {
            double prevMin = region.getMinHeight();
            region.setMinHeight(region.getMinHeight() - event.getY());
            if (region.getMinHeight() < region.getPrefHeight()) {
                region.setMinHeight(region.getPrefHeight());
                region.setTranslateY(region.getTranslateY() - (region.getPrefHeight() - prevMin));

                //Marks this Event as consumed. This stops its further propagation.
                event.consume();
                return;
            }
            if (region.getMinHeight() > region.getPrefHeight() || event.getY() < 0) {
                region.setTranslateY(region.getTranslateY() + event.getY());
            }
        } else if (dragging == WEST) {
            double prevMin = region.getMinWidth();
            region.setMinWidth(region.getMinWidth() - event.getX());
            if (region.getMinWidth() < region.getPrefWidth()) {
                region.setMinWidth(region.getPrefWidth());
                region.setTranslateX(region.getTranslateX() - (region.getPrefWidth() - prevMin));

                //Marks this Event as consumed. This stops its further propagation.
                event.consume();
                return;
            }
            if (region.getMinWidth() > region.getPrefWidth() || event.getX() < 0) {
                region.setTranslateX(region.getTranslateX() + event.getX());
            }
        }

        //Marks this Event as consumed. This stops its further propagation.
        event.consume();
    }

    private void mousePressed(MouseEvent event) {
        // ignore clicks outside of the draggable margin
        if (isInDraggableZoneE(event)) {
            dragging = EAST;
        } else if (isInDraggableZoneS(event)) {
            dragging = SOUTH;
        } else if (isInDraggableZoneN(event)) {
            dragging = NORTH;
        } else if (isInDraggableZoneW(event)) {
            dragging = WEST;
        } else {
            return;
        }

        // make sure that the minimum height is set to the current height once,
        // setting a min height that is smaller than the current height will
        // have no effect
        if (!initMinHeight) {
            region.setMinHeight(region.getHeight());
            region.setMinWidth(region.getWidth());
            initMinHeight = true;
        }

    }
}
