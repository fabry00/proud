package com.console.view.center;

import com.console.service.appservice.ApplicationService;
import com.console.util.NodeUtil;
import com.console.view.graphdata.toolbar.ToolbarView;
import com.console.view.systemlayout.SystemlayoutView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
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
        Parent pane = new SystemlayoutView().getView();
        util.ancorToPaneLeft(pane, 0.0);
        util.ancorToPaneTop(pane, 40.0);
        util.ancorToPaneRight(pane, 0.0);
        util.ancorToPaneBottom(pane, 0.0);
        centerPane.getChildren().add(pane);
    }

}
