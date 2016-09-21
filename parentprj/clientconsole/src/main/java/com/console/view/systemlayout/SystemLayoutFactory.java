package com.console.view.systemlayout;

import com.console.domain.AppNode;
import com.console.domain.AppState;
import com.console.util.view.NodeGestures;
import com.console.util.view.PannableCanvas;
import com.console.view.systemlayout.element.NodeElement;
import com.console.view.systemlayout.element.SystemElement;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import javafx.scene.shape.Line;

/**
 *
 * @author fabry
 */
class SystemLayoutFactory {

    private static final double START_X = 10.0;
    private static final double START_Y = 10.0;
    private static final double X_GAP = 150.0;

    void draw(PannableCanvas canvas, NodeGestures gestures, AppState currentState) {

        Map<AppNode, SystemElement> elements = createElements(canvas, gestures, currentState);
        createConnections(elements,canvas);
    }

    private Map<AppNode, SystemElement> createElements(PannableCanvas canvas, NodeGestures gestures, AppState currentState) {
        double x = START_X;
        double y = START_Y;
        Map<AppNode, SystemElement> elements = new HashMap<>();
        for (AppNode node : currentState.getNodes()) {
            NodeElement element = new NodeElement(node);
            canvas.getChildren().add(element.draw(x, y, gestures));
            x += X_GAP;
            elements.put(node, element);
        }
        return elements;
    }

    private void createConnections(Map<AppNode, SystemElement> elements, PannableCanvas canvas) {
        elements.forEach((k, v) -> {
            List<SystemElement> relatedElements = getRelatedElements(elements, k);
            v.createConnections(relatedElements);
            canvas.getChildren().addAll(v.getConnections());
        });
    }

    private List<SystemElement> getRelatedElements(Map<AppNode, SystemElement> elements, AppNode node) {
        List<SystemElement> relatedElements = new ArrayList<>();
        Set<AppNode> connections = node.getConnections();
        for(AppNode r : connections){
            relatedElements.add(elements.get(r));
        }
        return relatedElements;
    }

}
