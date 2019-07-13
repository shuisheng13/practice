package com.yhh.kafka.hellow;

import com.yhh.kafka.config.Configs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

public class KafkaProduceList {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers","47.107.185.140:9092");
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String,String> produce =null;
        try{
            produce = new KafkaProducer<String,String>(properties);
            ProducerRecord<String,String> producerRecord;
            for (int i = 0; i <20000000 ; i++) {
                producerRecord = new ProducerRecord<String, String>(Configs.DATE,"test0964554","lison" +
                        "lison" +
                        "lison" +
                        "lisonlison" +
                        "lison" +
                        "lison" +
                        "lisonlisonlisonlisonlisonlisonlison" +
                        "lisonlisonlisonlisonlisonlisonlison" +
                        "lison"+i);
                Future<RecordMetadata> res = produce.send(producerRecord);
                if(null!=res.get()){
                    RecordMetadata data = res.get();
                    RecordMetadata recordMetadata = res.get();
                    if(null!=recordMetadata){
                        System.out.println(String.format("偏移量：%s,分区：%s",
                                recordMetadata.offset(),
                                recordMetadata.partition()));
                    }
                }
            }


        }catch (Exception e){
           e.printStackTrace();
        }finally {
            produce.close();
        }



    }
}
