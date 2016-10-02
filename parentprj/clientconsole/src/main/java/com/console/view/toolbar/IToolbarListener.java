package com.console.view.toolbar;

import com.console.domain.AppMetric;
import com.console.domain.IAppElement;

import java.util.List;

/**
 * Toolbar listener interface
 * @author Fabrizio Faustinoni
 */
interface IToolbarListener {

    void showKpi(List<IAppElement> nodes, List<AppMetric> metrics);

}
