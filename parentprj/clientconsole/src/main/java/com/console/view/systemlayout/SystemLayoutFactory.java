package com.console.view.systemlayout;

import com.console.domain.AppElement;
import com.console.util.view.NodeGestures;
import com.console.util.view.PannableCanvas;
import com.console.view.systemlayout.element.LayerElement;
import com.console.view.systemlayout.element.NodeElement;
import com.console.view.systemlayout.element.SystemElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
class SystemLayoutFactory {

    private static final double NODE_X_GAP = 150.0;
    private static final double LAYER_Y_GAP = 250.0;
    private static final double LAYER_START_X = 20.0;
    private static final double LAYER_START_Y = 32.0;
    private static final double NODE_START_X = 30.0;
    private static final double NODE_START_Y = 37.0;

    private final Logger logger = Logger.getLogger(SystemLayoutFactory.class);

    void draw(PannableCanvas canvas, NodeGestures gestures, ObservableList<AppElement> layers) {

        Map<AppElement, SystemElement> elements = new HashMap<>();

        double y = NODE_START_Y;

        // Draw layers in reverse order
        for (int i = layers.size() - 1; i >= 0; i--) {

            AppElement layer = layers.get(i);
            if (layer.getType().equals(AppElement.Type.Layer)) {
                logger.debug("Drawing layer: " + layer.getName());

                Map<AppElement, SystemElement> leyerElemens
                        = createElements(canvas, gestures, layer.getNodes(), y);

                createLayer(canvas, gestures, layer, leyerElemens, y);
                elements.putAll(leyerElemens);
                y += LAYER_Y_GAP;
            } else {
                logger.error("Drawing node outside a layer is not supported yet");
            }
        }

        createConnections(elements, canvas);
    }

    private Map<AppElement, SystemElement> createElements(PannableCanvas canvas,
            NodeGestures gestures, ObservableList<AppElement> nodes, double y) {

        double x = NODE_START_X;

        Map<AppElement, SystemElement> elements = new HashMap<>();
        for (AppElement node : nodes) {
            if (node.getType().equals(AppElement.Type.Node)) {
                logger.debug("Drawing node: " + node.getName());
                SystemElement element = new NodeElement(node);
                canvas.getChildren().add(element.draw(x, y, gestures));
                x += NODE_X_GAP;
                elements.put(node, element);
            } else {
                logger.error("Drawing nested layers is not supported yet");
            }
        }
        return elements;
    }

    private void createConnections(Map<AppElement, SystemElement> elements, PannableCanvas canvas) {
        elements.forEach((k, v) -> {
            List<SystemElement> relatedElements = getRelatedElements(elements, k);
            logger.debug("Connectiong: " + k.getName() + " with:" + relatedElements.toString());
            v.createConnections(relatedElements);
            canvas.getChildren().addAll(v.getConnections());
        });
    }

    private List<SystemElement> getRelatedElements(Map<AppElement, SystemElement> elements, AppElement node) {
        List<SystemElement> relatedElements = new ArrayList<>();
        Set<AppElement> connections = node.getConnections();
        for (AppElement r : connections) {
            relatedElements.add(elements.get(r));
        }
        return relatedElements;
    }

    private void createLayer(PannableCanvas canvas, NodeGestures gestures, AppElement layer,
            Map<AppElement, SystemElement> leyerElemens, double y) {

        double x = getLayerX(leyerElemens.values());
        SystemElement element = new LayerElement(layer, leyerElemens.values(), NODE_X_GAP, LAYER_START_Y);
        Node node = element.draw(x, y - LAYER_START_Y, gestures);
        canvas.getChildren().add(node);
        node.toBack();
    }

    private double getLayerX(Collection<SystemElement> nodes) {
        double x = Double.MAX_VALUE;
        for (SystemElement element : nodes) {
            double elementX = ((Region) element.getContainer()).getLayoutX();
            if (elementX < x) {
                x = elementX;
            }
        }
        return LAYER_START_X + x;
    }

}
