package com.console.view.center.toolbar;

import com.console.domain.AppMetric;

/**
 *
 * @author fabry
 */
public interface IToolbarListener {

    public void resetSeriesClicked();

    public void metricSelected(AppMetric metric);
    
    public void nodesSelectedChanged();

}
