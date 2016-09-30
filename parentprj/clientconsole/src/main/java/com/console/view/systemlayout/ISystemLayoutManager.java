package com.console.view.systemlayout;

import com.console.util.view.ILayoutManager;
import com.console.view.systemlayout.element.ISystemElement;
import javafx.collections.ObservableList;

import java.util.List;

/**
 *
 * @author fabry
 */
public interface ISystemLayoutManager extends ILayoutManager {

    public void changeLayout(List<ISystemElement> elementToShow);

    public void addSelectedNode(ISystemElement node);

    public ObservableList<ISystemElement> getSelectedNodes();

    public void removeSelectedNode(ISystemElement node);

    public void showKpiLayout(String title, List<ISystemElement> elementToShow);

    public ISystemElement getVirtualLayer(ObservableList<ISystemElement> layerNodes);
}
