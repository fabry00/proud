package com.mycompany.nodeproducer;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

public class MsgProducer {

    private final Logger logger = Logger.getLogger(MsgProducer.class);
    private final Properties conf;
    private KafkaProducer producer;

    MsgProducer(Properties producerProp) {
        logger.debug("Starting produced");
        this.conf = producerProp;
        init();
    }

    public final void init() {
        // Add some configurations
        this.conf.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        this.conf.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<>(conf);

    }

    public void send(Message message) {

        ProducerRecord<String, String> rec
                = new ProducerRecord<>(message.topic, message.msg);
        producer.send(rec);
    }

}
