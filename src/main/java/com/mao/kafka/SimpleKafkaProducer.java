package com.mao.kafka;

import kafka.producer.ProducerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
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

        KafkaProducer<String,String> producer = new KafkaProducer<String, String>(props);         //new api
        //Producer<String, String> producer = new Producer<>(config);                            //old api

        String topic = "mytopic";
        for (int i = 1; i <= 10; i++) {
            String value = "value_" + i;
            ProducerRecord<String,String> msg=new ProducerRecord<String,String>(topic,value);       //new api
            //KeyedMessage<String, String> msg = new KeyedMessage<String, String>(topic, value);   //old api
            producer.send(msg);
        }
        System.out.println("send message over.");

        producer.close();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        SimpleKafkaProducer simpleProducer = new SimpleKafkaProducer();
        simpleProducer.execMsgSend();
    }
}
