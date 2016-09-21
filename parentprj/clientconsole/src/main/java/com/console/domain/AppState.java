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
    //https://gist.github.com/andytill/3116203
    Callback<AppNode, Observable[]> extractor = new Callback<AppNode, Observable[]>() {

        @Override
        public Observable[] call(AppNode p) {
            return new Observable[]{(Observable) p.IsFineProp()};
        }
    };

    private State state;
    private final StringProperty stateProperty = new SimpleStringProperty("");
    private final StringProperty message = new SimpleStringProperty("");
    private final ObservableList<AppNode> nodes
            = FXCollections.synchronizedObservableList(FXCollections.observableArrayList(extractor));

    private final ObservableList<AppNode> nodesInAnomalySate
            = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());

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
    }

    public void addAbnormalNode(AppNode node) {
        int index = nodesInAnomalySate.lastIndexOf(node);
        if (index < 0) {
            nodesInAnomalySate.add(node);
        }
    }

    public AppState clone() {
        AppState cloned = new AppState();

        cloned.setState(state);
        nodes.stream().forEach((node) -> cloned.addNodeData(node));
        cloned.setMessage(message.getValue());

        return cloned;
    }

    public ObservableList<AppNode> getNodes() {
        return nodes;
    }
}
