package com.yhh.practice.spring.bakexchange;

import com.rabbitmq.client.*;
import com.yhh.practice.spring.common.Constant;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class Produce {
    private static AtomicInteger atomicInteger = new AtomicInteger(0);
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

            //备用交换器
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("alternate-exchange",Constant.BAK_EXCHANGE_NAME_02);
            //绑定主交换器
            channel.exchangeDeclare(Constant.MAIN_EXCHANGE_NAME, BuiltinExchangeType.DIRECT,false,false,map);
            channel.exchangeDeclare(Constant.BAK_EXCHANGE_NAME_02,BuiltinExchangeType.FANOUT,true,false,null);
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
            /*日志消息级别，作为路由键使用*/
            String[] serverities = {"error","info","warning"};
            for(int i=0;i<300;i++){
                String severity = serverities[i%3];//每一次发送一条不同严重性的日志

                // 发送的消息
                String message = "Hello World_"+(i+1);
                //参数1：exchange name
                //参数2：routing key
                channel.basicPublish(Constant.MAIN_EXCHANGE_NAME, severity,true,
                        null, message.getBytes());
                System.out.println(" 发送消息【路由】 '" + severity +"【消息】"
                        + message + "【次数】"+atomicInteger.incrementAndGet());
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
