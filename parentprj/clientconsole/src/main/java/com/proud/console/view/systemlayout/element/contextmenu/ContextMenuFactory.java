package com.proud.console.view.systemlayout.element.contextmenu;

import com.proud.console.view.systemlayout.ISystemLayoutManager;
import javafx.scene.control.ContextMenu;

/**
 * @author Fabrizio Faustinoni
 */
public class ContextMenuFactory {

    private final ZoomIn zoomIn;
    private final KpiGraph kpiGraph;
    private ContextMenu contextMenu;

    public ContextMenuFactory(ISystemLayoutManager layoutManager) {
        this.zoomIn = new ZoomIn(layoutManager);
        this.kpiGraph = new KpiGraph(layoutManager);
    }

    public ContextMenu create() {

        contextMenu = new ContextMenu(zoomIn.getMenu(), kpiGraph.getMenu());
        contextMenu.setOnShowing(e -> disableMenus());
        return contextMenu;
    }

    private void disableMenus() {
        zoomIn.disable();
        kpiGraph.disable();
    }

}
