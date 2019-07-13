package com.yhh.kafka.hellow;

import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.clients.producer.KafkaProducer;

public class KafkaProducerTest {
    public static void main(String[] args){
        Properties props = new Properties();
        props.put("bootstrap.servers", "47.107.185.140:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer producer = new KafkaProducer(props);
        for (int i = 0; i < 100; i++) {
            Future result =  producer.send(new ProducerRecord("data", Integer.toString(i),Integer.toString(i)));
            producer.flush();
        }
        producer.close();

    }

}