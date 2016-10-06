package com.proud.console.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fabrizio Faustinoni
 */
public final class AppLayer extends AppNode implements IAppElement {

    private static final Type TYPE = Type.Layer;
    private final ObservableList<IAppElement> layersNodes = FXCollections.observableArrayList();

    private AppLayer(Builder builder) {
        super(builder);
        this.layersNodes.addAll(builder.layersNodes);
    }

    @Override
    public Type getType() {
        return TYPE;
    }

    @Override
    public ObservableList<IAppElement> getNodes() {
        return this.layersNodes;
    }

    @Override
    public IAppElement clone() {
        return new AppLayer.Builder(getName())
                .withNodes(layersNodes).build();
    }

    public static class Builder extends AppNode.Builder {

        private final List<IAppElement> layersNodes = new ArrayList<>();

        public Builder(String name) {
            super(name);
        }

        public Builder withNodes(List<IAppElement> layersNodes) {
            this.layersNodes.addAll(layersNodes);
            return this;
        }

        @Override
        public IAppElement build() {
            return new AppLayer(this);
        }
    }
}
