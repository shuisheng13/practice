package com.yhh.practice.spring.dlx;

import com.rabbitmq.client.*;
import com.yhh.practice.spring.common.Constant;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer1 {
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {

        try{
            Channel channel = Constant.getChanner();
            channel.exchangeDeclare(Constant.GET_DIRECT_BATCH_EXCHANGE_NAME_01, BuiltinExchangeType.DIRECT);
            //String queue = channel.queueDeclare().getQueue();
            String queue = Constant.QUEUE_REJECT;
            channel.queueDeclare(queue,true,false,false,null);
            channel.queueBind(queue,Constant.GET_DIRECT_BATCH_EXCHANGE_NAME_01,"error");
            System.out.println(" [#] Waiting for messages:");
            final Consumer consunmer1 = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body);
                    System.out.println("消费者收到消息 message "+"["+message+"]+  路由 "+"["+envelope.getRoutingKey()+"]"
                            +" 交换器 ["+envelope.getExchange()+"]"+" 消费次数 ["+atomicInteger.incrementAndGet()+"]"
                            +" 消费者[ consunmer1 ]  消息ID ["+envelope.getDeliveryTag()+"]");
                    //单条确认
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            };
            //信道绑定队列和消费者
            //该消费者一次性拉取150条消息
            //channel.basicQos(10,false);
            channel.basicConsume(queue,false,consunmer1);
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }

    }
}
