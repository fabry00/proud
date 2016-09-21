package com.console.view.systemlayout;

import com.console.domain.AppState;
import com.console.util.view.NodeGestures;
import com.console.util.view.PannableCanvas;
import com.console.view.systemlayout.element.NodeElement;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author fabry
 */
class SystemLayoutFactory {

    void draw(PannableCanvas canvas, NodeGestures gestures, AppState currentState) {
        AtomicReference<Double> x = new AtomicReference<>(10.0);
        AtomicReference<Double> y = new AtomicReference<>(10.0);
        AtomicReference<Double> xGap = new AtomicReference<>(150.0);

        currentState.getNodes().forEach((node) -> {
            NodeElement element = new NodeElement(node);
            canvas.getChildren().add(element.draw(x.get(), y.get(), gestures));
            x.set(x.get() + xGap.get());

        });
    }

}
