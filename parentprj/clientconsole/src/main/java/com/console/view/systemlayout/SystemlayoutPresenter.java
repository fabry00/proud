package com.console.view.systemlayout;

import com.console.domain.AppState;
import com.console.domain.IAppStateListener;
import com.console.domain.State;
import com.console.service.appservice.ApplicationService;
import com.console.util.view.NodeGestures;
import com.console.util.view.PannableCanvas;
import com.console.util.view.SceneGestures;
import com.console.view.systemlayout.element.ISystemElement;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.controlsfx.control.BreadCrumbBar;
import org.controlsfx.control.BreadCrumbBar.BreadCrumbActionEvent;

/**
 * Created by exfaff on 20/09/2016.
 */
public class SystemlayoutPresenter implements Initializable, IAppStateListener, ISystemLayoutManager {

    private final Logger logger = Logger.getLogger(SystemlayoutPresenter.class);

    @FXML
    ScrollPane systemPane;

    @FXML
    BreadCrumbBar crumbBar;

    @Inject
    private ApplicationService appService;

    private PannableCanvas canvas;
    private NodeGestures nodeGestures;
    private BreadCrumbManager crumbManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        crumbManager = new BreadCrumbManager(crumbBar, this);
        crumbManager.initCrumbar();

        Group group = new Group();
        canvas = new PannableCanvas(systemPane.getWidth(), systemPane.getHeight());
        // we don't want the canvas on the top/left in this example => just
        // translate it a bit
        canvas.setTranslateX(0);
        canvas.setTranslateY(0);

        nodeGestures = new NodeGestures(canvas);

        group.getChildren().add(canvas);
        systemPane.setContent(group);
        systemPane.setPannable(true);// enable scroll with mouse
        SceneGestures sceneGestures = new SceneGestures(canvas);
        systemPane.addEventFilter(MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        systemPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        systemPane.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());

        appService.subscribeToState(this, State.STARTED);

    }

    @Override
    public void AppStateChanged(AppState oldState, AppState currentState) {
        // AppState == State.STARTED
        SystemLayoutFactory factory = new SystemLayoutFactory();
        factory.draw(this, canvas, nodeGestures, currentState.getLayers());
    }

    @Override
    public void changeLayout(List<ISystemElement> elementsToShow) {
        logger.debug("Change System layout");
        crumbManager.selectLayout(elementsToShow);

    }

}
