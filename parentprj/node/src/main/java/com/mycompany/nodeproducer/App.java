package com.mycompany.nodeproducer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
    private static final int SCHEDULE_EVERY = 5; //seconds
    
    private final Logger logger = Logger.getLogger(App.class);
    private ScheduledExecutorService executor = null;

    public static void main(String[] args) throws InterruptedException {

        new App().start();

        System.out.println("Type \"exit\" to stop the application");

        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        while (!line.equals("exit")) {
            line = in.nextLine();
        }
    }

    public void start() throws InterruptedException {
        initLogger();
        Properties producerProp = getProducerProperties();
        Properties nodeProperties = getNodeProperties();
        MyKafkaProducer producer = new MyKafkaProducer(producerProp);
        ResourceReader resourceReader = new ResourceReader(nodeProperties, producer);

        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(resourceReader, INITIAL_SLEEP, SCHEDULE_EVERY, TimeUnit.SECONDS);
    }

    private void initLogger() {
        File log4jfile = new File(LOG_CONF);
        if (!log4jfile.exists()) {
            System.err.println(LOG_CONF);
        }
        PropertyConfigurator.configure(log4jfile.getAbsolutePath());
    }

    private Properties getNodeProperties() {
        File confFile = new File(NODE_CONF);
        if (!confFile.exists()) {
            System.err.println(NODE_CONF);
        }
        try {
            //Configure the Producer from external file
            Properties configProperties = new Properties();
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(NODE_CONF);
            configProperties.load(in);
            in.close();
            return configProperties;
        } catch (IOException ex) {
            logger.error(ex);
        }
        return null;
    }
    
    private Properties getProducerProperties() {
        File confFile = new File(PRODUCER_CONF);
        if (!confFile.exists()) {
            System.err.println(PRODUCER_CONF);
        }
        try {
            //Configure the Producer from external file
            Properties configProperties = new Properties();
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(PRODUCER_CONF);
            configProperties.load(in);
            in.close();
            return configProperties;
        } catch (IOException ex) {
            logger.error(ex);
        }
        return null;

    }
}
