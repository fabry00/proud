package com.console.view.systemlayout.element.contextmenu;

import com.console.domain.IAppElement;
import com.console.view.systemlayout.ISystemLayoutManager;
import com.console.view.systemlayout.element.ISystemElement;
import javafx.collections.FXCollections;
import javafx.scene.control.MenuItem;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Zoom in context action
 * Created by Fabrizio Faustinoni on 01/10/2016.
 */
class ZoomIn {
    private final Logger logger = Logger.getLogger(ZoomIn.class);
    private final ISystemLayoutManager layoutManager;
    private final MenuItem menu;

    ZoomIn(ISystemLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        menu = new MenuItem("Zoom In Selected nodes");
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

        if (layoutManager.getSelectedNodes().size() == 1 &&
                layoutManager.getSelectedNodes().get(0).getType().equals(IAppElement.Type.Node)) {
            menu.setDisable(true);
            return;
        }

        menu.setDisable(false);
    }

    private void exec() {
        logger.debug("Zoomin nodes selected: " + layoutManager.getSelectedNodes().size());
        ISystemElement virtualLayer = null;
        List<ISystemElement> layers = new ArrayList<>();
        for (ISystemElement node : layoutManager.getSelectedNodes()) {
            ISystemElement layer = null;
            if (node.getParent() != null) {
                // Search layer to add
                for (ISystemElement layerAdded : layers) {
                    if (layerAdded.getName().equals(node.getParent().getName())) {
                        layer = layerAdded;
                    }
                }
                if (layer == null) {
                    // Layer note already added, create it
                    layer = node.getParent().clone();
                    layer.getAppElement().getNodes().clear();
                    layer.getNodes().clear();
                    layers.add(layer);

                }
                layer.getAppElement().getNodes().add(node.getAppElement());
                layer.getNodes().add(node);

            } else {
                // Node hasn't a layer
                if (virtualLayer == null) {
                    virtualLayer = layoutManager.getVirtualLayer(FXCollections.observableArrayList());
                }
                virtualLayer.getNodes().add(node);
            }
        }
        if (virtualLayer != null) {
            layers.add(virtualLayer);
        }
        layoutManager.changeLayout(layers);
    }
}