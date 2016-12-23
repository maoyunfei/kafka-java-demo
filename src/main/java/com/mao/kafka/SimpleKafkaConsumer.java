package com.mao.kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by mao on 2016/12/02.
 */

public class SimpleKafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleKafkaConsumer.class);

    private void execMsgConsume() {
        Properties props = new Properties();
        props.put("zookeeper.connect", "127.0.0.1:2181");
        props.put("group.id", "group-A");
        props.put("auto.offset.reset","largest");

        ConsumerConfig config = new ConsumerConfig(props);
        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(config);

        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put("topic-1", 1);
        Map<String, List<KafkaStream<byte[], byte[]>>> streamMap = consumer.createMessageStreams(topicCountMap);
        KafkaStream<byte[], byte[]> kafkaStream = streamMap.get("topic-1").get(0);
        ConsumerIterator<byte[], byte[]> iterator = kafkaStream.iterator();
        while(iterator.hasNext()){
            MessageAndMetadata<byte[], byte[]> next = iterator.next();
            String message=new String(next.message(), Charset.forName("utf-8"));
            System.out.println(next.partition()+"--->"+next.offset()+"-->" + message);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        SimpleKafkaConsumer simpleConsumer = new SimpleKafkaConsumer();
        simpleConsumer.execMsgConsume();
    }
}
