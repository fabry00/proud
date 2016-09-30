package com.console.view.systemlayout.element;

import com.console.domain.IAppElement;
import com.console.util.view.PannableCanvas;
import com.console.view.systemlayout.ISystemLayoutManager;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author fabry
 */
public class SystemLayoutFactory {

    private static final double NODE_X_GAP = 150.0;
    private static final double LAYER_Y_GAP = 250.0;
    private static final double LAYER_START_X = 20.0;
    private static final double LAYER_START_Y = 32.0;
    private static final double NODE_START_X = 30.0;
    private static final double NODE_START_Y = 37.0;

    private final Logger logger = Logger.getLogger(SystemLayoutFactory.class);

    public List<ISystemElement> draw(ISystemLayoutManager layoutManager, PannableCanvas canvas,
                                     ObservableList<IAppElement> layers) {

        // Clear all elementd in canvas
        canvas.getChildren().clear();

        Map<IAppElement, ISystemElement> elements = new HashMap<>();
        List<ISystemElement> layersDrawn = new ArrayList<>();
        double y = NODE_START_Y;

        // Draw layers in reverse order
       // for (int i = layers.size() - 1; i >= 0; i--) {
        for (int i = 0; i < layers.size(); i++) {

            IAppElement layer = layers.get(i);
            if (layer.getType().equals(IAppElement.Type.Layer)) {
                logger.trace("Drawing layer: " + layer.getName());

                Map<IAppElement, ISystemElement> leyerElements
                        = createElements(layoutManager, canvas, layer.getNodes(), y);

                ISystemElement layerDrawn = createLayer(layoutManager, canvas, layer, leyerElements, y);

                layersDrawn.add(layerDrawn);
                elements.putAll(leyerElements);
                y += LAYER_Y_GAP;
            } else {
                logger.error("Drawing node outside a layer is not supported yet");
            }
        }

        createConnections(elements, canvas);

        return layersDrawn;
    }

    private Map<IAppElement, ISystemElement> createElements(ISystemLayoutManager layoutManager,
                                                            PannableCanvas canvas, ObservableList<IAppElement> nodes, double y) {

        double x = NODE_START_X;

        Map<IAppElement, ISystemElement> elements = new HashMap<>();
        for (IAppElement node : nodes) {
            if (node.getType().equals(IAppElement.Type.Node)) {
                logger.trace("Drawing node: " + node.getName());
                ISystemElement element = new NodeElement(node, layoutManager, canvas);
                canvas.getChildren().add(element.draw(x, y));
                x += NODE_X_GAP;
                elements.put(node, element);
            } else {
                logger.error("Drawing nested layers is not supported yet");
            }
        }
        return elements;
    }

    private void createConnections(Map<IAppElement, ISystemElement> elements, PannableCanvas canvas) {
        elements.forEach((k, v) -> {
            List<ISystemElement> relatedElements = getRelatedElements(elements, k);
            logger.trace("Connection: " + k.getName() + " with:" + relatedElements.toString());
            v.createConnections(relatedElements);
            canvas.getChildren().addAll(v.getConnections());
        });
    }

    private List<ISystemElement> getRelatedElements(Map<IAppElement, ISystemElement> elements, IAppElement node) {
        List<ISystemElement> relatedElements = new ArrayList<>();
        Set<IAppElement> connections = node.getConnections();
        for (IAppElement r : connections) {
            relatedElements.add(elements.get(r));
        }
        return relatedElements;
    }

    private ISystemElement createLayer(ISystemLayoutManager layoutManager, PannableCanvas canvas,
                                       IAppElement layer, Map<IAppElement, ISystemElement> leyerElemens, double y) {

        double x = getLayerX(leyerElemens.values());
        // TODO implements netsted layers
        ISystemElement layerElement;
        if (!layer.isVirtual()) {
            layerElement = new LayerElement(layer, leyerElemens.values(),
                    NODE_X_GAP, LAYER_START_Y, layoutManager, canvas);
        } else {
            layerElement = new VirtualLayerElement(layer, leyerElemens.values(),
                    NODE_X_GAP, LAYER_START_Y, layoutManager, canvas);
        }

        Node node = layerElement.draw(x, y - LAYER_START_Y);
        canvas.getChildren().add(node);
        node.toBack();

        // Set parent to each layer's nodes
        leyerElemens.forEach((k, v) -> {
            v.setParent(layerElement);
        });
        return layerElement;
    }

    private double getLayerX(Collection<ISystemElement> nodes) {
        double x = Double.MAX_VALUE;
        for (ISystemElement element : nodes) {
            double elementX = ((Region) element.getContainer()).getLayoutX();
            if (elementX < x) {
                x = elementX;
            }
        }
        return LAYER_START_X + x;
    }

}
