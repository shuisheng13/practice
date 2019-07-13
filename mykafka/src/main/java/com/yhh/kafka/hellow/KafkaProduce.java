package com.yhh.kafka.hellow;

import com.yhh.kafka.config.Configs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

public class KafkaProduce {

    public static void shisheng910(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers","47.107.185.140:9092");
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String,String> produce =null;
        try{
            produce = new KafkaProducer<String,String>(properties);
            ProducerRecord<String,String> producerRecord;
            producerRecord = new ProducerRecord<String, String>(Configs.ZERO,"test0","lison");
            Future<RecordMetadata> res = produce.send(producerRecord);
            if(null!=res.get()){
                RecordMetadata recordMetadata = res.get();
                if(null!=recordMetadata){
                    System.out.println(String.format("偏移量：%s,分区：%s",
                            recordMetadata.offset(),
                            recordMetadata.partition()));
                }
            }
        }catch (Exception e){
           e.printStackTrace();
        }finally {
            produce.close();
        }



    }
}
