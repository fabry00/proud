package com.proud.console.service.appservice;

import com.proud.console.domain.AppAction;
import com.proud.console.domain.ICallback;

/**
 *
 * @author Fabrizio Faustinoni
 */
interface IActionHandler {

    void execute(AppAction action, ApplicationService appService, final ICallback callback);
}
