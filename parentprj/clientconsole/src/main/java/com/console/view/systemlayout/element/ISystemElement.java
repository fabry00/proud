package com.console.view.systemlayout.element;

import java.util.Collection;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import com.console.domain.IAppElement;

/**
 *
 * @author fabry
 */
public interface ISystemElement {

    public Node draw(double x, double y);

    public void setParent(ISystemElement parent);

    public ISystemElement getParent();

    public void createConnections(List<ISystemElement> relatedElements);

    public Collection<Line> getConnections();

    public Node getContainer();

    public IAppElement.Type getType();
    
    public IAppElement getAppElement();

    public String getName();

    public void selected();

    public void unSelected();
}
