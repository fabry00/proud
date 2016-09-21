package com.console.domain;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

/**
 *
 * @author fabry
 */
public class AppState {

    // Every time the the node change state the event listChanged is fired
    // TODO TO CHECK
    //https://gist.github.com/andytill/3116203
    Callback<AppNode, Observable[]> extractor = new Callback<AppNode, Observable[]>() {

        @Override
        public Observable[] call(AppNode p) {
            return new Observable[]{(Observable) p.IsFineProp()};
        }
    };

    private State state = State.UNKWOWN;
    private final StringProperty stateProperty = new SimpleStringProperty("");
    private final StringProperty message = new SimpleStringProperty("");
    private final ObservableList<AppNode> nodes
            = FXCollections.synchronizedObservableList(FXCollections.observableArrayList(extractor));

    private final ObservableList<AppNode> nodesInAnomalySate
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

    public StringProperty getMessage() {
        return message;
    }

    public ObservableList getNodesInAbnormalState() {
        return nodesInAnomalySate;
    }

    public void setState(State state) {
        this.state = state;
        this.stateProperty.set(state.getLabel());
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public void addNodeData(AppNode newNodeData) {
        int itemIndex = nodes.lastIndexOf(newNodeData);
        AppNode nodeData;
        if (itemIndex >= 0) {
            nodeData = nodes.get(itemIndex);
            //nodesSync.remove(itemIndex);
            //nodesSync.add(itemIndex, node);
            //NodeData currentNode = nodesSync.get(itemIndex);
            AppNode.Builder.syncNewData(nodeData, newNodeData);
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
        } else {
            setState(State.NEWDATARECEIVED);
        }
    }

    public void addAbnormalNode(AppNode node) {
        int index = nodesInAnomalySate.lastIndexOf(node);
        if (index < 0) {
            nodesInAnomalySate.add(node);
        }
    }

    /**
     * Clone this state
     *
     * @return
     */
    public AppState clone() {
        AppState cloned = new AppState();

        cloned.setState(state);
        nodes.stream().forEach((node) -> cloned.addNodeData(node));
        cloned.setMessage(message.getValue());

        return cloned;
    }

    /**
     * Copy the state
     *
     * @param state
     */
    public void copyFrom(AppState stateToCpy) {
        setState(stateToCpy.state);
        stateToCpy.nodes.stream().forEach((node) -> addNodeData(node));
        setMessage(stateToCpy.message.getValue());
    }

    public ObservableList<AppNode> getNodes() {
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
