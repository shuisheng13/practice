package com.yhh.practice.spring.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.yhh.practice.spring.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/***
 * topic 模式的交换器
 * 订阅过该交换器的路由都能收到消息
 */
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
            channel.exchangeDeclare(Constant.TOPIC_EXCHANGE_TEST, BuiltinExchangeType.TOPIC);
            /*日志消息，路由键最终格式类似于：info.order.B*/
            String[] severities={"error","info","warning"};
            for(int i=0;i<3;i++){
                String[]  modules={"user","order","email"};
                for(int j=0;j<3;j++){
                    String[]  servers={"A","B","C"};
                    for(int k=0;k<3;k++){
                        // 发送的消息
                        String message = "Hello Topic_["+i+","+j+","+k+"]";
                        String routeKey = severities[i%3]+"."+modules[j%3]
                                +"."+servers[k%3];
                        channel.basicPublish(Constant.TOPIC_EXCHANGE_TEST,routeKey,
                                null, message.getBytes());
                        System.out.println(" [x] Sent '" + routeKey +":'"
                                + message + "'");
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
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
