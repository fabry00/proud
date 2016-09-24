package com.console.view.nodeview;

import com.airhacks.afterburner.views.FXMLView;

/**
 * Created by exfaff on 20/09/2016.
 */
public class NodeView extends FXMLView {

    public NodePresenter getRealPresenter() {
        return (NodePresenter) super.getPresenter();
    }

}
