package com.console.view.systemlayout.element;

import com.console.domain.IAppElement;
import javafx.scene.Node;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author Fabrizio Faustinoni
 */
public interface ISystemElement {

    Node draw(double x, double y);

    ISystemElement getParent();

    void setParent(ISystemElement parent);

    void createConnections(List<ISystemElement> relatedElements);

    Collection<Connection> getConnections();

    Node getContainer();

    IAppElement.Type getType();

    IAppElement getAppElement();

    String getName();

    void selected();

    void unSelected();

    boolean isVirtual();

    ISystemElement clone();

    Collection<ISystemElement> getNodes();
}
