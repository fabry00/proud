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

    /**
     * Get a propery file not packed withn the jar
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
