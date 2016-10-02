package com.proud.console.service.appservice;

import com.proud.console.domain.AppAction;
import com.proud.console.domain.ICallback;
import org.apache.log4j.Logger;

class ChangeThemeAction implements IActionHandler {

    private final Logger logger = Logger.getLogger(ChangeThemeAction.class);

    @Override
    public void execute(AppAction action, ApplicationService appService,final ICallback callback) {
        logger.debug("ChangeTheme execution");

        appService.getMainApp().changeTheme();

    }
}
