package com.yhh.practice.spring.dlx;

import com.rabbitmq.client.*;
import com.yhh.practice.spring.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class Produce {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);
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
            channel.exchangeDeclare(Constant.DLX_FAOUNT_EXCHANGE, BuiltinExchangeType.FANOUT);
//            String queueName = Constant.QUEUE_REJECT;
//            /*日志消息级别，作为路由键使用*/
//            String serveritie = "error";
//            channel.queueBind(queueName,Constant.GET_EXCHANGE_NAME_01,serveritie);


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

            String[] serverities = {"error","info","warning"};
            for(int i=0;i<3;i++){
                String severity = serverities[i%3];
                // 发送的消息
                String message = "Hello World_"+(i+1);
                //参数1：exchange name
                //参数2：routing key
                channel.basicPublish(Constant.DLX_FAOUNT_EXCHANGE, severity,true,
                        null, message.getBytes());
//                System.out.println("发送者发送消息 message "+"["+message+"]+  路由 "+"["+severity+"]"
//                        +" 发送次数 ["+atomicInteger.incrementAndGet()+"]");
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
