package com.console.view.tree;

import com.console.domain.AppNode;
import com.console.domain.ElementInfo;
import com.console.service.appservice.ApplicationService;
import java.net.URL;
import java.util.*;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.PropertySheet.Item;
import com.console.domain.IAppElement;

/**
 *
 * @author fabry
 */
public class TreePresenter implements Initializable/*, IAppStateListener*/ {

    private static final String ABNORMAL_STATUS_CLASS = "list-view-abnormal";
    private static final String FAILURE_PREDICTED_CLASS = "list-view-failue";

    private final Logger logger = Logger.getLogger(TreePresenter.class);

    private PopOver statusPopOver;
    private Label ipAddress;
    private Label statusNode;

    @Inject
    ApplicationService appService;

    @FXML
    private ListView nodeList;

    protected ListProperty<IAppElement> listProperty = new SimpleListProperty<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("initialize");
       // appService.subscribeToState(this, State.ABNORMAL_NODE_STATE);
        listProperty.set(appService.getCurrentState().getNodes());
        nodeList.itemsProperty().bind(listProperty);

       

        nodeList.setCellFactory(lv -> {
            ListCell<Item> cell = new DefaultListCell();
            cell.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
                if (isNowHovered && !cell.isEmpty()) {
                    showPopup(cell);
                } else {
                    // hidePopup();
                }
            });
            return cell;
        });

        initPopOver();

    }

    private void initPopOver() {
        statusPopOver = new PopOver();
        statusPopOver.headerAlwaysVisibleProperty().set(true);
        VBox pane = new VBox();
        pane.setPrefWidth(400);
        pane.setPadding(new Insets(10,10,10,2));
        ipAddress = new Label();
        statusNode = new Label();
        pane.getChildren().add(ipAddress);
        pane.getChildren().add(statusNode);
        statusPopOver.setContentNode(pane);
    }

    private void showPopup(ListCell<Item> cell) {
        IAppElement node = (IAppElement) cell.getItem();
        statusPopOver.setTitle("Node " + node.getName() + " Info");
        String ipAddressStr = node.getInfo().get(ElementInfo.Type.IP).getValue();
        ipAddress.setText("IP ADDRESS: " + ipAddressStr);
        statusNode.getStyleClass().remove(FAILURE_PREDICTED_CLASS);
        statusNode.getStyleClass().remove(ABNORMAL_STATUS_CLASS);
        if (node.FailureDetected()) {
            
            statusNode.setText("FAILURE PREDICTED");
            statusNode.getStyleClass().add(FAILURE_PREDICTED_CLASS);
        } else if (node.AnomalyDetected()) {
            statusNode.setText("ANOMALY DETECTED");
            statusNode.getStyleClass().add(ABNORMAL_STATUS_CLASS);
        } else {
            statusNode.setText("FINE");
        }
        statusPopOver.show(cell);
    }

    private void hidePopup() {
        statusPopOver.hide();
    }

    /*@Override
    public void AppStateChanged(AppState oldState, AppState currentState) {
        //nodeList.refresh();
    }*/

    private static class DefaultListCell<T> extends ListCell<T> {

        @Override
        public void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else if (item instanceof AppNode) {
                AppNode node = (AppNode) item;
                setText(node.getName());
                if (node.FailureDetected()) {
                    System.out.println("################################################### FailureDetected "+node.getName());
                    getStyleClass().add(FAILURE_PREDICTED_CLASS);
                } else if (node.AnomalyDetected()) {
                    System.out.println("################################################### AnomalyDetected "+node.getName());
                    getStyleClass().add(ABNORMAL_STATUS_CLASS);
                } else {
                    System.out.println("################################################### fine "+node.getName());
                    getStyleClass().remove(FAILURE_PREDICTED_CLASS);
                    getStyleClass().remove(ABNORMAL_STATUS_CLASS);
                }

            } else {
                setText(item == null ? "null" : item.toString());
                setGraphic(null);
            }
        }
    }
}
