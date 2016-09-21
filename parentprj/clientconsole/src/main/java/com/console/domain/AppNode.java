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
public class AppNode {

    private static final int MAX_METRICS_COUNT = 50;

    private enum NodeState {
        FINE, ANOMALY_DETECTED, FAILURE_PREDICTED
    }
    private final String name;
    private NodeState state = NodeState.FINE;
    private final Map<AppMetric, ObservableList<XYChart.Data<Date, Object>>> metrics = new HashMap<>();
    private final Set<AppNode> connectedTo = new HashSet<>();

    private final Map<NodeInfo.Type, NodeInfo> info = new HashMap<>();
    private BooleanProperty isFineProp = new SimpleBooleanProperty(true);

    private AppNode(Builder builder) {
        this.name = builder.name;
        this.metrics.putAll(builder.metrics);
        this.state = builder.state;
        this.info.putAll(builder.info);
        this.connectedTo.addAll(builder.connectedTo);
        this.isFineProp.set(IsFine());
    }

    public String getName() {
        return this.name;
    }

    public ObservableList<XYChart.Data<Date, Object>> getMetric(AppMetric type) {
        return this.metrics.get(type);
    }

    public void addMetricValue(AppMetric metric, Object key, Object value) {
        this.metrics.get(metric).add(new XYChart.Data(key, value));
    }

    public boolean AnomalyDetected() {
        return state.equals(NodeState.ANOMALY_DETECTED);
    }

    public boolean FailureDetected() {
        return state.equals(NodeState.FAILURE_PREDICTED);
    }

    public BooleanProperty IsFineProp() {
        return this.isFineProp;
    }

    public boolean IsFine() {
        return !AnomalyDetected() && !FailureDetected();
    }

    public Map<NodeInfo.Type, NodeInfo> getInfo() {
        return Collections.unmodifiableMap(info);
    }

    public String toString() {
        return this.name;
    }

    public Set<AppNode> getConnections() {
        return this.connectedTo;
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

    public static class Builder {

        private final String name;
        private final Map<AppMetric, ObservableList<XYChart.Data<Date, Object>>> metrics = new HashMap<>();
        private final Set<AppNode> connectedTo = new HashSet<>();

        private NodeState state = NodeState.FINE;

        private Map<NodeInfo.Type, NodeInfo> info = new HashMap<>();

        public Builder(String node) {
            this.name = node;
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
            this.state = NodeState.ANOMALY_DETECTED;
            return this;
        }

        public Builder isFailureDetected() {
            this.state = NodeState.FAILURE_PREDICTED;
            return this;
        }

        public Builder withInfo(NodeInfo info) {
            this.info.put(info.type, info);
            return this;
        }

        public Builder connectedTo(AppNode node) {
            this.connectedTo.add(node);
            return this;
        }

        public AppNode build() {
            AppNode node = new AppNode(this);

            if (node.getName().isEmpty()) {
                // thread-safe
                throw new IllegalStateException("Node id not valid");
            }

            return node;
        }

        public static void syncNewData(AppNode node, AppNode newData) {
            node.state = newData.state;

            node.isFineProp.set(node.IsFine());

            newData.metrics.forEach((metric, newata) -> {
                ObservableList<XYChart.Data<Date, Object>> values
                        = node.metrics.get(metric);

                int total = values.size() + newata.size();
                if (total > MAX_METRICS_COUNT) {
                    // To many values, remove the oldest
                    int toDelete = total - MAX_METRICS_COUNT;
                    values.remove(0, toDelete);
                }

                values.addAll(newata);
            });
        }

    }

    public static class NodeInfo {

        public enum Type {
            IP
        };

        public NodeInfo(Type type, String value) {
            this.type = type;
            this.value = value;
        }

        private final String value;
        private final Type type;

        public String getValue() {
            return value;
        }

        public Type getType() {
            return type;
        }

    }
}
