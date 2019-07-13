package com.yhh.kafka.hellow;

import com.yhh.kafka.config.Configs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import javax.naming.ldap.PagedResultsControl;
import java.util.Collections;
import java.util.Properties;

public class KafkaCoumser {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers","47.107.185.140:9092");
        properties.put("key.deserializer",
                StringDeserializer.class);
        properties.put("value.deserializer",
                StringDeserializer.class);
        KafkaConsumer<String,String> kafkaCoumser = new KafkaConsumer<String,String>(properties);

        try{
            kafkaCoumser.subscribe(Collections.singletonList(Configs.DATE));
            while (true){
                ConsumerRecords<String,String> records =  kafkaCoumser.poll(500);
                for(ConsumerRecord<String,String> record : records){
                    System.out.println(String.format("topic:%s,分区：%d,偏移量：%d," +
                                    "key:%s,value:%s",record.topic(),record.partition(),
                            record.offset(),record.key(),record.value()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            kafkaCoumser.close();
        }





    }
}
