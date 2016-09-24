package com.console.view.systemlayout;

import com.console.util.view.ILayoutManager;
import com.console.view.systemlayout.element.ISystemElement;
import java.util.List;

/**
 *
 * @author fabry
 */
public interface ISystemLayoutManager extends ILayoutManager {

    public void changeLayout(List<ISystemElement> elementToShow);

    public void addSelectedNode(ISystemElement node);

    public void removeSelectedNode(ISystemElement node);

}
