package com.console.domain;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author exfaff
 */
public final class AppLayer extends AppNode implements AppElement {

    private final ObservableList<AppElement> layersNodes = FXCollections.observableArrayList();

    private AppLayer(Builder builder) {
        super(builder);
        this.layersNodes.addAll(builder.layersNodes);
    }

    @Override
    public Type getType() {
        return Type.Layer;
    }
    
    @Override
    public ObservableList<AppElement> getNodes() {
        return this.layersNodes;
    }

    public static class Builder extends AppNode.Builder {

        private final List<AppElement> layersNodes = new ArrayList<>();

        public Builder(String name) {
            super(name);
        }
        
        public Builder withNodes(List<AppElement> layersNodes){
            this.layersNodes.addAll(layersNodes);
            return this;
        }

        @Override
        public AppElement build() {
            return new AppLayer(this);
        }
    }
}
