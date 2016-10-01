package com.console.service.appservice;

import com.console.domain.ActionType;
import com.console.domain.AppAction;
import com.console.domain.ICallback;
import com.console.domain.State;
import org.apache.log4j.Logger;

/**
 *
 * @author Fabrizio Faustinoni
 */
class CloseAction implements IActionHandler {

    private final Logger logger = Logger.getLogger(CloseAction.class);

    @Override
    public void execute(AppAction action, ApplicationService appService,final ICallback callback) {
        logger.debug("Close Action execution");
        if (!appService.getCurrentState().getState().equals(State.STOPPED)) {
            logger.debug("Application not stopped, stopping");
            appService.dispatch(new AppAction<>(ActionType.STOP, null));
        }

        try {
            // Wait all thread exit
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }
        logger.debug("Exit the application");
        System.exit(0);
    }

}
