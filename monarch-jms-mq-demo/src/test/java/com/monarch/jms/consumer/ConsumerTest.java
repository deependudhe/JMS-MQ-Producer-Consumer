package com.monarch.jms.consumer;

import javax.jms.*;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.monarch.jms.config.JmsConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConsumerTest {

    @Mock
    private MQQueueConnectionFactory mockConnectionFactory;

    @Mock
    private Connection mockConnection;

    @Mock
    private Session mockSession;

    @Mock
    private Queue mockQueue;

    @Mock
    private MessageConsumer mockConsumer;

    @Mock
    private TextMessage mockTextMessage;

    @Before
    public void setUp() throws JMSException {
        when(mockConnectionFactory.createConnection(JmsConfig.getAppUser(), JmsConfig.getAppPassword())).thenReturn(mockConnection);
        when(mockConnection.createSession(true, Session.SESSION_TRANSACTED)).thenReturn(mockSession);
        when(mockSession.createQueue(JmsConfig.getQueueName())).thenReturn(mockQueue);
        when(mockSession.createConsumer(mockQueue)).thenReturn(mockConsumer);
    }

    @Test
    public void testReceiveMessage() throws JMSException {
        when(mockConsumer.receive(15000)).thenReturn(mockTextMessage);

        Consumer consumer = new Consumer();
        consumer.receiveMessage();

        verify(mockConnection).createSession(true, Session.SESSION_TRANSACTED);
        verify(mockSession).createQueue(JmsConfig.getQueueName());
        verify(mockConsumer).receive(15000);
        verify(mockSession).commit();
        verify(mockConsumer).close();
        verify(mockSession).close();
        verify(mockConnection).close();
    }

    @Test(expected = JMSException.class)
    public void testReceiveMessage_Exception() throws JMSException {
        when(mockConsumer.receive(15000)).thenThrow(new JMSException("Test Exception"));

        Consumer consumer = new Consumer();
        consumer.receiveMessage();

        verify(mockSession).rollback();
    }
}

