package com.console.domain;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public final class AppState {

    // Every time the the node change state the event listChanged is fired
    // TODO TO CHECK
    //https://gist.github.com/andytill/3116203
    final Callback<IAppElement, Observable[]> extractor = new Callback<IAppElement, Observable[]>() {

        @Override
        public Observable[] call(IAppElement p) {
            return new Observable[]{(Observable) p.IsFineProp()};
        }
    };

    private final Logger logger = Logger.getLogger(AppState.class);
    private State state = State.UNKWOWN;
    private final StringProperty stateProperty = new SimpleStringProperty(state.getLabel());
    private final StringProperty message = new SimpleStringProperty("");

    private final ObservableList<IAppElement> nodes
            = FXCollections.synchronizedObservableList(FXCollections.observableArrayList(extractor));

    private final ObservableList<IAppElement> nodesInAnomalySate
            = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());

    private final ObservableList<IAppElement> layers
            = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());

    private AppState(Builder builder) {
        this.state = builder.state;
    }

    private AppState() {

    }

    public State getState() {
        return state;
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

    public void setState(State state) {
        this.state = state;
        this.stateProperty.set(state.getLabel());
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
     * @return
     */
    @Override
    public AppState clone() {
        AppState cloned = new AppState();
        cloneState(this, cloned);
        return cloned;
    }

    /**
     * Copy the state
     *
     * @param stateToCpy
     */
    public void copyFrom(AppState stateToCpy) {
        cloneState(stateToCpy, this);
    }

    private void cloneState(AppState src, AppState target) {
        src.getLayers().forEach((layer) -> target.addLayer(layer));
        src.getNodes().forEach((node) -> target.addNodeData(node));
        target.setMessage(src.getMessageProp().getValue());

        // This must be the last statement
        target.setState(src.getState());
    }

    public ObservableList<IAppElement> getNodes() {
        return nodes;
    }

    public static class Builder {

        private State state = State.UNKWOWN;

        public Builder() {
        }

        public AppState build() {
            return new AppState(this);
        }
    }

}
