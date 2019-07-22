package com.yhh.practice.spring.productConfirm;

import com.rabbitmq.client.*;
import com.yhh.practice.spring.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class ProduceBathConfirm {

    private static AtomicInteger sccatomicInteger = new AtomicInteger(0);
    private static AtomicInteger erratomicInteger = new AtomicInteger(0);
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
            channel.exchangeDeclare(Constant.DIRECT_CONFIRM_TEST, BuiltinExchangeType.DIRECT);
            channel.addReturnListener(new ReturnListener() {
                public void handleReturn(int replyCode, String replyText,
                                         String exchange, String routingKey,
                                         AMQP.BasicProperties properties,
                                         byte[] body)
                        throws IOException {
                    String message = new String(body);
                    System.out.println("RabbitMq返回的replyCode:  "+replyCode);
                    System.out.println("RabbitMq返回的replyText:  "+replyText);
                    System.out.println("RabbitMq返回的exchange:  "+exchange);
                    System.out.println("RabbitMq返回的routingKey:  "+routingKey);
                    System.out.println("RabbitMq返回的message:  "+message);
                }
            });
            channel.confirmSelect();

            /*日志消息级别，作为路由键使用*/
            String[] serverities = {"error", "info", "warning"};
            for (int i = 0; i < 100; i++) {
                String severity = serverities[i % 3];//每一次发送一条不同严重性的日志

                // 发送的消息
                String message = "Hello World_" + (i + 1);
                //参数1：exchange name
                //参数2：routing key
                channel.basicPublish(Constant.DIRECT_CONFIRM_TEST, "error",true,
                        null, message.getBytes());
//                System.out.println(" [x] Sent '" + severity + "':'"
//                        + message + "'");
            }
            //批量确认
            channel.waitForConfirmsOrDie();
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
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
