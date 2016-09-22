package com.console.view.systemlayout.element;

import com.console.domain.AppElement;
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

    /*public enum Type {Node,Layer};*/
    
    public Node draw(double x, double y, final NodeGestures nodeGestures);

    public void createConnections(List<SystemElement> relatedElements);
    
    public Collection<Line> getConnections();
    
    public Node getContainer();
    
    public AppElement.Type getType();
}
