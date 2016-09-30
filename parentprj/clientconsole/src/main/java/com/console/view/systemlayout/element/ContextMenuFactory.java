package com.console.view.systemlayout.element;

import com.console.domain.IAppElement;
import com.console.util.view.ILayoutManager;
import com.console.view.systemlayout.ISystemLayoutManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.WindowEvent;

import java.util.Arrays;

/**
 * @author fabry
 */
public class ContextMenuFactory {

    private final ISystemLayoutManager layoutManager;
    private final ZoomIn zoomIn;
    private final KpiGraph kpiGraph;
    private ContextMenu contextMenu;

    public ContextMenuFactory(ISystemLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        this.zoomIn = new ZoomIn(layoutManager);
        this.kpiGraph = new KpiGraph(layoutManager);
    }

    public ContextMenu create() {

        contextMenu = new ContextMenu(zoomIn.getMenu(), kpiGraph.getMenu());
        contextMenu.setOnShowing(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                disableMenus();
            }
        });
        /*contextMenu.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {

                System.out.println("shown")
                ;
            }
        });*/

        return contextMenu;
    }

    private void disableMenus() {
        zoomIn.disable();
        kpiGraph.disable();
    }

    private static class ZoomIn {
        private final ISystemLayoutManager layoutManager;
        private final MenuItem menu;

        public ZoomIn(ISystemLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
            menu = new MenuItem("ZoomIn");
            menu.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    exec();
                }
            });
        }

        public MenuItem getMenu() {
            return menu;
        }

        public void disable() {
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
            ISystemElement virtualLayer = layoutManager.getVirtualLayer(layoutManager.getSelectedNodes());
            layoutManager.changeLayout(Arrays.asList(virtualLayer));
        }
    }

    private static class KpiGraph {
        private final ISystemLayoutManager layoutManager;
        private final MenuItem menu;


        public KpiGraph(ISystemLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
            menu = new MenuItem("KPI Graph");
            menu.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    exec();
                }
            });
        }

        public MenuItem getMenu() {
            return menu;
        }

        public void disable() {
            if (layoutManager.getSelectedNodes().isEmpty()) {
                menu.setDisable(true);
                return;
            }

            menu.setDisable(false);
        }

        private void exec() {

        }
    }
}
