package com.yhh.practice.spring.mandatory;

import com.rabbitmq.client.*;
import com.yhh.practice.spring.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * 失败通知
 */
public class Produce {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    private static AtomicInteger error = new AtomicInteger(0);

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
            channel.exchangeDeclare(Constant.MANDATORY_TEST_01, BuiltinExchangeType.DIRECT);
            /***
             * 失败通知回调
             */
            //连接失败时通知
            connection.addShutdownListener(new ShutdownListener() {
                @Override
                public void shutdownCompleted(ShutdownSignalException e) {
                    System.out.println("发送端失败通知 ---------> connent连接关闭时通知 ["+e.getMessage()+"]");
                }
            });

            channel.addShutdownListener(new ShutdownListener() {
                @Override
                public void shutdownCompleted(ShutdownSignalException cause) {
                    System.out.println("发送端失败通知 ---------> channel连接关闭时通知 ["+cause.getMessage()+"]");
                }
            });
            channel.addReturnListener(new ReturnListener() {
                @Override
                public void handleReturn(int replyCode, String replyText,
                                         String exchange, String routingKey,
                                         AMQP.BasicProperties properties,
                                         byte[] body) throws IOException {
                    String message = new String(body);
                    System.out.println("返回的replyText ："+replyText);
                    System.out.println("返回的exchange ："+exchange);
                    System.out.println("返回的routingKey ："+routingKey);
                    System.out.println("返回的message ："+message);
                }
            });


            /*日志消息级别，作为路由键使用*/
            String[] serverities = {"error","info","warning","test"};
            for(int i=0;i<30000;i++){
                String severity = serverities[i%4];//每一次发送一条不同严重性的日志

                // 发送的消息
                String message = "Hello World_"+(i+1);
                //参数1：exchange name
                //参数2：routing key
                channel.basicPublish(Constant.MANDATORY_TEST_01, severity,true,
                        null, message.getBytes());
                System.out.println("发送端发送消息  路由["+severity+"] 消息["+message+"] 次数["+atomicInteger.incrementAndGet()+"]");
            }
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
