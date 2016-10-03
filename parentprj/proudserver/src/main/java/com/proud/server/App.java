package com.proud.server;

import com.proud.commons.AppHelper;
import com.proud.commons.ConfigUtils;
import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * Application class
 * Created by Fabrizio Faustinoni on 02/10/2016.
 */


public class App {

    private static String[] ARGS;
    private final Logger logger = Logger.getLogger(App.class);
    private final AppHelper appHelper = new AppHelper();

    private Properties appConfigs;

    public static void main(String[] args) {
        App.ARGS = args;
        new App().start();
    }

    public App() {
        appHelper.initLogger();
    }

    public void start() {
        logger.debug("start");
        initConfiguration();
        appHelper.forceTimeZone(appConfigs);


        // Start the server
        new Server().startServer(App.ARGS, appConfigs);

    }

    private void initConfiguration() {
        logger.debug("initConfiguration");
        ConfigUtils utils = new ConfigUtils();
        appConfigs = utils.getInternalProp(App.class.getClassLoader(), ConfigUtils.APP_CONFIGS);
    }
}