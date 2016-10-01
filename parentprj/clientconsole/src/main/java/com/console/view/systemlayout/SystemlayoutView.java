package com.console.view.systemlayout;

import com.airhacks.afterburner.views.FXMLView;

/**
 * System view
 * Created by Fabrizio Faustinoni on 20/09/2016.
 */
public class SystemlayoutView  extends FXMLView {

    public SystemlayoutPresenter getRealPresenter() {
        return (SystemlayoutPresenter) super.getPresenter();
    }

}
