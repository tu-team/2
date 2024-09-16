package mq;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.broker.BrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MessageQueuePersister {

    private static final String MQ_PERSISTENCE_DIR = "mq-store";

    private static final Logger logger = LoggerFactory.getLogger(MessageQueuePersister.class);

    private Map<String, Session> queueListeners = new ConcurrentHashMap<>();

    private BrokerService brokerService;
    private ActiveMQConnection connection;

    @PostConstruct
    public void init() {
        String brokerURL = "vm://localhost";
        logger.info("Initializing ActiveMQ connection factory with broker URL {0} and storage directory {1}", brokerURL, MQ_PERSISTENCE_DIR);

        try {
            brokerService = new BrokerService();
            brokerService.addConnector(brokerURL);
            brokerService.setDataDirectory(MQ_PERSISTENCE_DIR);
            brokerService.start();
            logger.info("ActiveMQ broker started.");

            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);

            connection = (ActiveMQConnection) connectionFactory.createConnection();
            connection.setExceptionListener(e -> logger.error("JMSException", e));

            RedeliveryPolicy redeliveryPolicy = connectionFactory.getRedeliveryPolicy();
            redeliveryPolicy.setMaximumRedeliveries(-1);
            connectionFactory.setRedeliveryPolicy(redeliveryPolicy);
            logger.info("Redelivery policy is {0}", redeliveryPolicy);

            connection.start();
            logger.info("ActiveMQ connection started.");
        } catch (Exception e) {
            logger.error("Exception while creating ActiveMQ broker and/or connection", e);
        }
    }

    @PreDestroy
    public void shutDown() {
        logger.info("Shutting down ActiveMQ connection.");
        try {
            queueListeners.forEach((queueName, session) -> {
                logger.info("Closing session for queue {0}", queueName);
                try {
                    session.close();
                } catch (JMSException e) {
                    logger.warn("Exception when closing ActiveMQ session for queue {0}: {1}", queueName, e);
                }
            });

            if (connection != null) {
                connection.close();
                logger.info("ActiveMQ connection closed.");
            }

            if (brokerService != null) {
                brokerService.stop();
                logger.info("ActiveMQ broker stopped.");
            }
        } catch (Exception e) {
            logger.error("Exception while shutting down ActiveMQ broker and/or closing connection", e);
        }
    }

    public <T extends Serializable> boolean enqueue(Supplier<T> suplier) {
        T message = suplier.get();
        String queueName = message.getClass().getCanonicalName();
        logger.debug("Sending to queue {0} message of type {1}.", queueName, message.getClass());

        if (!queueListeners.containsKey(queueName)) {
            logger.warn("There is no listener registered for queue: " + queueName);
            return false;
        } else {
            try {
                Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
                Destination destination = session.createQueue(queueName);

                MessageProducer producer = session.createProducer(destination);
                producer.setDeliveryMode(DeliveryMode.PERSISTENT);

                ObjectMessage objectMessage = session.createObjectMessage(message);
                producer.send(objectMessage);

                session.commit();
                producer.close();
                logger.debug("Successfuly delivered to queue {0} message {1}", queueName, message);
                return true;
            } catch (JMSException e) {
                logger.error("Exception when sending message to queue", e);
                return false;
            }
        }
    }

    public <T extends Serializable> void subscribeConsumer(Class<T> messageClass, Function<T, Boolean> consumer) {
        try {
            String queueName = messageClass.getCanonicalName();
            final Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
            Session existingListenerSession = queueListeners.putIfAbsent(queueName, session);
            if (existingListenerSession != null) {
                session.close();
                throw new IllegalArgumentException("The queue " + queueName + " already has a listener registered");
            }

            Destination destination = session.createQueue(queueName);
            MessageConsumer messageConsumer = session.createConsumer(destination);
            messageConsumer.setMessageListener(message -> {
                try {
                    T messagePayload = (T) ((ObjectMessage) message).getObject();
                    logger.debug("Received payload {0} from ActiveMQ queue {1}", messagePayload, queueName);
                    if (consumer.apply(messagePayload)) {
                        logger.debug("Consumer accepted the message, acknowledging it for ActiveMQ");
                        session.commit();
                    } else {
                        logger.debug("Consumer rejected the message, not acknowledging it for ActiveMQ");
                        session.rollback();
                    }
                } catch (JMSException e) {
                    logger.error("Exception while processing message from queue", e);
                    try {
                        session.rollback();
                    } catch (JMSException ignore) {
                        logger.error("Exception while rolling back transaction", ignore);
                    }
                }
            });
        } catch (JMSException e) {
            logger.error("Exception while registering consumer for queue", e);
        }
    }
}