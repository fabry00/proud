package com.proud.console.service.appservice;

import com.proud.console.domain.AppAction;
import com.proud.console.domain.ICallback;
import com.proud.console.domain.State;
import org.apache.log4j.Logger;

/**
 *
 * @author Fabrizio Faustinoni
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
