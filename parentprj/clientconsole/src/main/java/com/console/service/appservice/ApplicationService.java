package com.console.service.appservice;

import com.console.App;
import com.console.domain.*;
import com.console.service.IService;
import com.console.service.backend.ThreadBackendService;

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
 * @author fabry
 */
public class ApplicationService {

    private final Logger logger = Logger.getLogger(ApplicationService.class);

    private final List<AppState> oldStates = new ArrayList<>();
    private final AppState currentState = new AppState.Builder(new AppEventManager()).build();
    private final ActionFactory factory = new ActionFactory();
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

    public App getMainApp() {
        return mainApp;
    }

    public void dispatch(final AppAction<ActionType, Object> action) {
        logger.debug("New action: " + action);
        final ApplicationService self = this;

        // Use Platform.runLater(...) for quick and simple operations and 
        // Task for complex and big operations .
        Platform.runLater(() -> {
            try {

                AppState oldState = null;//currentState.clone();
                IActionHandler handler = factory.create(action.type);

                handler.execute(action, self, new ICallback() {
                    @Override
                    public void success(Object obj) {
                        logger.debug("Action executed");
                        //fireAppStateChange(oldState);
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

    public void stopAllServices() {

        services.entrySet().stream().map((entry) -> {
            logger.debug("Stopping service: " + entry.getKey().toString());
            return entry;
        }).forEach((entry) -> {
            ((IService) entry.getValue()).stop();
        });

    }

    private void initServices() {
        this.services.put(ServiceName.BACKEND, new ThreadBackendService(this));

    }

}
