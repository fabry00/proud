package com.mycompany.commons;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author user
 */
public class ConfigUtils {

    private final Logger logger = Logger.getLogger(ConfigUtils.class);

    public Properties getProp(String propFile) {
        File confFile = new File(propFile);
        if (!confFile.exists()) {
            logger.error("file not found: " + propFile);

        }
        Properties configProperties = new Properties();
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(propFile)) {
            configProperties.load(in);
        } catch (IOException ex) {
            logger.error(ex);
        }
        return configProperties;
    }
}
