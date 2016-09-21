package com.console.view.systemlayout.element;

import com.console.util.view.NodeGestures;
import javafx.scene.Node;

/**
 *
 * @author fabry
 */
public interface SystemElement {

    public Node draw(double x, double y, final NodeGestures nodeGestures);
}
