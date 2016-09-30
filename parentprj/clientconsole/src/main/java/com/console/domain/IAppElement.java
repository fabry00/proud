package com.console.domain;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

/**
 *
 * @author exfaff
 */
public interface IAppElement {

    public enum Type {
        Node, Layer
    }

    public enum State {
        FINE, ANOMALY_DETECTED, FAILURE_PREDICTED
    }

    public String getName();
    
    public Type getType();

    public ObservableList<XYChart.Data<Date, Object>> getMetric(AppMetric type);

    public Map<AppMetric, ObservableList<XYChart.Data<Date, Object>>> getMetrics();

    public void addMetricValue(AppMetric metric, Object key, Object value);

    public boolean AnomalyDetected();

    public boolean FailureDetected();

    public BooleanProperty IsFineProp();

    public State getState();

    public Map<ElementInfo.Type, ElementInfo> getInfo();

    public ObservableList<IAppElement> getNodes();

    public Set<IAppElement> getConnections();

    public void syncNewData(IAppElement newData);

    public boolean isVirtual();
}
