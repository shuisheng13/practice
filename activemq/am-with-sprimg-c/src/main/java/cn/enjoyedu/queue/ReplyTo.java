package cn.enjoyedu.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com
 *往期视频咨询芊芊老师  QQ：2130753077  VIP课程咨询 依娜老师QQ：2133576719
 *类说明：
 */
@Component
public class ReplyTo {

    @Autowired
    @Qualifier("jmsConsumerQueueTemplate")
    private JmsTemplate jmsTemplate;

    public void send(final String consumerMsg, Message produerMessage)
            throws JMSException {

        jmsTemplate.send(produerMessage.getJMSReplyTo(),
                new MessageCreator() {
            public Message createMessage(Session session)
                    throws JMSException {
                Message msg
                        = session.createTextMessage(
                                "QueueReceiver1 accept msg"
                        +consumerMsg);
                return msg;
            }
        });



    }

}
