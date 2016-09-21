package com.console.view.systemlayout.element;

import com.console.util.view.NodeGestures;
import java.util.Collection;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.shape.Line;

/**
 *
 * @author fabry
 */
public interface SystemElement {

    public Node draw(double x, double y, final NodeGestures nodeGestures);

    public void createConnections(List<SystemElement> relatedElements);
    
    public Collection<Line> getConnections();
    
    public Node geContainer();
}
