package com.console.service.appservice;

import com.console.domain.AppAction;
import com.console.domain.AppState;
import com.console.domain.ICallback;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
class NewMessageAction implements IActionHandler {

    private final Logger logger = Logger.getLogger(NewMessageAction.class);

    @Override
    public void execute(AppAction action, ApplicationService appService,final ICallback callback) {
        String message = (String) action.value;
        logger.debug("New application message: " + message);
        appService.getCurrentState().setMessage(message);
    }

}
