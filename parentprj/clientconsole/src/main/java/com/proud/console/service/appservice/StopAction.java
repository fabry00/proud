package com.proud.console.service.appservice;

import com.proud.console.domain.AppAction;
import com.proud.console.domain.ICallback;
import com.proud.console.domain.State;
import org.apache.log4j.Logger;

/**
 *
 * @author Fabrizio Faustinoni
 */
class StopAction implements IActionHandler {

    private Logger logger = Logger.getLogger(StopAction.class);

    @Override
    public void execute(AppAction action, ApplicationService appService,final ICallback callback) {
        logger.debug("Stop action execution");

        /*if (!currentState.getState().equals(State.STARTED)
                && !currentState.getState().equals(State.PROCESSING)) {
            StringBuilder builder
                    = new StringBuilder("Wrong application status. Expected: ");
            builder.append(State.STARTED.toString())
                    .append(" or ")
                    .append(State.PROCESSING.toString())
                    .append(" but found: ")
                    .append(currentState.getState().toString());
            logger.warn(builder.toString());
            return currentState;
        }*/
        appService.stopAllServices();

        appService.getCurrentState().setState(State.STOPPED);
    }

}
