package com.console.service.appservice;

import com.console.domain.AppAction;
import com.console.domain.AppState;

/**
 *
 * @author fabry
 */
interface IActionHandler {

    public void execute(AppState currentState,
            AppAction action, ApplicationService appService);
}
