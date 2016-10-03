package com.proud.server;

import com.proud.server.resource.ResourceHelper;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

/**
 * Application Server
 * Created by Fabrizio Faustinoni on 03/10/2016.
 */
@SpringBootApplication
public class Server {
    private final Logger logger = Logger.getLogger(Server.class);

    public void startServer(String[] args, Properties appConfigs) {
        logger.debug("Starting server");

        ResourceHelper.setAppProperties(appConfigs);
        SpringApplication.run(Server.class,  args);
    }
}
