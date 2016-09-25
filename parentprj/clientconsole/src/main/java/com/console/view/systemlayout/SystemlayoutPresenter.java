package com.console.view.systemlayout;

import com.console.domain.AppState;
import com.console.domain.IAppElement;
import com.console.domain.IAppStateListener;
import com.console.domain.State;
import com.console.service.appservice.ApplicationService;
import com.console.util.view.PannableCanvas;
import com.console.util.view.SceneGestures;
import com.console.view.systemlayout.element.ISystemElement;
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
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;
import org.controlsfx.control.BreadCrumbBar;

/**
 * Created by exfaff on 20/09/2016.
 */
public class SystemlayoutPresenter implements Initializable, IAppStateListener, ISystemLayoutManager {

    private final Logger logger = Logger.getLogger(SystemlayoutPresenter.class);

    private final ObservableList<ISystemElement> selectedNodes = FXCollections.observableArrayList();

    @FXML
    AnchorPane ancorPaneSystem;

    @FXML
    ScrollPane systemPane;

    @FXML
    BreadCrumbBar crumbBar;

    @FXML
    ToggleButton btnLockUnlock;

    @Inject
    private ApplicationService appService;

    private boolean ctrlPressed = false;
    private PannableCanvas canvas;
    private BreadCrumbManager crumbManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initToolbar();

        initSystemLayout();
        initEvents();
        appService.subscribeToState(this, State.STARTED);
    }

    @Override
    public void AppStateChanged(AppState oldState, AppState currentState) {
        // AppState == State.STARTED
        SystemLayoutFactory factory = new SystemLayoutFactory();
        List<ISystemElement> drawedElement = factory.draw(this, canvas, currentState.getLayers());
        crumbManager.setRootElements(drawedElement);
    }

    @Override
    public void changeLayout(List<ISystemElement> elementsToShow) {
        logger.debug("Change System layout");
        crumbManager.selectLayout(elementsToShow);

        ObservableList<IAppElement> elemesToDraw = getElemsToShow(elementsToShow);

        SystemLayoutFactory factory = new SystemLayoutFactory();
        factory.draw(this, canvas, elemesToDraw);

    }

    @Override
    public boolean isLayoutLocked() {
        return !btnLockUnlock.selectedProperty().get();
    }

    @Override
    public void addSelectedNode(ISystemElement node) {
        if (ctrlPressed) {
            if (!selectedNodes.contains(node)) {
                selectedNodes.add(node);
                logger.debug("Added node to selected: " + node.getName() + " " + selectedNodes.size());
                node.selected();
            }
        }
    }

    @Override
    public void removeSelectedNode(ISystemElement node) {
        selectedNodes.remove(node);
        logger.debug("Removed node to selected: " + node.getName() + " " + selectedNodes.size());
        node.unSelected();
    }

    @FXML
    public void onSave() {
        Alert dlg = new Alert(AlertType.INFORMATION);

        dlg.setTitle("Save layout");
        String optionalMasthead = "Not implemented yet";
        dlg.getDialogPane().setContentText("This feautures has not been implemented yet!");
        dlg.getDialogPane().setHeaderText(optionalMasthead);
        dlg.show();
    }

    private void initCrumbar() {
        crumbManager = new BreadCrumbManager(crumbBar, this, appService);
        crumbManager.initCrumbar();
    }

    private void initSystemLayout() {
        Group group = new Group();
        canvas = new PannableCanvas(systemPane.getWidth(), systemPane.getHeight());
        // we don't want the canvas on the top/left in this example => just
        // translate it a bit
        canvas.setTranslateX(0);
        canvas.setTranslateY(0);

        group.getChildren().add(canvas);
        systemPane.setContent(group);
        systemPane.setPannable(true);// enable scroll with mouse
        SceneGestures sceneGestures = new SceneGestures(canvas);
        systemPane.addEventFilter(MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        systemPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        systemPane.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());

    }

    private void initToolbar() {
        initCrumbar();

        btnLockUnlock.setTooltip(new Tooltip("Lock/Unlock elements"));
    }

    private ObservableList<IAppElement> getElemsToShow(List<ISystemElement> elementsSelected) {
        ObservableList<IAppElement> elemesToDraw = FXCollections.observableArrayList();

        for (IAppElement elem : appService.getCurrentState().getLayers()) {
            for (ISystemElement elementSelected : elementsSelected) {
                if (elementSelected.getAppElement().equals(elem)) {
                    elemesToDraw.add(elem);
                }

            }

        }

        return elemesToDraw;
    }

    private void clearNodeSelected() {

        selectedNodes.forEach((node) -> {
            node.unSelected();
        });
        selectedNodes.clear();
    }

    private void initEvents() {

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!event.getButton().equals(MouseButton.PRIMARY)) {
                    return;
                }
                if (!ctrlPressed) {
                    clearNodeSelected();
                }

            }
        });

        Platform.runLater(() -> {
            // We need run later because the element is not been initialized yet
            Scene scene = ((Node) ancorPaneSystem).getScene();
            scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode().equals(KeyCode.CONTROL)) {
                        ctrlPressed = true;
                    }
                }
            });

            scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode().equals(KeyCode.CONTROL)) {
                        ctrlPressed = false;
                    }
                }
            });

        });

    }

}
