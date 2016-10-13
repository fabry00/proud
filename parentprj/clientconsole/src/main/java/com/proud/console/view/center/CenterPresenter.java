package com.proud.console.view.center;

import com.proud.console.util.NodeUtil;
import com.proud.console.view.systemlayout.SystemlayoutView;
import com.proud.console.view.toolbar.ToolbarView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Fabrizio Faustinoni
 */
public class CenterPresenter implements Initializable {

    private static final String SYSTEM_TAB = "System";
    private final Logger logger = Logger.getLogger(CenterPresenter.class);
    private final NodeUtil util = new NodeUtil();

    @FXML
    AnchorPane topPane;
    @FXML
    TabPane tabsPane;
    @FXML
    AnchorPane systemTab;

    private ITabsManager tabsManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("initialize");
        tabsManager = new TabsManager(tabsPane);
        initToolbar();
        initSystenLayout();
    }

    private void initToolbar() {

        ToolbarView toolbar = new ToolbarView();
        Parent pane = toolbar.getView();
        util.anchorToPaneLeft(pane, 0.0);
        util.anchorToPaneTop(pane, 0.0);
        util.anchorToPaneRight(pane, 0.0);
        topPane.getChildren().add(pane);

        toolbar.getRealPresenter().subscribe(new ToolbarListener(tabsManager));
    }

    private void initSystenLayout() {
        SystemlayoutView view = new SystemlayoutView();
        view.getRealPresenter().setTabManager(tabsManager);
        Parent pane = view.getView();

        util.anchorToPaneLeft(pane, 0.0);
        util.anchorToPaneTop(pane, 0.0);
        util.anchorToPaneRight(pane, 0.0);
        util.anchorToPaneBottom(pane, 0.0);

        systemTab.getChildren().add(pane);

        tabsPane.getTabs().get(0).setId(SYSTEM_TAB);
        tabsPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);

    }
}
