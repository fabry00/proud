package com.console.domain;

import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Fabrizio Faustinoni
 */
public interface IAppElement {

    String getName();

    Type getType();

    ObservableList<XYChart.Data<Date, Object>> getMetric(AppMetric type);

    Map<AppMetric, ObservableList<XYChart.Data<Date, Object>>> getMetrics();

    void addMetricValue(AppMetric metric, Object key, Object value);

    boolean AnomalyDetected();

    boolean FailureDetected();

    BooleanProperty IsFineProp();

    State getState();

    Map<ElementInfo.Type, ElementInfo> getInfo();

    ObservableList<IAppElement> getNodes();

    Set<IAppElement> getConnections();

    void syncNewData(IAppElement newData);

    boolean isVirtual();

    IAppElement clone();

    enum Type {
        Node, Layer
    }

    enum State {
        FINE, ANOMALY_DETECTED, FAILURE_PREDICTED
    }
}
