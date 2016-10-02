package com.proud.console.view.graphdata.toolbar;

import com.proud.console.domain.AppMetric;

/**
 *
 * @author Fabrizio Faustinoni
 */
public interface IToolbarListener {

    void resetSeriesClicked();

    void metricSelected(AppMetric metric);

    void nodesSelectedChanged();

}
