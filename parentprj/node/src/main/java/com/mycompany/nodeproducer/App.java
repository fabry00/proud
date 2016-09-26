package com.mycompany.nodeproducer;

import com.mycompany.commons.ConfigUtils;
import java.io.File;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author fabry
 */
public class App {

    private static final String LOG_CONF = "log4j.properties";
    private static final String NODE_CONF = "node.properties";
    private static final String PRODUCER_CONF = "producer.properties";
    private static final int INITIAL_SLEEP = 3; //seconds
    private static final int SCHEDULE_EVERY = 10; //seconds

    private final Logger logger = Logger.getLogger(App.class);
    private ScheduledExecutorService executor = null;

    public static void main(String[] args) throws InterruptedException {

        new App().start();
        String javaLibPath = System.getProperty("java.library.path");
        System.out.println("The java library path is: " + javaLibPath);
        System.out.println("Type \"exit\" to stop the application");

        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        while (!line.equals("exit")) {
            line = in.nextLine();
        }
    }

    public void start() throws InterruptedException {
        initLogger();
        ConfigUtils utils = new ConfigUtils();
        Properties producerProp = utils.getExtenralProp(PRODUCER_CONF);
        Properties nodeProperties = utils.getExtenralProp(NODE_CONF);
        MsgProducer producer = new MsgProducer(producerProp);
        ResourceReader resourceReader = new ResourceReader(nodeProperties, producer);

        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(resourceReader, INITIAL_SLEEP, SCHEDULE_EVERY, TimeUnit.SECONDS);
    }

    private void initLogger() {
        File log4jfile = new File(LOG_CONF);
        if (!log4jfile.exists()) {
            logger.error("file not found: " + LOG_CONF);
        }
        PropertyConfigurator.configure(log4jfile.getAbsolutePath());
    }
}
