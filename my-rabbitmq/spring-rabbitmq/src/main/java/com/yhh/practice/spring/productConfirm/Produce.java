package com.yhh.practice.spring.productConfirm;

import com.rabbitmq.client.*;
import com.yhh.practice.spring.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * 发送者确认(单条确认)
 */
public class Produce {

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
            channel.confirmSelect();

            /*日志消息级别，作为路由键使用*/
            String[] serverities = {"error", "info", "warning"};
            for (int i = 0; i < 100; i++) {
                String severity = serverities[i % 3];//每一次发送一条不同严重性的日志
                // 发送的消息
                String message = "Hello World_" + (i + 1);
                //参数1：exchange name
                //参数2：routing key
                channel.basicPublish(Constant.DIRECT_ACK_CONFIRM_TEST_01, "error",true,
                        null, message.getBytes());
//                System.out.println(" [x] Sent '" + severity + "':'"
//                        + message + "'");
                if(channel.waitForConfirms()){
                    System.out.println("send success  atomicInteger "+sccatomicInteger.incrementAndGet());
                }else{
                    System.out.println("send failure atomicInteger "+erratomicInteger.incrementAndGet());
                }
            }
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
