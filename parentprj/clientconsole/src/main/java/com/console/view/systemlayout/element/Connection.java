package com.console.view.systemlayout.element;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fabrizio Faustinoni
 */
public class Connection extends Line {

    private static final Color BASE_COLOR = Color.GRAY.deriveColor(0, 1, 1, 0.5);
    private static final Color SELECTED_COLOR_BLACK = Color.BLACK.deriveColor(0, 1, 1, 0.7);
    private static final Color SELECTED_COLOR_AQUA = Color.AQUA.deriveColor(0, 1, 1, 0.7);
    private static final Color SELECTED_COLOR_BLUE = Color.BLUE.deriveColor(0, 1, 1, 0.7);
    private static final Color SELECTED_COLOR_CHOCOLATE = Color.CHOCOLATE.deriveColor(0, 1, 1, 0.7);
    private static final Color SELECTED_COLOR_RED = Color.RED.deriveColor(0, 1, 1, 0.7);
    private static final Color SELECTED_COLOR_YELLOW = Color.YELLOWGREEN.deriveColor(0, 1, 1, 1);
    private static final List<Color> SELECTED_COLORS = new ArrayList<Color>() {{
        add(SELECTED_COLOR_BLACK);
        add(SELECTED_COLOR_AQUA);
        add(SELECTED_COLOR_BLUE);
        add(SELECTED_COLOR_CHOCOLATE);
        add(SELECTED_COLOR_RED);
        add(SELECTED_COLOR_YELLOW);
    }};

    private static int colorUsedIndex = -1;

    public Connection(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY) {
        startXProperty().bind(startX);
        startYProperty().bind(startY);
        endXProperty().bind(endX);
        endYProperty().bind(endY);
        setStrokeWidth(2);
        setStroke(BASE_COLOR);
        setStrokeLineCap(StrokeLineCap.BUTT);
        getStrokeDashArray().setAll(10.0, 5.0);
        setMouseTransparent(true);
    }

    public static Color getRandomColor() {
        //int index = new Random().nextInt(SELECTED_COLORS.size());
        colorUsedIndex++;
        if (colorUsedIndex > 100) {
            // Just give a limit
            colorUsedIndex = 0;
        }
        return SELECTED_COLORS.get(colorUsedIndex % SELECTED_COLORS.size());
    }

    public void selected(Color color) {

        setStroke(color);
    }

    public void unSelected() {
        setStroke(BASE_COLOR);
    }
}
