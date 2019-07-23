package com.yhh.practice.spring.qos;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.yhh.practice.spring.common.Constant;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * Qos 批量确认
 */
public class QosBatchConsumer  extends DefaultConsumer {
    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    public QosBatchConsumer(Channel channel) {
        super(channel);
    }

    /***
     * 继承父类方法
     * @param consumerTag
     * @param envelope
     * @param properties
     * @param body
     * @throws IOException
     */
    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        atomicInteger.incrementAndGet();
        String message = new String(body);
        System.out.println("消费者收到消息 message "+"["+message+"]+  路由 "+"["+envelope.getRoutingKey()+"]"
                +" 交换器 ["+envelope.getExchange()+"]"+" 消费次数 ["+atomicInteger.incrementAndGet()+"]");
        //批量确认,每50条确认一次
            if(atomicInteger.get()==envelope.getDeliveryTag()){
                this.getChannel().basicAck(envelope.getDeliveryTag(),false);
                System.out.println("批量消息确认["+envelope.getDeliveryTag()+"]");
            }
        //channel.basicAck(envelope.getDeliveryTag(),false);
    }


}
