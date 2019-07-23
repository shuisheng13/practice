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
    private static AtomicInteger returnInteger = new AtomicInteger(0);

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
            channel.exchangeDeclare(Constant.DIRECT_ACK_CONFIRM_TEST_01, BuiltinExchangeType.DIRECT);
            //开启确认模式
            channel.confirmSelect();
            /***
             * 异步确认回调
             */
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println("【handleAck】 deliveryTag:" + deliveryTag
                            + ",multiple:" + multiple+"【ack次数】"+sccatomicInteger.incrementAndGet());
                    //if(100 == deliveryTag){
                        try {
                            Thread.sleep(1*1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    //}
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println("【handleNack】 deliveryTag:" + deliveryTag
                            + ",multiple:" + multiple+"【nack次数】"+erratomicInteger.incrementAndGet());
                }
            });

            channel.addReturnListener(new ReturnListener() {
                public void handleReturn(int replyCode, String replyText,
                                         String exchange, String routingKey,
                                         AMQP.BasicProperties properties,
                                         byte[] body)
                        throws IOException {
                    String message = new String(body);
                    System.out.println("失败确认的replyCode:【  "+replyCode+"】 " +
                            "失败确认的replyText:【"+replyText+"】"+
                            "失败确认的exchange :【"+exchange+"】"+
                            "失败确认的routingKey:  【"+routingKey+"】"+
                            "失败确认的message : 【"+message+"】"+
                            "失败确认次数 count 【"+returnInteger.incrementAndGet()+"】");
                }
            });
            String[] severities = {"error", "warning"};
            for (int i = 0; i < 1000; i++) {
                String severity = severities[i % 2];
                // 发送的消息
                String message = "Hello World_" + (i + 1) + ("_" + System.currentTimeMillis());
                channel.basicPublish(Constant.DIRECT_ACK_CONFIRM_TEST_01, severity, false,
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