package com.console.service.appservice;

import com.console.domain.AppAction;
import com.console.domain.ICallback;

/**
 *
 * @author Fabrizio Faustinoni
 */
interface IActionHandler {

    void execute(AppAction action, ApplicationService appService, final ICallback callback);
}
