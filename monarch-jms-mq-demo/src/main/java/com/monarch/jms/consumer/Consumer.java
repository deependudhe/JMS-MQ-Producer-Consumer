package com.monarch.jms.consumer;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.monarch.jms.config.JmsConfig;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;


public class Consumer {

    public void receiveMessage() {
        MQQueueConnectionFactory cf = null;
        Connection connection = null;
        Session session = null;
        try {
            cf = JmsConfig.createConnectionFactory();
            connection = cf.createConnection(JmsConfig.getAppUser(), JmsConfig.getAppPassword());
            connection.start();
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
            Queue queue = session.createQueue(JmsConfig.getQueueName());

            MessageConsumer consumer = session.createConsumer(queue);
            String receivedMessage = ((TextMessage )consumer.receive()).getText(); // in ms or 15 seconds

            if (receivedMessage != null) {
                session.commit();
                System.out.println("\nReceived message:\n" + receivedMessage);
            } else {
                session.rollback();
                System.out.println("No message received within the given timeout.");
            }

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

