package com.console.view.systemlayout.element;

import com.console.view.systemlayout.ISystemLayoutManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.WindowEvent;

/**
 *
 * @author fabry
 */
public class ContextMenuFactory {
    
    public ContextMenu create(ISystemLayoutManager layoutManager) {
        MenuItem zoomIn = new MenuItem("Zoom in");
        zoomIn.setOnAction((ActionEvent e) -> {
            layoutManager.changeLayout(layoutManager.getSelectedNodes());
        });
        
        MenuItem item2 = new MenuItem("Preferences");
        item2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Preferences");
            }
        });
        
        final ContextMenu contextMenu = new ContextMenu(zoomIn, item2);
        contextMenu.setOnShowing(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                System.out.println("showing");
            }
        });
        contextMenu.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                System.out.println("shown");
            }
        });
        
        return contextMenu;
    }
    
}
