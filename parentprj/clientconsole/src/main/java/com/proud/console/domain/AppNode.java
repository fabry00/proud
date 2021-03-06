package com.proud.console.domain;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.*;

/**
 * AppElement that represents a Node of the sysetm
 * Created by Fabrizio Faustinoni on 15/09/2016.
 */
public class AppNode implements IAppElement {

    private static final int MAX_METRICS_COUNT = 50;
    private static final Type TYPE = Type.Node;

    private final String name;
    private final Map<AppMetric, ObservableList<XYChart.Data<Date, Object>>> metrics = new HashMap<>();
    private final Set<IAppElement> connectedTo = new HashSet<>();
    private final Map<ElementInfo.Type, ElementInfo> info = new HashMap<>();
    private final BooleanProperty isFineProp = new SimpleBooleanProperty(true);
    private final boolean isVirtual;

    private IAppElement.State state = IAppElement.State.FINE;

    protected AppNode(Builder builder) {
        this.name = builder.name;
        this.metrics.putAll(builder.metrics);
        this.state = builder.state;
        this.info.putAll(builder.info);
        this.connectedTo.addAll(builder.connectedTo);
        this.isFineProp.set(isFine());
        this.isVirtual = builder.isVirtual;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Type getType() {
        return TYPE;
    }

    @Override
    public boolean isVirtual() {
        return isVirtual;
    }

    @Override
    public ObservableList<XYChart.Data<Date, Object>> getMetric(AppMetric type) {
        return this.metrics.get(type);
    }

    @Override
    public Map<AppMetric, ObservableList<XYChart.Data<Date, Object>>> getMetrics() {
        return this.metrics;
    }

    @Override
    public void addMetricValue(AppMetric metric, Object key, Object value) {
        ObservableList<XYChart.Data<Date, Object>> serie;
        if (!metrics.containsKey(metric)) {
            serie = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
            metrics.put(metric, serie);
        } else {
            serie = metrics.get(metric);
        }
        serie.add(new XYChart.Data(key, value));
    }

    @Override
    public boolean AnomalyDetected() {
        return state.equals(IAppElement.State.ANOMALY_DETECTED);
    }

    @Override
    public boolean FailureDetected() {
        return state.equals(IAppElement.State.FAILURE_PREDICTED);
    }

    @Override
    public BooleanProperty IsFineProp() {
        return this.isFineProp;
    }

    @Override
    public State getState() {
        return this.state;
    }

    @Override
    public Map<ElementInfo.Type, ElementInfo> getInfo() {
        return Collections.unmodifiableMap(info);
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public Set<IAppElement> getConnections() {
        return this.connectedTo;
    }

    @Override
    public ObservableList<IAppElement> getNodes() {
        return FXCollections.unmodifiableObservableList(FXCollections.emptyObservableList());
    }

    @Override
    public IAppElement clone() {
        Builder builder = new AppNode.Builder(getName());
        builder.withConnections(this.getConnections())
                .withInfos(this.getInfo())
                .withMetricValues(this.getMetrics())
                .withState(getState());
        if (isVirtual) {
            builder.isVirtual();
        }


        return builder.build();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AppNode other = (AppNode) o;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name) + Objects.hash(getType());
    }

    @Override
    public void syncNewData(IAppElement newData) {
        state = newData.getState();
        info.putAll(newData.getInfo());
        connectedTo.addAll(newData.getConnections());
        isFineProp.set(newData.IsFineProp().get());

        newData.getMetrics().forEach((metric, newata) -> {
            ObservableList<XYChart.Data<Date, Object>> values
                    = metrics.get(metric);

            int total = values.size() + newata.size();
            if (total > MAX_METRICS_COUNT) {
                // To many values, remove the oldest
                int toDelete = total - MAX_METRICS_COUNT;
                values.remove(0, toDelete);
            }

            values.addAll(newata);
        });
    }

    private boolean isFine() {
        return !AnomalyDetected() && !FailureDetected();
    }

    public static class Builder {

        private final String name;
        private final Map<AppMetric, ObservableList<XYChart.Data<Date, Object>>> metrics = new HashMap<>();
        private final Set<IAppElement> connectedTo = new HashSet<>();

        private boolean isVirtual = false;
        private IAppElement.State state = IAppElement.State.FINE;
        private Map<ElementInfo.Type, ElementInfo> info = new HashMap<>();

        public Builder(String name) {
            this.name = name;
        }

        public Builder withMetricValue(AppMetric metric, Date key, Object value) {
            ObservableList<XYChart.Data<Date, Object>> serie;
            if (!metrics.containsKey(metric)) {
                serie = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
                metrics.put(metric, serie);
            } else {
                serie = metrics.get(metric);
            }
            serie.add(new XYChart.Data(key, value));
            return this;
        }

        public Builder withMetricValues(Map<AppMetric, ObservableList<XYChart.Data<Date, Object>>> metrics) {
            this.metrics.putAll(metrics);
            return this;
        }

        public Builder isInAbnormalState() {
            this.state = IAppElement.State.ANOMALY_DETECTED;
            return this;
        }

        public Builder isFailureDetected() {
            this.state = IAppElement.State.FAILURE_PREDICTED;
            return this;
        }

        public Builder withState(IAppElement.State state) {
            this.state = state;
            return this;
        }

        public Builder withInfo(ElementInfo info) {
            this.info.put(info.getType(), info);
            return this;
        }

        public Builder withInfos(Map<ElementInfo.Type, ElementInfo> infos) {
            this.info.putAll(infos);
            return this;
        }

        public Builder connectedTo(IAppElement node) {
            this.connectedTo.add(node);
            return this;
        }

        public Builder withConnections(Set<IAppElement> connectedTo) {
            this.connectedTo.addAll(connectedTo);
            return this;
        }

        public Builder isVirtual() {
            this.isVirtual = true;
            return this;
        }

        public IAppElement build() {
            IAppElement node = new AppNode(this);

            if (node.getName().isEmpty()) {
                // thread-safe
                throw new IllegalStateException("Node id not valid");
            }

            return node;
        }
    }

}
