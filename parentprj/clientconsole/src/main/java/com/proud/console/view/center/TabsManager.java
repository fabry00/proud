package com.proud.console.view.center;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.apache.log4j.Logger;

/**
 * Manages the tabs
 * Created by fabry on 02/10/2016.
 */
class TabsManager implements ITabManager {

    private final Logger logger = Logger.getLogger(TabsManager.class);
    private final TabPane tabsPane;

    TabsManager(TabPane tabsPane) {
        this.tabsPane = tabsPane;

    }

    @Override
    public void addTab(String idTab, Node content) {

        logger.debug("Add Tab with id: " + idTab);
        Tab tab = null;
        for (Tab openedTab : tabsPane.getTabs()) {
            //logger.debug(openedTab.getText());
            if (openedTab.getId().equals(idTab)) {
                tab = openedTab;
                break;
            }
        }

        if (tab == null) {
            tab = new Tab(idTab, content);
            tab.closableProperty().set(true);
            tab.setId(idTab);

        }

        tabsPane.getTabs().add(tab);
        tabsPane.getSelectionModel().select(tab);
    }
}
