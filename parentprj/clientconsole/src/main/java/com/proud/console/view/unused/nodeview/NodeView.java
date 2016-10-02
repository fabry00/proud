package com.proud.console.view.unused.nodeview;

import com.airhacks.afterburner.views.FXMLView;

/**
 * Node View
 * Created by Fabrizio Faustinoni on 20/09/2016.
 */
public class NodeView extends FXMLView {

    public NodePresenter getRealPresenter() {
        return (NodePresenter) super.getPresenter();
    }

}
