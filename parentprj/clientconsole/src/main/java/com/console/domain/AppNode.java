package com.console.domain;

import java.util.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

/**
 * Created by exfaff on 15/09/2016.
 */
public class AppNode implements IAppElement {

    private static final int MAX_METRICS_COUNT = 50;

    private final String name;
    private IAppElement.State state = IAppElement.State.FINE;
    private final Map<AppMetric, ObservableList<XYChart.Data<Date, Object>>> metrics = new HashMap<>();
    private final Set<IAppElement> connectedTo = new HashSet<>();

    private final Map<ElementInfo.Type, ElementInfo> info = new HashMap<>();
    private final BooleanProperty isFineProp = new SimpleBooleanProperty(true);

    protected AppNode(Builder builder) {
        this.name = builder.name;
        this.metrics.putAll(builder.metrics);
        this.state = builder.state;
        this.info.putAll(builder.info);
        this.connectedTo.addAll(builder.connectedTo);
        this.isFineProp.set(isFine());
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Type getType() {
        return Type.Node;
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
        this.metrics.get(metric).add(new XYChart.Data(key, value));
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
        return Objects.hash(name);
    }

    @Override
    public void syncNewData(IAppElement newData) {
        state = newData.getState();

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
        private final Set<AppNode> connectedTo = new HashSet<>();

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

        public Builder isInAbnormalState() {
            this.state = IAppElement.State.ANOMALY_DETECTED;
            return this;
        }

        public Builder isFailureDetected() {
            this.state = IAppElement.State.FAILURE_PREDICTED;
            return this;
        }

        public Builder withInfo(ElementInfo info) {
            this.info.put(info.getType(), info);
            return this;
        }

        public Builder connectedTo(AppNode node) {
            this.connectedTo.add(node);
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
