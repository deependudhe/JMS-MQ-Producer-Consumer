package com.monarch.jms.producer;

import javax.jms.*;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.monarch.jms.config.JmsConfig;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

public class ProducerTest {

    private MQQueueConnectionFactory mockConnectionFactory;

    private Connection mockConnection;

    Session mockSession;

    private Queue mockQueue;

    private MessageProducer mockProducer;

    @Before
    public void setUp() throws JMSException {
        mockConnectionFactory = mock(MQQueueConnectionFactory.class);
        mockConnection = mock(Connection.class);
        mockSession = mock(Session.class);
        mockQueue= mock(Queue.class);
        mockProducer= mock(MessageProducer.class);
    }

    @Test
    //@Ignore
    public void testSendMessage() throws JMSException {
        Producer producer = new Producer();
        producer.sendMessage();

        verify(mockConnection).createSession(true, Session.SESSION_TRANSACTED);
        verify(mockSession).createQueue(JmsConfig.getQueueName());
        verify(mockProducer).send(any(TextMessage.class));
        verify(mockSession).commit();
        verify(mockProducer).close();
        verify(mockSession).close();
        verify(mockConnection).close();
    }

    @Test
    public void testSendMessage_Exception() throws JMSException {

        when(mockConnectionFactory.createConnection(JmsConfig.getAppUser(), JmsConfig.getAppPassword())).thenReturn(mockConnection);
        when(mockConnection.createSession(true, Session.SESSION_TRANSACTED)).thenReturn(mockSession);
        when(mockSession.createQueue(JmsConfig.getQueueName())).thenReturn(mockQueue);
        when(mockSession.createProducer(mockQueue)).thenReturn(mockProducer);

        doThrow(new JMSException("Test Exception")).when(mockProducer).send(any(TextMessage.class));

        Producer producer = new Producer();
        producer.sendMessage();

        verify(mockSession).rollback();
    }
}
