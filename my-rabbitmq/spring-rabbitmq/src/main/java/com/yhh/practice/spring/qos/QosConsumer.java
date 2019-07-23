package com.yhh.practice.spring.qos;

import com.rabbitmq.client.*;
import com.yhh.practice.spring.common.Constant;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * Qos 单条确认
 */
public class QosConsumer {
    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    public static void main(String[] args) {

        try{
            Channel channel = Constant.getChanner();
            channel.exchangeDeclare(Constant.TOPIC_EXCHANGE_TEST, BuiltinExchangeType.TOPIC);
            String queue = channel.queueDeclare().getQueue();
            channel.queueBind(queue,Constant.TOPIC_EXCHANGE_TEST,"#");
            System.out.println(" [#] Waiting for messages:");
            final Consumer consunmer1 = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body);
                    System.out.println("消费者收到消息 message "+"["+message+"]+  路由 "+"["+envelope.getRoutingKey()+"]"
                            +" 交换器 ["+envelope.getExchange()+"]"+" 消费次数 ["+atomicInteger.incrementAndGet()+"]");
                    //单条确认
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            };
            //信道绑定队列和消费者
            //该消费者一次性拉取150条消息
            channel.basicQos(150,true);

        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }

    }
}
