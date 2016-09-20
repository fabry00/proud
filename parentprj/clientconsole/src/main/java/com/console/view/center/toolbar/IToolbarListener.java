package com.console.view.center.toolbar;

import com.console.domain.Metric;

/**
 *
 * @author fabry
 */
public interface IToolbarListener {

    public void resetSeriesClicked();

    public void metricSelected(Metric metric);
    
    public void nodesSelectedChanged();

}
