package com.yhh.practice.spring.getMessage;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import com.yhh.practice.spring.common.Constant;

import java.util.concurrent.atomic.AtomicInteger;

public class Consumer3 {
    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    public static void main(String[] args) {

        try{
            Channel channel = Constant.getChanner();
            channel.exchangeDeclare(Constant.GET_FACTION_EXCHANGE_NAME_01,
                    BuiltinExchangeType.FANOUT);
            String queueName = Constant.QUEUE_GET;
            channel.queueDeclare(queueName,
                    false,false,
                    false,null);
            String severity="info";
            channel.queueBind(queueName,
                    Constant.GET_FACTION_EXCHANGE_NAME_01, severity);
            System.out.println(" [*] Waiting for messages......");
            /***
             *  自旋方式去获取消息
             */
            while (true){
                GetResponse getResponse = channel.basicGet(queueName, true);
                if(null!=getResponse){
                    System.out.println("received["
                            +getResponse.getEnvelope().getRoutingKey()+"]"
                            +new String(getResponse.getBody()));
                }
                System.out.println("消费者消费次数 【"+atomicInteger.incrementAndGet()+"】");
                Thread.sleep(100);

            }

        }catch (Exception e){

        }

    }

}
