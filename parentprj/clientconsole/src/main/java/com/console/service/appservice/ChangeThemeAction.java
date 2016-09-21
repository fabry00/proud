package com.console.service.appservice;

import com.console.domain.AppAction;
import com.console.domain.AppState;
import org.apache.log4j.Logger;

class ChangeThemeAction implements IActionHandler {

    private final Logger logger = Logger.getLogger(ChangeThemeAction.class);

    @Override
    public void execute(AppState currentState, AppAction action, ApplicationService appService) {
        logger.debug("ChangeTheme execution");

        appService.getMainApp().changeTheme();

    }
}
