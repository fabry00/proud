package com.console.view.systemlayout.element.contextmenu;

import com.console.view.systemlayout.ISystemLayoutManager;
import javafx.scene.control.MenuItem;

/**
 * ContextMenu action
 * Created by Fabrizio Faustinoni on 01/10/2016.
 */
class KpiGraph {
    private final ISystemLayoutManager layoutManager;
    private final MenuItem menu;


    KpiGraph(ISystemLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        menu = new MenuItem("Show Anomalous KPI Graph");
        menu.setOnAction(e -> exec());
    }

    MenuItem getMenu() {
        return menu;
    }

    void disable() {
        if (layoutManager.getSelectedNodes().isEmpty()) {
            menu.setDisable(true);
            return;
        }

        menu.setDisable(false);
    }

    private void exec() {

    }
}