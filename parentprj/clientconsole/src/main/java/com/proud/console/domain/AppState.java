package com.proud.console.domain;

import com.proud.console.service.appservice.AppEventManager;
import com.proud.domain.PredictionType;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.apache.log4j.Logger;

/**
 * @author Fabrizio Faustinoni
 */
public final class AppState {

    // Every time the the node change state the event listChanged is fired
    // TODO TO CHECK
    //https://gist.github.com/andytill/3116203
    private final Callback<IAppElement, Observable[]> extractor = p -> new Observable[]{(Observable) p.IsFineProp()};

    private final Logger logger = Logger.getLogger(AppState.class);
    private final StringProperty message = new SimpleStringProperty("");
    private final ObservableList<IAppElement> nodes
            = FXCollections.synchronizedObservableList(FXCollections.observableArrayList(extractor));
    private final ObservableList<IAppElement> nodesInAnomalySate
            = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
    private final ObservableList<IAppElement> layers
            = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
    private State state = State.UNKNOWN;
    private final StringProperty stateProperty = new SimpleStringProperty(state.getLabel());
    private PredictionType failurePrediction = PredictionType.NOT_DETECTED;
    private PredictionType systemFailure = PredictionType.NOT_DETECTED;
    private AppEventManager eventManager;


    private AppState(Builder builder) {
        this.eventManager = builder.evtManager;
        this.state = builder.state;
        this.failurePrediction = builder.failurePrediction;
        this.systemFailure = builder.systemFailure;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        logger.debug("SetState: " + state.getLabel());
        this.state = state;
        this.stateProperty.set(state.getLabel());

        fireEvent(AppEvent.STATE_CHANGED, null);
    }

    public PredictionType getFailurePrediction() {
        return failurePrediction;
    }

    public void setFailurePrediction(PredictionType val) {
        this.failurePrediction = val;
        fireEvent(AppEvent.FAILURE_PREDICTED_CHANGED, null);
    }

    public PredictionType getSystemFailure() {
        return systemFailure;
    }

    public void setSystemFailure(PredictionType val) {
        this.systemFailure = val;
        fireEvent(AppEvent.SYSTEM_FAILURE_CHANGED, null);
    }

    public StringProperty getStateProp() {
        return stateProperty;
    }

    public StringProperty getMessageProp() {
        return message;
    }

    public ObservableList getNodesInAbnormalState() {
        return nodesInAnomalySate;
    }

    public ObservableList<IAppElement> getLayers() {
        return layers;
    }

    public AppEventManager getEventManager() {
        return eventManager;
    }

    public ObservableList<IAppElement> getNodes() {
        return nodes;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public void addNodeData(IAppElement newNodeData) {
        int itemIndex = nodes.lastIndexOf(newNodeData);
        IAppElement nodeData;
        if (itemIndex >= 0) {
            nodeData = nodes.get(itemIndex);
            //nodesSync.remove(itemIndex);
            //nodesSync.add(itemIndex, node);
            //NodeData currentNode = nodesSync.get(itemIndex);
            nodeData.syncNewData(newNodeData);
        } else {
            nodes.add(newNodeData);
        }

        if (newNodeData.AnomalyDetected() || newNodeData.FailureDetected()) {
            addAbnormalNode(newNodeData);
        } else {
            int index = getNodesInAbnormalState().lastIndexOf(newNodeData);
            if (index >= 0) {
                getNodesInAbnormalState().remove(index);
            }
        }

        if (!getNodesInAbnormalState().isEmpty()) {
            setState(State.ABNORMAL_NODE_STATE);
        }/* else {
         setState(State.NEWDATARECEIVED);
         }*/

    }

    public void addAbnormalNode(IAppElement node) {
        int index = nodesInAnomalySate.lastIndexOf(node);
        if (index < 0) {
            nodesInAnomalySate.add(node);
        }
    }

    public void addLayer(IAppElement layer) {
        int index = layers.lastIndexOf(layer);
        if (index < 0) {
            layers.add(layer);
        }
    }

    /**
     * Clone this state
     *
     * @return The State
     */
    @Override
    public AppState clone() {
        AppState cloned = new AppState.Builder(eventManager).build();
        cloneState(this, cloned);
        return cloned;
    }

    /**
     * Copy the state
     *
     * @param stateToCpy state to copy
     */
    public void copyFrom(AppState stateToCpy) {
        cloneState(stateToCpy, this);
    }

    private void cloneState(AppState src, AppState target) {

        src.getLayers().forEach(target::addLayer);
        src.getNodes().forEach(target::addNodeData);
        target.setMessage(src.getMessageProp().get());



        if(!target.getFailurePrediction().equals(src.getFailurePrediction())) {
            target.setFailurePrediction(src.getFailurePrediction());
        }
        if(!target.getSystemFailure().equals(src.getSystemFailure())) {
            target.setSystemFailure(src.getSystemFailure());
        }
        if(!target.getMessageProp().get().equals(src.getMessageProp().get())) {
            target.setMessage(src.getMessageProp().get());
        }


        // this must be the last
        if(!target.getState().equals(src.getState())) {
            target.setState(src.getState());
        }

    }

    private void fireEvent(AppEvent event, Object param) {
        if(eventManager != null) {
            eventManager.fireAppEvent(event, param);
        }
    }


    public static class Builder {

        private final AppEventManager evtManager;
        private final State state = State.UNKNOWN;
        private PredictionType failurePrediction = PredictionType.NOT_DETECTED;
        private PredictionType systemFailure = PredictionType.NOT_DETECTED;

        public Builder(AppEventManager evtManager) {
            this.evtManager = evtManager;
        }

        public Builder failurePrediction(PredictionType type) {
            this.failurePrediction = type;
            return this;
        }

        public Builder systemFailure(PredictionType type) {
            this.systemFailure = type;
            return this;
        }

        public AppState build() {
            return new AppState(this);
        }
    }

}
