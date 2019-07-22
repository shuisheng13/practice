package com.yhh.practice.spring.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.yhh.practice.spring.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Produce {

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
            channel.exchangeDeclare(Constant.FANOUT_EXCHANGE_TEST, BuiltinExchangeType.FANOUT);
            /*日志消息级别，作为路由键使用*/
            String[] serverities = {"error","info","warning"};
            for(int i=0;i<3;i++){
                String severity = serverities[i%3];//每一次发送一条不同严重性的日志

                // 发送的消息
                String message = "Hello World_"+(i+1);
                //参数1：exchange name
                //参数2：routing key
                channel.basicPublish(Constant.FANOUT_EXCHANGE_TEST, severity,
                        null, message.getBytes());
                System.out.println(" [x] Sent '" + severity +"':'"
                        + message + "'");
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
