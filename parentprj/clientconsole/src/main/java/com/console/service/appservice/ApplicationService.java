package com.console.service.appservice;

import com.console.App;
import com.console.domain.AppAction;
import com.console.domain.ActionType;
import com.console.domain.AppState;
import com.console.domain.IAppStateListener;
import com.console.domain.ServiceName;
import com.console.domain.State;
import com.console.service.IService;
import com.console.service.backend.ThreadBackendService;
import com.console.domain.ICallback;
import com.mycompany.commons.ConfigUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javafx.application.Platform;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class ApplicationService {

    private final Logger logger = Logger.getLogger(ApplicationService.class);

    private final List<AppState> oldStates = new ArrayList<>();
    private final AppState currentState = new AppState.Builder().build();
    private final ActionFactory factory = new ActionFactory();

    private final Set<IAppStateListener> listeners = new HashSet<>();
    private final Map<State, Set<IAppStateListener>> listenersToState = new HashMap<>();

    private final Map<ServiceName, Object> services = new HashMap<>();

    private static App mainApp;

    public static void setMainApp(App app) {
        mainApp = app;
    }

    @PostConstruct
    public void init() {
        logger.debug("init");
        currentState.setState(State.NOT_STARTED);
    }

    public AppState getCurrentState() {
        return currentState;
    }

    public Properties getAppConfigs() {
        return mainApp.getAppConfigs();
    }

    public void dispatch(final AppAction<ActionType, Object> action) {
        logger.debug("New action: " + action);
        final ApplicationService self = this;

        // Use Platform.runLater(...) for quick and simple operations and 
        // Task for complex and big operations .
        Platform.runLater(() -> {
            try {
                AppState oldState = currentState.clone();
                IActionHandler handler = factory.create(action.type);

                handler.execute(action, self, new ICallback() {
                    @Override
                    public void success(Object obj) {
                        logger.debug("Action executed");
                        fireAppStateChange(oldState);
                    }

                    @Override
                    public void fail(String message) {
                        logger.error(message);
                    }
                });

            } catch (ActionNotFoundException ex) {
                logger.error(ex);
            }
        });

    }

    public Object getService(ServiceName serviceName) {
        if (!services.containsKey(serviceName)) {
            logger.error("Service not found: " + serviceName);
            return null;
        }

        return services.get(serviceName);
    }

    public void subscribe(IAppStateListener listener) {

        listeners.add(listener);
    }

    public void subscribeToState(IAppStateListener listener, State type) {

        Set<IAppStateListener> listemersForAState;
        if (!listenersToState.containsKey(type)) {
            listemersForAState = new HashSet<>();
            listenersToState.put(type, listemersForAState);
        } else {
            listemersForAState = listenersToState.get(type);
        }

        listemersForAState.add(listener);
    }

    /*public void unsubscribe(IAppStateListener listener) {
        listeners.remove(listener);
    }*/
    public void stopAllServices() {

        services.entrySet().stream().map((entry) -> {
            logger.debug("Stopping service: " + entry.getKey().toString());
            return entry;
        }).forEach((entry) -> {
            ((IService) entry.getValue()).stop();
        });

    }

    public App getMainApp() {
        return mainApp;
    }

    private void fireAppStateChange(AppState oldState) {
        logger.debug("fireAppStateChange");

        // TODO check if save oldAppState
        //oldStates.add(oldState);
        // Fire to whome is subscribed to all events
        listeners.stream().forEach((listener) -> Platform.runLater(() -> {
            logger.debug("fire state change to: " + listener.getClass().getSimpleName());
            listener.AppStateChanged(oldState, currentState);
        }));

        Set<IAppStateListener> specificListeners = listenersToState.get(currentState.getState());
        if (specificListeners == null) {
            return;
        }

        specificListeners.stream().forEach((listener) -> Platform.runLater(() -> {
            listener.AppStateChanged(oldState, currentState);
        }));

    }

    private void initServices() {
        this.services.put(ServiceName.BACKEND, new ThreadBackendService(this));

    }

}
