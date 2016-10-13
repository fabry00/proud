package com.proud.console.view.center;


import com.proud.console.domain.AppMetric;
import com.proud.console.domain.IAppElement;
import com.proud.console.view.graphdata.GraphdataView;
import com.proud.console.view.toolbar.IToolbarListener;
import javafx.scene.control.Button;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Toolbar listener
 * Created by Fabrizio Faustinoni on 02/10/2016.
 */
public class ToolbarListener implements IToolbarListener {

    private final Logger logger = Logger.getLogger(ToolbarListener.class);

    private final ITabsManager tabsManger;

    public ToolbarListener(ITabsManager tabsManager) {
        this.tabsManger = tabsManager;
    }

    @Override
    public void showKpi(List<IAppElement> nodes, List<AppMetric> metrics) {
        logger.debug("showKPi of nodes: " + nodes + " metrics: " + metrics);
        String title = getTitle(nodes, metrics);
        logger.debug("Title: "+title);
        GraphdataView view = new GraphdataView();
        tabsManger.addTab(title,view.getView());
    }

    private String getTitle(List<IAppElement> nodes, List<AppMetric> metrics) {
        String title = "KPIs";
        for (IAppElement node : nodes) {
            title += " " + node.getName();
        }
        title += " [";
        for (AppMetric metric : metrics) {
            title += metric.name() + " ";
        }

        title += "]";

        return title;
    }
}
