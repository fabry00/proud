package com.mycompany.nodeproducer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.annotation.PostConstruct;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

public class MyKafkaProducer {

    private final Logger logger = Logger.getLogger(MyKafkaProducer.class);
    private final Properties conf;
    private KafkaProducer producer;

    MyKafkaProducer(Properties producerProp) {
        this.conf = producerProp;
        init();
    }

    public void init() {
        // Add some configurations
        this.conf.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        this.conf.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<>(conf);

    }

    public void send(Message message) {

        ProducerRecord<String, String> rec
                = new ProducerRecord<String, String>(message.topic, message.msg);
        producer.send(rec);
    }

}
