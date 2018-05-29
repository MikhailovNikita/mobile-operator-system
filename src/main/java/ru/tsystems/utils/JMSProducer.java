package ru.tsystems.utils;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class JMSProducer {

    private ActiveMQConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageProducer producer;
    private static final Logger logger = Logger.getLogger(JMSProducer.class);

    public JMSProducer() throws JMSException {
        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createTopic("Ecare");
        producer = session.createProducer(destination);
    }

    public void sendMessage() {
        try {
            TextMessage message = session.createTextMessage("Update");
            producer.send(message);
        } catch (JMSException e) {
            logger.warn(e.getMessage());
        }
    }
}
