package com.console.service.appservice;

import com.console.domain.AppAction;
import com.console.domain.AppState;
import com.console.domain.ICallback;

/**
 *
 * @author fabry
 */
class FullScreenAction implements IActionHandler {

    @Override
    public void execute(AppAction action, ApplicationService appService,final ICallback callback) {
        appService.getMainApp().switchToFullScreen((boolean) action.value);
    }

}
