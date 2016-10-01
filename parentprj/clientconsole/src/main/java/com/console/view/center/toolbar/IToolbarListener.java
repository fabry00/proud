package com.console.view.center.toolbar;

import com.console.domain.AppMetric;

/**
 *
 * @author Fabrizio Faustinoni
 */
interface IToolbarListener {

    void resetSeriesClicked();

    void metricSelected(AppMetric metric);

    void nodesSelectedChanged();

}
