package com.console.service.appservice;

import com.console.domain.AppAction;
import com.console.domain.AppState;
import com.console.domain.State;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
class StatusChangedAction implements IActionHandler {

    private Logger logger = Logger.getLogger(StatusChangedAction.class);

    @Override
    public void execute(AppState currentState,
            AppAction action, ApplicationService appService) {

        logger.debug("StatusChangedAction execution");
        State newState = (State) action.value;
        currentState.setState(newState);
    }

}
