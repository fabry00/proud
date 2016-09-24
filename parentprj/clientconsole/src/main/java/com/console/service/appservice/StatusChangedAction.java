package com.console.service.appservice;

import com.console.domain.AppAction;
import com.console.domain.AppState;
import com.console.domain.ICallback;
import com.console.domain.State;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
class StatusChangedAction implements IActionHandler {

    private Logger logger = Logger.getLogger(StatusChangedAction.class);

    @Override
    public void execute(AppAction action, ApplicationService appService,final ICallback callback) {

        logger.debug("StatusChangedAction execution");
        State newState = (State) action.value;
        appService.getCurrentState().setState(newState);
    }

}
