package com.proud.commons;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.util.Properties;
import java.util.TimeZone;

/**
 * Helper for all applications class
 * Created by Fabrizio Fausitnoni on 03/10/2016.
 */
public class AppHelper {
    private static final String LOG_CONF = "log4j.properties";

    private final Logger logger = Logger.getLogger(AppHelper.class);

    public void initLogger() {
        File log4jfile = new File(LOG_CONF);
        if (!log4jfile.exists()) {
            System.err.println(LOG_CONF);
        }
        PropertyConfigurator.configure(log4jfile.getAbsolutePath());
    }

    public void forceTimeZone(Properties appConfigs) {
        Boolean forceTimezone = Boolean.parseBoolean(appConfigs.getProperty(ConfigUtils.KEY_FORCE_TIME_ZONE,
                ConfigUtils.DEFAULT_FORCE_TIME_ZONE));

        if (!forceTimezone) {
            logger.debug("Forcing timezone disabled");
            return;
        }
        String timeZone = appConfigs.getProperty(ConfigUtils.KEY_TIME_ZONE, ConfigUtils.DEFAULT_TIME_ZONE);
        logger.info("TimeZone set to: "+timeZone);
        System.setProperty("user.timezone", timeZone);

        TimeZone.setDefault(TimeZone.getTimeZone(System.getProperty("user.timezone")));
    }

    public String getJRE(){
        return System.getProperty("java.version");
    }

    public String getJVM(){
        return System.getProperty("java.vm.name");
    }
}
