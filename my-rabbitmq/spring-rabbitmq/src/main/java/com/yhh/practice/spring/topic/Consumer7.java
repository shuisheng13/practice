package com.yhh.practice.spring.topic;

import com.rabbitmq.client.*;
import com.yhh.practice.spring.common.Constant;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer7 {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constant.ADRESS);
        factory.setUsername(Constant.USERNAME);
        factory.setPassword(Constant.PASSWORD);
        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(Constant.TOPIC_EXCHANGE_TEST, BuiltinExchangeType.TOPIC);
            String queue = channel.queueDeclare().getQueue();
            String service ="#.C";
            channel.queueBind(queue,Constant.TOPIC_EXCHANGE_TEST,service);
            System.out.println(" [#] Waiting for messages:");
            final Consumer consunmer1 = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body);
                    System.out.println("消费者收到消息 message "+"["+message+"]+  路由 "+"["+envelope.getRoutingKey()+"]"
                            +" 交换器 ["+envelope.getExchange()+"]"+" 消费次数 ["+atomicInteger.incrementAndGet()+"]");
                }
            };
            //信道绑定队列和消费者
            channel.basicConsume(queue,true,consunmer1);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            try {
//                channel.close();
//                connection.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (TimeoutException e) {
//                e.printStackTrace();
//            }
        }
    }
}
