package com.monarch.jms.config;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import javax.jms.JMSException;

public class JmsConfig {

    private static final String HOST = "localhost";
    private static final int PORT = 1414;
    private static final String CHANNEL = "DEV.APP.SVRCONN";
    private static final String QMGR = "QM1";
    private static final String APP_USER = "app";
    private static final String APP_PASSWORD = "";
    private static final String QUEUE_NAME = "DEV.QUEUE.1";

    public static MQQueueConnectionFactory createConnectionFactory() throws JMSException {
        MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
        cf.setHostName(HOST);
        cf.setPort(PORT);
        cf.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        cf.setQueueManager(QMGR);
        cf.setChannel(CHANNEL);
        return cf;
    }

    public static String getQueueName() {
        return QUEUE_NAME;
    }

    public static String getAppUser() {
        return APP_USER;
    }

    public static String getAppPassword() {
        return APP_PASSWORD;
    }
}

