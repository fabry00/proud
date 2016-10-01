package com.console.view.graphdata.toolbar;

import com.console.domain.AppMetric;

/**
 *
 * @author Fabrizio Faustinoni
 */
public interface IToolbarListener {

    void resetSeriesClicked();

    void metricSelected(AppMetric metric);

    void nodesSelectedChanged();

}
