package com.proud.console.service.appservice;

import com.proud.console.domain.ActionType;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Fabrizio Faustinoni
 */
class ActionFactory {

    private static final Map<ActionType, IActionHandler> MAPPER
            = new HashMap<ActionType, IActionHandler>() {
        {
            put(ActionType.START, new InitAction());
            put(ActionType.STOP, new StopAction());
            put(ActionType.STATUS_CHANGED, new StatusChangedAction());
            put(ActionType.DATA_RECEIVED, new ProcessDataAction());
            put(ActionType.CLOSE, new CloseAction());
            put(ActionType.NEW_MESSAGE, new NewMessageAction());
            put(ActionType.CHANGE_THEME, new ChangeThemeAction());
            put(ActionType.FULL_SCREEN, new FullScreenAction());
        }
    };
    private final Logger logger = Logger.getLogger(ActionFactory.class);

    IActionHandler create(ActionType type) throws ActionNotFoundException {
        logger.debug("Creating action: " + type);

        if (!MAPPER.containsKey(type)) {
            throw new ActionNotFoundException(type);
        }
        return MAPPER.get(type);
    }
}
