package com.console.service.appservice;

import com.console.domain.AppAction;
import com.console.domain.AppState;
import com.console.domain.ICallback;

/**
 *
 * @author fabry
 */
interface IActionHandler {

    public void execute(AppAction action, ApplicationService appService,final ICallback callback);
}
