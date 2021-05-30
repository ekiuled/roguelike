package roguelike.util;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import roguelike.ui.UI;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Class for managing interaction with the server
 */
public class ServerConnection {
    private final Channel controllerChannel;
    private final Channel viewChannel;
    private final String queueName;
    private final String username;

    /**
     * Declares messaging queues and sends a register message to the server
     */
    public ServerConnection(String username, String host) throws Exception {
        this.username = username;

        Connection connection = new QueueConnectionFactory(host).newConnection();

        controllerChannel = connection.createChannel();
        controllerChannel.queueDeclare(
                QueueConnectionFactory.CONTROLLER_QUEUE_NAME,
                true,
                false,
                true,
                null);

        viewChannel = connection.createChannel();
        viewChannel.exchangeDeclare(QueueConnectionFactory.VIEW_EXCHANGE_NAME, "fanout");
        queueName = viewChannel.queueDeclare().getQueue();
        viewChannel.queueBind(queueName, QueueConnectionFactory.VIEW_EXCHANGE_NAME, "");

        sendAction(Action.REG);
    }

    /**
     * Sends a serialized action to the server
     */
    public void sendMessage(String message) throws IOException {
        controllerChannel.basicPublish(
                "",
                QueueConnectionFactory.CONTROLLER_QUEUE_NAME,
                null,
                message.getBytes());
    }

    /**
     * Sends an action and username to the server
     */
    public void sendAction(Action action) throws IOException {
        sendMessage(new Gson().toJson(new ControlMessage(username, action)));
    }

    /**
     * Setups UI subscription to the server's view messages
     */
    public void setViewListener(UI ui) throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String jsonString = new String(delivery.getBody(), StandardCharsets.UTF_8);
            ViewMessage message = new Gson().fromJson(jsonString, ViewMessage.class);
            Position center = message.playersPosition.get(username);
            Integer health = message.playersHealth.get(username);
            if (center != null) {
                ui.display(message.map, center, health, message.levelNumber, username);
            }
        };

        viewChannel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}
