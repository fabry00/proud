package com.proud.console.view.systemlayout;

import com.proud.console.domain.*;
import com.proud.console.service.appservice.ApplicationService;
import com.proud.console.util.view.PannableCanvas;
import com.proud.console.util.view.SceneGestures;
import com.proud.console.view.center.ITabManager;
import com.proud.console.view.graphdata.GraphdataView;
import com.proud.console.view.systemlayout.element.BreadCrumbManager;
import com.proud.console.view.systemlayout.element.ISystemElement;
import com.proud.console.view.systemlayout.element.SystemLayoutFactory;
import com.proud.console.view.systemlayout.element.VirtualLayerElement;
import com.proud.console.view.systemlayout.element.contextmenu.ContextMenuFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;
import org.controlsfx.control.BreadCrumbBar;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * System layout presenter
 * Created by Fabrizio Faustinoni on 20/09/2016.
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
    private ITabManager tabManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initToolbar();
        initSystemLayout();
        initContextMenu();
        initEvents();
        appService.getCurrentState().getEventManager().subscribeToEvent(this, AppEvent.STATE_CHANGED);
    }

    @Override
    public void AppEvent(AppEvent event, Object param) {
        // Subscribed to AppEvent.STATE_CHANGED
        if (appService.getCurrentState().getState().equals(State.STARTED)) {
            SystemLayoutFactory factory = new SystemLayoutFactory();
            List<ISystemElement> drawedElement = factory.draw(this, canvas, appService.getCurrentState().getLayers());
            crumbManager.setRootElements(drawedElement);
        }
    }

    @Override
    public void changeLayout(List<ISystemElement> elementsToShow) {
        logger.debug("Change System layout");

        crumbManager.selectLayout(elementsToShow);
        ObservableList<IAppElement> elemesToDraw = getElemsToShow(elementsToShow);
        SystemLayoutFactory factory = new SystemLayoutFactory();
        factory.draw(this, canvas, elemesToDraw);

        selectedNodes.clear();
    }

    @Override
    public void showKpiLayout(String title, List<ISystemElement> elementToShow) {
        logger.debug("show Kpi Layout");
        GraphdataView view = new GraphdataView();
        tabManager.addTab(title, view.getView());
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
    public ObservableList<ISystemElement> getSelectedNodes(){
        return selectedNodes;
    }

    @Override
    public void removeSelectedNode(ISystemElement node) {
        selectedNodes.remove(node);
        logger.debug("Removed node to selected: " + node.getName() + " " + selectedNodes.size());
        node.unSelected();
    }

    @Override
    public ISystemElement getVirtualLayer(ObservableList<ISystemElement> layerNodes) {
        IAppElement layer = new AppLayer.Builder("virtual").isVirtual().build();

        layerNodes.forEach((node) -> layer.getNodes().add(node.getAppElement()));

        return new VirtualLayerElement(layer, layerNodes, 0, 0, this, canvas);
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

    public void setTabManager(ITabManager tabManager) {
        this.tabManager = tabManager;
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

//        Cycle without functional operator
//        for (IAppElement elem : appService.getCurrentState().getLayers()) {
//            for (ISystemElement elementSelected : elementsSelected) {
//                if (elementSelected.getAppElement().equals(elem)) {
//                    elemesToDraw.add(elem);
//                }
//
//            }
//        }

        elementsSelected.forEach((elem) -> elemesToDraw.addAll(elem.getAppElement()));
       /* appService.getCurrentState().getLayers().stream().forEach((elem) -> {
            elementsSelected.stream().filter((elementSelected)
                    -> (elementSelected.getAppElement().equals(elem))).forEach((_item) -> {
                elemesToDraw.add(elem);
            });
        });*/

        return elemesToDraw;
    }

    private void clearNodeSelected() {

        selectedNodes.forEach(ISystemElement::unSelected);
        selectedNodes.clear();
    }

    private void initEvents() {

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            if (!event.getButton().equals(MouseButton.PRIMARY)) {
                return;
            }
            if (!ctrlPressed) {
                clearNodeSelected();
            }
        });

        Platform.runLater(() -> {
            // We need run later because the element is not been initialized yet
            Scene scene = ancorPaneSystem.getScene();
            scene.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
                if (event.getCode().equals(KeyCode.CONTROL)) {
                    ctrlPressed = true;
                }
            });

            scene.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
                if (event.getCode().equals(KeyCode.CONTROL)) {
                    ctrlPressed = false;
                }
            });

        });

    }

    private void initContextMenu() {
        ContextMenuFactory builder = new ContextMenuFactory(this);
        ContextMenu menu = builder.create();
        systemPane.setContextMenu(menu);
    }

}
