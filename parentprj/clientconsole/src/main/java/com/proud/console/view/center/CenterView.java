/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proud.console.view.center;

import com.airhacks.afterburner.views.FXMLView;

/**
 *
 * @author Fabrizio Faustinoni
 */
public class CenterView extends FXMLView {

    public CenterPresenter getRealPresenter() {
        return (CenterPresenter) super.getPresenter();
    }

}
