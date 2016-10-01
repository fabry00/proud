package com.console.service.appservice;

import com.console.domain.ActionType;

/**
 *
 * @author Fabrizio Faustinoni
 */
class ActionNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    ActionNotFoundException(ActionType action) {
        super("ActionNotFound: " + action.toString());
    }
}
