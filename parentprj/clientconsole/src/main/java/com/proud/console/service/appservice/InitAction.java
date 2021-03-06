package com.proud.console.service.appservice;

import com.proud.console.domain.AppAction;
import com.proud.console.domain.AppState;
import com.proud.console.domain.ICallback;
import com.proud.console.domain.State;
import com.proud.console.service.provider.AppDataProvider;
import com.proud.console.service.provider.IDataProvider;
import org.apache.log4j.Logger;

/**
 *
 * @author Fabrizio Faustinoni
 */
class InitAction implements IActionHandler {

    private final static State expected = State.NOT_STARTED;
    private final Logger logger = Logger.getLogger(InitAction.class);

    @Override
    public void execute(AppAction action, ApplicationService appService, final ICallback callback) {
        logger.debug("Init action execution");

        // TODO clean code
        //        IBackendService backendService = (IBackendService) appService.getService(ServiceName.BACKEND);
        //
        //        if (!currentState.getState().equals(expected)) {
        //            StringBuilder builder
        //                    = new StringBuilder("Wrong application status. Expected: ");
        //            builder.append(expected.toString())
        //                    .append(" but found: ")
        //                    .append(currentState.getState().toString());
        //            logger.warn(builder.toString());
        //            return;
        //        }
        //
        //        try {
        //            backendService.start();
        //        } catch (BackEndServiceException ex) {
        //            logger.error(ex);
        //            currentState.setState(State.ERROR);
        //        }
        //        currentState.setState(State.STARTED);
        IDataProvider provider = new AppDataProvider();

        provider.getSystemState(new ICallback() {
            @Override
            public void success(Object obj
            ) {
                appStateRetrieved((AppState) obj, appService.getCurrentState(), callback);
            }

            @Override
            public void fail(String message
            ) {
                logger.error(message);
            }
        });
    }

    private void appStateRetrieved(AppState state, AppState currentState, ICallback callback) {
        currentState.copyFrom(state);
        currentState.setState(State.STARTED);
        callback.success(null);
    }
}
