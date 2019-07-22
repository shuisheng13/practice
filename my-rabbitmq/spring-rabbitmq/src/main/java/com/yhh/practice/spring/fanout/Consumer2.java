package com.yhh.practice.spring.fanout;

import com.rabbitmq.client.*;
import com.yhh.practice.spring.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * 测试其它路由能够接受到fanout模式消息
 */
public class Consumer2 {
    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constant.ADRESS);
        factory.setUsername(Constant.USERNAME);
        factory.setPassword(Constant.PASSWORD);
        Connection connection = null;
        Channel channel = null;
        try{
            connection = factory.newConnection();
            channel = connection.createChannel();
            //信道绑定到交换器
            channel.exchangeDeclare(Constant.FANOUT_EXCHANGE_TEST, BuiltinExchangeType.FANOUT);
            //获取随机队列
            String queueName = channel.queueDeclare().getQueue();
            //其它路由
            String severities="yhh";
            channel.queueBind(queueName,Constant.FANOUT_EXCHANGE_TEST,severities);
            System.out.println("[路由 : test ] Waiting for messages:");


            final Consumer consunmer2 = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body);
                    System.out.println("消费者收到消息 message "+"["+message+"]+  路由 "+"["+envelope.getRoutingKey()+"]"
                            +" 交换器 ["+envelope.getExchange()+"]"+"  接受消息次数 ： "+ atomicInteger.incrementAndGet());
                }
            };
            channel.basicConsume(queueName,true,consunmer2);
        }catch (Exception e){
                e.printStackTrace();
        }finally {
            try {
                channel.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }

        }

    }

}
