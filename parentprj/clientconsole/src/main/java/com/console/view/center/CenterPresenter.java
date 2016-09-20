package com.console.view.center;

import com.console.domain.Action;
import com.console.domain.ActionType;
import com.console.domain.AppState;
import com.console.domain.IAppStateListener;
import com.console.domain.State;
import com.console.service.appservice.ApplicationService;
import com.console.util.NodeUtil;
import com.console.view.graphdata.GraphdataView;
import com.console.view.graphdata.toolbar.ToolbarView;
import com.console.view.systemlayout.SystemlayoutPresenter;
import com.console.view.systemlayout.SystemlayoutView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javax.inject.Inject;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class CenterPresenter implements Initializable {

    private final Logger logger = Logger.getLogger(CenterPresenter.class);

    @Inject
    private ApplicationService appService;

    @FXML
    Button startButton;

    @FXML
    Button stopButton;

    @FXML
    AnchorPane centerPane;

    private NodeUtil util = new NodeUtil();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("initialize");
  
        //AnchorPane graphPane = (AnchorPane) new GraphdataView().getView();
        //util.ancorToPane(graphPane, 0.0);
        //centerPane.getChildren().add(graphPane);

        initToolbar();
        initSystenLayout();

    }

    private void initToolbar() {
        AnchorPane pane = (AnchorPane) new ToolbarView().getView();
        util.ancorToPaneLeft(pane, 0.0);
        util.ancorToPaneTop(pane, 0.0);
        util.ancorToPaneRight(pane, 0.0);
        centerPane.getChildren().add(pane);
    }

    private void initSystenLayout() {
        FlowPane pane = (FlowPane) new SystemlayoutView().getView();
        util.ancorToPaneLeft(pane, 0.0);
        util.ancorToPaneTop(pane, 70.0);
        util.ancorToPaneRight(pane, 0.0);
        centerPane.getChildren().add(pane);
    }

}
