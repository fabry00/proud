package com.proud.console.service.appservice;

import com.proud.console.domain.AppAction;
import com.proud.console.domain.ICallback;

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
