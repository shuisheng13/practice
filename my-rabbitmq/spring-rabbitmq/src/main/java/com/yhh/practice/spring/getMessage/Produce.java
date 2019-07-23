package com.yhh.practice.spring.getMessage;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.yhh.practice.spring.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class Produce {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    public static void main(String[] args) {

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(Constant.ADRESS);
            factory.setUsername(Constant.USERNAME);
            factory.setPassword(Constant.PASSWORD);
            Connection connection = null;
            Channel channel = null;
            try {
                //创建连接
                connection = factory.newConnection();
                //创建信道
                channel = connection.createChannel();
                //绑定fanout类型的交换器
                channel.exchangeDeclare(Constant.GET_FACTION_EXCHANGE_NAME_03, BuiltinExchangeType.DIRECT);
                /*日志消息级别，作为路由键使用*/
                String serveritie = "info";
                for(int i=0;i<3000;i++){

                    // 发送的消息
                    String message = "Hello World_"+(i+1);
                    //参数1：exchange name
                    //参数2：routing key
                    channel.basicPublish(Constant.GET_FACTION_EXCHANGE_NAME_03, serveritie,
                            null, message.getBytes());
                    System.out.println("发送者发送消息 message "+"["+message+"]+  路由 "+"["+serveritie+"]"
                            +" 发送次数 ["+atomicInteger.incrementAndGet()+"]");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    channel.close();
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        }
}
