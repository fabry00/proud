package com.console.view.status;

import com.console.domain.*;
import com.console.service.appservice.ApplicationService;
import com.console.util.Configs;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;

import javax.inject.Inject;

import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.apache.log4j.Logger;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.StatusBar;

/**
 * @author user
 */
public class StatusPresenter implements Initializable, IAppStateListener{

    private final Logger logger = Logger.getLogger(StatusPresenter.class);

    @Inject
    private ApplicationService appService;

    @FXML
    private StatusBar statusBar;

    private Label failurePredictionStatus;
    private Label systemFailureStatus;
    private SequentialTransition sequentialTransition;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("Initialize");

        // setProgressIndicator(appService.getCurrentState().getState());

        setLeftItems();
        setRightItems();

        appService.getCurrentState().getEventManager().subscribeToEvent(this, AppEvent.STATE_CHANGED);
        appService.getCurrentState().getEventManager().subscribeToEvent(this, AppEvent.FAILURE_PREDICTED_CHANGED);
        appService.getCurrentState().getEventManager().subscribeToEvent(this, AppEvent.SYSTEM_FAILURE_CHANGED);
        failureEvent();
    }


    @Override
    public void AppEvent(AppEvent event,Object param) {
        failureEvent();
    }

    private void failureEvent() {
        logger.debug("failureEvent");
        sequentialTransition.getChildren().clear();
        manageStatusLabel(appService.getCurrentState().getFailurePrediction(),failurePredictionStatus);
        manageStatusLabel(appService.getCurrentState().getSystemFailure(),systemFailureStatus);
    }

    private void manageStatusLabel(PredictionType failureType, Label statusLabel) {
        if(failureType.isError()){
            statusLabel.getStyleClass().add("system-status-label-error");
            statusLabel.getStyleClass().remove("system-status-label-fine");

            // Do not why, but this freeze the tabs
            //sequentialTransition.getChildren().add(getTransition(statusLabel));
            //sequentialTransition.play();
        }else {
            statusLabel.getStyleClass().add("system-status-label-fine");
            statusLabel.getStyleClass().remove("system-status-label-fine");
        }
        statusLabel.setText(failureType.getLabel());
    }

    private void setLeftItems() {
        final String appVersion = appService.getAppConfigs().getProperty(Configs.VERSION);
        Label versionLabel = new Label("Version: " + appVersion);
        Label failurePrediction = new Label("FAILURE PREDICTION");
        failurePredictionStatus = new Label("NOT_DETECTED");
        failurePredictionStatus.setPrefWidth(100);
        failurePredictionStatus.getStyleClass().add("system-status-label");
        failurePredictionStatus.setAlignment(Pos.CENTER);
        Label systemFailure = new Label("SYSTEM FAILURE");
        systemFailureStatus = new Label("DETECTED");
        systemFailureStatus.getStyleClass().add("system-status-label");
        systemFailureStatus.setPrefWidth(100);
        systemFailureStatus.setAlignment(Pos.CENTER);


        sequentialTransition = new SequentialTransition();
        sequentialTransition.setCycleCount(Timeline.INDEFINITE);
        sequentialTransition.setAutoReverse(true);

        HBox leftBox = new HBox();
        leftBox.setSpacing(3);
        leftBox.getChildren().addAll(versionLabel, new Separator(),
                failurePrediction, failurePredictionStatus, new Separator(),
                systemFailure, systemFailureStatus);

        statusBar.getLeftItems().add(leftBox);

    }

    private FadeTransition getTransition(Node node){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(700),node);
        fadeTransition.setFromValue(1.0f);
        fadeTransition.setToValue(0.4f);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(true);
        return fadeTransition;
    }

    private void setRightItems() {
        statusBar.textProperty().bind(appService.getCurrentState().getMessageProp());
        Label statusLabel = new Label();
        statusLabel.textProperty().bind(appService.getCurrentState().getStateProp());
        statusBar.getRightItems().add(new Separator(Orientation.VERTICAL));
        statusBar.getRightItems().add(statusLabel);
        statusBar.getRightItems().add(new Separator(Orientation.VERTICAL));
    }

    /*private void setProgressIndicator(State state) {
        if (state.equals(State.NEWDATARECEIVED)
                || state.equals(State.WAITING)
                || state.equals(State.NEWDATARECEIVED)) {
            statusBar.setProgress(-1);
        } else {
            statusBar.setProgress(0);
        }
    }*/

}
