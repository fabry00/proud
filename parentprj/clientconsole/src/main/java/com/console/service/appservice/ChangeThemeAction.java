package com.console.service.appservice;

import com.console.domain.AppAction;
import com.console.domain.AppState;
import com.console.domain.ICallback;
import org.apache.log4j.Logger;

class ChangeThemeAction implements IActionHandler {

    private final Logger logger = Logger.getLogger(ChangeThemeAction.class);

    @Override
    public void execute(AppAction action, ApplicationService appService,final ICallback callback) {
        logger.debug("ChangeTheme execution");

        appService.getMainApp().changeTheme();

    }
}
