package roguelike.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Creates queue connections and stores queue-related constants
 */
public class QueueConnectionFactory {
    private final static String ROGUELIKE = "roguelike";
    public final static String CONTROLLER_QUEUE_NAME = "roguelike.controller";
    public final static String VIEW_EXCHANGE_NAME = "roguelike.view";
    private final ConnectionFactory factory;

    public QueueConnectionFactory(String host) {
        factory = new ConnectionFactory();
        factory.setUsername(ROGUELIKE);
        factory.setPassword(ROGUELIKE);
        factory.setVirtualHost(ROGUELIKE);
        factory.setHost(host);
    }

    public Connection newConnection() throws IOException, TimeoutException {
        return factory.newConnection();
    }
}
