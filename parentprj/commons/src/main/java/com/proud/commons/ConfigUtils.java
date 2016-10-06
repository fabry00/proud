package com.proud.commons;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author user
 */
public class ConfigUtils {

    private final Logger logger = Logger.getLogger(ConfigUtils.class);

    public static final String APP_CONFIGS = "appconfigurations.properties";
    public static final String KEY_VERSION = "app.version";
    public static final String KEY_FORCE_TIME_ZONE = "timezone.forceTimeZone";
    public static final String KEY_TIME_ZONE = "timezone.zone";
    public static final String DEFAULT_FORCE_TIME_ZONE = "false";
    public static final String DEFAULT_TIME_ZONE = "UTC";

    /**
     * Get a propery file not packed within the jar
     *
     * @param propFile
     * @return
     */
    public Properties getExtenralProp(String propFile) {
        File confFile = new File(propFile);
        if (!confFile.exists()) {
            logger.error("file not found: " + propFile);
            return null;

        }
        Properties configProperties = new Properties();
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(propFile)) {
            configProperties.load(in);
        } catch (IOException ex) {
            logger.error(ex);
        }
        return configProperties;
    }

    /**
     * Get a properties file packed within the jar
     *
     * @param loader
     * @param propFile
     * @return
     */
    public Properties getInternalProp(ClassLoader loader, String propFile) {
        Properties configProperties = new Properties();
        try (InputStream in = loader.getResourceAsStream(propFile)) {
            configProperties.load(in);
        } catch (IOException ex) {
            logger.error(ex);
        }
        return configProperties;
    }
}
