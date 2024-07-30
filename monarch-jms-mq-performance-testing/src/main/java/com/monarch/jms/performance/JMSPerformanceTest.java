package com.monarch.jms.performance;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.monarch.jms.config.JmsConfig;
import org.openjdk.jmh.annotations.*;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

@State(Scope.Benchmark)
public class JMSPerformanceTest {

    private Connection connection;
    private Session session;
    private Queue queue;
    private MessageProducer producer;
    private MessageConsumer consumer;

    @Setup(Level.Trial)
    public void setup() throws JMSException {
        MQQueueConnectionFactory cf = JmsConfig.createConnectionFactory();
        connection = cf.createConnection(JmsConfig.getAppUser(), JmsConfig.getAppPassword());
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        queue = session.createQueue(JmsConfig.getQueueName());
        producer = session.createProducer(queue);
        consumer = session.createConsumer(queue);
    }

    @TearDown(Level.Trial)
    public void tearDown() throws JMSException {
        if (consumer != null) {
            consumer.close();
        }
        if (producer != null) {
            producer.close();
        }
        if (session != null) {
            session.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    @Benchmark
    public void testSendMessage() throws JMSException {
        TextMessage message = session.createTextMessage("Performance Test Message");
        producer.send(message);
    }

    @Benchmark
    public void testReceiveMessage() throws JMSException {
        Message message = consumer.receive(1000);
        if (message != null) {
            message.acknowledge();
        }
    }
}

