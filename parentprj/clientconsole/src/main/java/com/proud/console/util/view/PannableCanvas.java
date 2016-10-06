package com.proud.console.util.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

//http://stackoverflow.com/questions/29506156/javafx-8-zooming-relative-to-mouse-pointer

public class PannableCanvas extends Pane {
    private static final String CANVAS_CSS = "com/proud/console/util/view/PannableCanvas.css";
    private static final String CANVAS_CLASS ="pannable-canvas";
    
    private static final DoubleProperty SCALE_FACTOR = new SimpleDoubleProperty(1.0);
    

    public PannableCanvas(Double minW,Double minHeight) {
        setMinSize(minW,minHeight);
        setPrefSize(minW,minHeight);
        getStylesheets().add(CANVAS_CSS);
        getStyleClass().add(CANVAS_CLASS);
        

        // add scale transform
        scaleXProperty().bind(SCALE_FACTOR);
        scaleYProperty().bind(SCALE_FACTOR);
    }

    /**
     * Add a grid to the canvas, send it to back
     */
    public void addGrid() {

        double w = getBoundsInLocal().getWidth();
        double h = getBoundsInLocal().getHeight();

        // add grid
        Canvas grid = new Canvas(w, h);

        // don't catch mouse events
        grid.setMouseTransparent(true);

        GraphicsContext gc = grid.getGraphicsContext2D();

            gc.setStroke(Color.GRAY);
        gc.setLineWidth(1);

        // draw grid lines
        double offset = 50;
        for (double i = offset; i < w; i += offset) {
            gc.strokeLine(i, 0, i, h);
            gc.strokeLine(0, i, w, i);
        }

        getChildren().add(grid);

        grid.toBack();
    }

    public double getScale() {
        return SCALE_FACTOR.get();
    }

    public void setScale(double scale) {
        SCALE_FACTOR.set(scale);
    }

    public void setPivot(double x, double y) {
        setTranslateX(getTranslateX() - x);
        setTranslateY(getTranslateY() - y);
    }
 }