package com.yhh.practice.spring.common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class Constant {

    public static final String ADRESS = "47.107.185.140";
    public static final String USERNAME ="yhh";
    public static final String PASSWORD = "yhh";
    public static final String FANOUT_EXCHANGE_TEST = "test_fonout_01";
    public static final String FANOUT_DIRECT_TEST = "test_direct_01";
    public static final String FANOUT_DIRECT_TEST_02 = "test_direct_02";
    public static final String MANDATORY_TEST_01 = "mandatory_test_02";
    public static final String TOPIC_EXCHANGE_TEST = "test_topic_01";
    public final static String DIRECT_CONFIRM_TEST = "test_direct_confirm_01";
    public final static String DIRECT_ACK_CONFIRM_TEST_01 = "direct_ack_confirm_test_01";
    public final static String BAK_EXCHANGE_NAME = "back_ex";
    public final static String BAK_EXCHANGE_NAME_02 = "back_ex_02";
    public final static String MAIN_EXCHANGE_NAME = "main_exchange_name";
    public final static String MAIN_EXCHANGE_NAME_02 = "main_exchange_name_02";
    public final static String GET_EXCHANGE_NAME_01 = "get_exchange_name_01";
    public final static String GET_FACTION_EXCHANGE_NAME_01 = "get_faction_exchange_name_02";
    public final static String GET_FACTION_EXCHANGE_NAME_03= "get_faction_exchange_name_03";
    public final static String GET_DIRECT_BATCH_EXCHANGE_NAME_01 = "get_direct_batch_exchange_name_01";
    public final static String DLX_FAOUNT_EXCHANGE = "dlx_faount_exchange";

    public final static int COUNT = 50;

    public final static String QUEUE_DIRECT = "queue_direct";
    public final static String QUEUE_MAIN = "queue_main";
    public final static String  QUEUE_GET = "queue_get";
    public final static String  QUEUE_QOS = "queue_qos";
    public final static String  QUEUE_QOS2 = "queue_qos2";
    public final static String  QUEUE_REJECT = "queue_reject";
    public static Channel getChanner(){

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
        }catch (Exception e){
            e.printStackTrace();
        }
        return channel;
    }


}
