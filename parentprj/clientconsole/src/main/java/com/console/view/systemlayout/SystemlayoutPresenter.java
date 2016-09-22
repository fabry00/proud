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
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.controlsfx.control.BreadCrumbBar;
import org.controlsfx.control.BreadCrumbBar.BreadCrumbActionEvent;

/**
 * Created by exfaff on 20/09/2016.
 */
public class SystemlayoutPresenter implements Initializable, IAppStateListener {
    
    private final Logger logger = Logger.getLogger(SystemlayoutPresenter.class);
    
    @FXML
    ScrollPane systemPane;
    
    @FXML
    BreadCrumbBar crumbBar;
    
    @Inject
    private ApplicationService appService;
    
    @Inject
    private SystemLayoutManager layoutManager;
    
    private PannableCanvas canvas;
    private NodeGestures nodeGestures;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCrumbar();
        
        Group group = new Group();
        canvas = new PannableCanvas(systemPane.getWidth(), systemPane.getHeight());
        // we don't want the canvas on the top/left in this example => just
        // translate it a bit
        canvas.setTranslateX(0);
        canvas.setTranslateY(0);

        // create sample nodes which can be dragged
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
        factory.draw(canvas, nodeGestures, currentState.getLayers());
    }
    
    
    private void initCrumbar() {
        //http://stackoverflow.com/questions/30931401/how-to-use-breadcrumbbar-in-controlsfx-javafx-8
        TreeItem<String> root = new TreeItem<>("System");
        
        TreeItem<String> item1 = new TreeItem<>("Item 1");
        TreeItem<String> item11 = new TreeItem<>("Item 1.1");
        TreeItem<String> item12 = new TreeItem<String>("Item 1.2");
        
        item1.getChildren().addAll(item11, item12);
        
        TreeItem<String> item2 = new TreeItem<>("Item 2");
        
        root.getChildren().addAll(item1, item2);
        
        crumbBar.selectedCrumbProperty().set(root);
        
        crumbBar.setOnCrumbAction(new EventHandler<BreadCrumbBar.BreadCrumbActionEvent<String>>() {
            @Override
            public void handle(BreadCrumbActionEvent<String> bae) {
                System.out.println("BreadCrumbActionEvent");
            }
        });
        
    }
    
}
