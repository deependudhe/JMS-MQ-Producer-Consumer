package com.monarch.jms.producer;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.monarch.jms.config.JmsConfig;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class Producer {

    public void sendMessage() {
        MQQueueConnectionFactory cf = null;
        Connection connection = null;
        Session session = null;
        try {
            cf = JmsConfig.createConnectionFactory();
            connection = cf.createConnection(JmsConfig.getAppUser(), JmsConfig.getAppPassword());
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
            Queue queue = session.createQueue(JmsConfig.getQueueName());

            MessageProducer producer = session.createProducer(queue);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            TextMessage message = session.createTextMessage("Your lucky number today is " + (System.currentTimeMillis() % 1000));

            producer.send(message);
            session.commit();
            System.out.println("Sent message:\n" + message.getText());

        } catch (JMSException e) {
            e.printStackTrace();
            if (session != null) {
                try {
                    session.rollback();
                } catch (JMSException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}

