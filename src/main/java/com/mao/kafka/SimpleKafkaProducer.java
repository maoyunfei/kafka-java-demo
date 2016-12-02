package com.mao.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by mao on 2016/12/02.
 */

public class SimpleKafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleKafkaProducer.class);

    private void execMsgSend() {
        Properties props = new Properties();
        props.put("metadata.broker.list", "127.0.0.1:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "1");

        ProducerConfig config = new ProducerConfig(props);

        System.out.println("set config info(" + config + ") ok.");

        Producer<String, String> procuder = new Producer<>(config);

        String topic = "mytopic";
        for (int i = 1; i <= 10; i++) {
            String value = "value_" + i;
            KeyedMessage<String, String> msg = new KeyedMessage<String, String>(topic, value);
            procuder.send(msg);
        }
        System.out.println("send message over.");

        procuder.close();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        SimpleKafkaProducer simpleProducer = new SimpleKafkaProducer();
        simpleProducer.execMsgSend();
    }
}
