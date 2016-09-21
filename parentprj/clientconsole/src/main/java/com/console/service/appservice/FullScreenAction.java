package com.console.service.appservice;

import com.console.domain.AppAction;
import com.console.domain.AppState;

/**
 *
 * @author fabry
 */
public class FullScreenAction implements IActionHandler {

    @Override
    public void execute(AppState currentState, AppAction action, ApplicationService appService) {
        appService.getMainApp().switchToFullScreen((boolean) action.value);
    }

}
