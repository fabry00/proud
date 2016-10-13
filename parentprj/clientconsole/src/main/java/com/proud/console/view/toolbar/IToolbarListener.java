package com.proud.console.view.toolbar;

import com.proud.console.domain.AppMetric;
import com.proud.console.domain.IAppElement;

import java.util.List;

/**
 * Toolbar listener interface
 * @author Fabrizio Faustinoni
 */
public interface IToolbarListener {

    void showKpi(List<IAppElement> nodes, List<AppMetric> metrics);

}
