package com.proud.console.service.appservice;

import com.proud.console.domain.AppEvent;
import com.proud.console.domain.IAppStateListener;
import javafx.application.Platform;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Fabrizio Faustinoni on 28/09/2016.
 */
public class AppEventManager {

    private final Logger logger = Logger.getLogger(AppEventManager.class);
    private final Map<AppEvent, Set<IAppStateListener>> listenersToEvent = new HashMap<>();

    public void subscribeToEvent(IAppStateListener listener, AppEvent event) {

        Set<IAppStateListener> listenersForAEvent;
        if (!listenersToEvent.containsKey(event)) {
            listenersForAEvent = new HashSet<>();
            listenersToEvent.put(event, listenersForAEvent);
        } else {
            listenersForAEvent = listenersToEvent.get(event);
        }

        listenersForAEvent.add(listener);
    }

    public void unsubscribe(IAppStateListener listener, AppEvent event) {
        listenersToEvent.get(event).remove(listener);
    }

    public void fireAppEvent(AppEvent event, Object param) {
        logger.debug("fireAppEvent: "+event.name());

        // TODO check if save oldAppState
        //oldStates.add(oldState);
        // Fire to whome is subscribed to all events
        Set<IAppStateListener> specificListeners = listenersToEvent.get(event);
        if (specificListeners == null) {
            return;
        }

        specificListeners.forEach((listener) -> Platform.runLater(() -> listener.AppEvent(event, param)));

    }
}
