package com.yhh.practice.spring.fanout;

import com.rabbitmq.client.*;
import com.yhh.practice.spring.common.Constant;

import java.io.IOException;

public class Consumer1 {

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
            //所有日志严重性级别  路由
            String[] severities={"error","info","warning"};
            for (String severity : severities) {
                //信道绑定队列绑定交换器绑定路由
                channel.queueBind(queueName,Constant.FANOUT_EXCHANGE_TEST,severity);
            }
            System.out.println("[*] Waiting for messages:");
          final Consumer consunmer1 = new DefaultConsumer(channel) {
              @Override
              public void handleDelivery(String consumerTag, Envelope envelope,
                                         AMQP.BasicProperties properties, byte[] body)
                      throws IOException {
                String message = new String(body);
                  System.out.println("消费者收到消息 message "+"["+message+"]+  路由 "+"["+envelope.getRoutingKey()+"]"
                  +" 交换器 ["+envelope.getExchange()+"]");
              }
          };
          //信道绑定队列和消费者
          channel.basicConsume(queueName,true,consunmer1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
