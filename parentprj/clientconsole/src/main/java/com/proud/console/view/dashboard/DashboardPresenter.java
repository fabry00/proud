package com.proud.console.view.dashboard;

import com.proud.console.domain.ActionType;
import com.proud.console.domain.AppAction;
import com.proud.console.service.appservice.ApplicationService;
import com.proud.console.util.NodeUtil;
import com.proud.console.view.center.CenterView;
import com.proud.console.view.status.StatusView;
import com.proud.console.view.unused.logo.LogoView;
import com.proud.console.view.unused.tree.TreeView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;
import org.controlsfx.control.NotificationPane;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author adam-bien.com
 */
public class DashboardPresenter implements Initializable {


    private final Logger logger = Logger.getLogger(DashboardPresenter.class);

    @FXML
    private Pane bottomPane;

    @FXML
    private AnchorPane centerPane;

    @FXML
    private AnchorPane leftPane;

    @Inject
    private ApplicationService appService;

    private NodeUtil util = new NodeUtil();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger.debug("Initialize");

        setCenterPane();
        //setLeftPane();
        setBottomPane();

        initApp();
    }

//    public void createLights() {
//          for (int i = 0; i < 255; i++) {
//            final int red = i;
//            LightView view = new LightView((f) -> red);
//             view.getViewAsync(lightsBox.getChildren()::add);
//        }
//         StatusView view = new StatusView();
//        lightsBox.getChildren().add(view.getView());
//    }


    /**
     * Closes the application.
     */
    @FXML
    public void handleExit() {
        appService.dispatch(new AppAction<>(ActionType.CLOSE, null));
    }

    @FXML
    public void handleChangeTheme() {
        appService.dispatch(new AppAction<>(ActionType.CHANGE_THEME, null));
    }

    @FXML
    public void handleFullScreen() {
        appService.dispatch(new AppAction<>(ActionType.FULL_SCREEN, true));
    }

    @FXML
    public void handleAbout() {
        new About().show(appService);
    }

    public ApplicationService getAppService() {
        return appService;
    }

    private void initApp() {
        logger.debug("initApp");
        appService.dispatch(new AppAction<>(ActionType.START, null));
    }

    private void setBottomPane() {
        StatusView view = new StatusView();
        AnchorPane statusView = (AnchorPane) view.getView();
        util.anchorToPane(statusView, 0.0);
        bottomPane.getChildren().add(statusView);
    }

    private void setCenterPane() {

        Node center = new CenterView().getView();
        util.anchorToPane(center, 0.0);
        centerPane.getChildren().add(center);
    }

    private void setLeftPane() {
        AnchorPane logo = (AnchorPane) new LogoView().getView();
        util.anchorToPaneLeft(logo, 0.0);
        util.anchorToPaneRight(logo, 0.0);
        util.anchorToPaneTop(logo, 0.0);
        logo.setPadding(new Insets(0));
        leftPane.getChildren().add(logo);

        AnchorPane tree = (AnchorPane) new TreeView().getView();
        util.anchorToPaneTop(tree, logo.getPrefHeight());
        util.anchorToPaneLeft(tree, 0.0);
        util.anchorToPaneRight(tree, 0.0);
        util.anchorToPaneBottom(tree, 0.0);
        tree.setPadding(new Insets(0));
        leftPane.getChildren().add(tree);
    }

}
