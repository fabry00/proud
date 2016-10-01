package com.console.service.appservice;

import com.console.domain.AppAction;
import com.console.domain.ICallback;

/**
 *
 * @author Fabrizio Faustinoni
 */
class FullScreenAction implements IActionHandler {

    @Override
    public void execute(AppAction action, ApplicationService appService,final ICallback callback) {
        appService.getMainApp().switchToFullScreen((boolean) action.value);
    }

}
