package com.yhh.practice.spring.productConfirm;

import com.rabbitmq.client.*;
import com.yhh.practice.spring.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * 发送者异步确认模式
 */
public class ProduceSyncConfirm {

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
            //开启确认模式
            channel.confirmSelect();
            /***
             * 异步确认回调
             */
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println("【handleAck】 deliveryTag:" + deliveryTag
                            + ",multiple:" + multiple);
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println("【handleNack】 deliveryTag:" + deliveryTag
                            + ",multiple:" + multiple);
                }
            });

            channel.addReturnListener(new ReturnListener() {
                public void handleReturn(int replyCode, String replyText,
                                         String exchange, String routingKey,
                                         AMQP.BasicProperties properties,
                                         byte[] body)
                        throws IOException {
                    String message = new String(body);
                    System.out.println("RabbitMq返回的replyCode:  " + replyCode);
                    System.out.println("RabbitMq返回的replyText:  " + replyText);
                    System.out.println("RabbitMq返回的exchange:  " + exchange);
                    System.out.println("RabbitMq返回的routingKey:  " + routingKey);
                    System.out.println("RabbitMq返回的message:  " + message);
                }
            });
            String[] severities = {"error", "warning"};
            for (int i = 0; i < 100; i++) {
                String severity = severities[i % 2];
                // 发送的消息
                String message = "Hello World_" + (i + 1) + ("_" + System.currentTimeMillis());
                channel.basicPublish(Constant.DIRECT_CONFIRM_TEST, severity, true,
                        MessageProperties.PERSISTENT_BASIC, message.getBytes());
                System.out.println("----------------------------------------------------");
                System.out.println(" Sent Message: [" + severity + "]:'" + message + "'");
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}