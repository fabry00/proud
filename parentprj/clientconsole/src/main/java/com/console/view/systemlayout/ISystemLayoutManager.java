package com.console.view.systemlayout;

import com.console.util.view.ILayoutManager;
import com.console.view.systemlayout.element.ISystemElement;
import javafx.collections.ObservableList;

import java.util.List;

/**
 *
 * @author Fabrizio Faustinoni
 */
public interface ISystemLayoutManager extends ILayoutManager {

    void changeLayout(List<ISystemElement> elementToShow);

    void addSelectedNode(ISystemElement node);

    ObservableList<ISystemElement> getSelectedNodes();

    void removeSelectedNode(ISystemElement node);

    void showKpiLayout(String title, List<ISystemElement> elementToShow);

    ISystemElement getVirtualLayer(ObservableList<ISystemElement> layerNodes);
}
