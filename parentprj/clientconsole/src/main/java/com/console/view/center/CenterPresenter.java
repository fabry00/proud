package com.console.view.center;

import com.console.util.NodeUtil;
import com.console.view.graphdata.toolbar.ToolbarView;
import com.console.view.systemlayout.SystemlayoutView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

//public class CenterPresenter implements Initializable {
//    private static int index = 0;
//    @FXML
//    TabPane tabs;
//
//    @FXML
//    public void add(){
//        Tab tab=new Tab("new tab "+(index++),new Label("Please help"));
//
//        tabs.getTabs().add(tab);
//    }
//
//    public void initialize(URL location, ResourceBundle resources) {
//
//    }
//}


/**
 *
 * @author Fabrizio Faustinoni
 */
public class CenterPresenter implements Initializable, ITabManager {

    private final Logger logger = Logger.getLogger(CenterPresenter.class);
    private final NodeUtil util = new NodeUtil();
    @FXML
    AnchorPane topPane;
    @FXML
    TabPane tabsPane;
    @FXML
    AnchorPane systemTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("initialize");

        initToolbar();
        initSystenLayout();
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

    private void initToolbar() {
        Parent pane = new ToolbarView().getView();
        util.ancorToPaneLeft(pane, 0.0);
        util.ancorToPaneTop(pane, 0.0);
        util.ancorToPaneRight(pane, 0.0);
        topPane.getChildren().add(pane);
    }

    private void initSystenLayout() {
        SystemlayoutView view = new SystemlayoutView();
        view.getRealPresenter().setTabManager(this);
        Parent pane = view.getView();

        util.ancorToPaneLeft(pane, 0.0);
        util.ancorToPaneTop(pane, 0.0);
        util.ancorToPaneRight(pane, 0.0);
        util.ancorToPaneBottom(pane, 0.0);

        systemTab.getChildren().add(pane);
        tabsPane.getTabs().get(0).setId("System");

        tabsPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
    }
}
