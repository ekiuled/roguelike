package roguelike.util;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import roguelike.ui.UI;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Class for managing interaction with the server
 */
public class ServerConnection {
    private final static String CONTROLLER_QUEUE_NAME = "roguelike.controller";
    private final static String VIEW_QUEUE_NAME = "roguelike.view";
    private final Channel controllerChannel;
    private final Channel viewChannel;
    private final String username;

    /**
     * Declares messaging queues and sends a register message to the server
     */
    public ServerConnection(String username) throws Exception {
        this.username = username;

        String host = "localhost";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);

        Connection connection = factory.newConnection();

        controllerChannel = connection.createChannel();
        controllerChannel.queueDeclare(
                CONTROLLER_QUEUE_NAME,
                true,
                false,
                true,
                null);

        viewChannel = connection.createChannel();
        viewChannel.queueDeclare(
                VIEW_QUEUE_NAME,
                true,
                false,
                true,
                null);

        sendAction(Action.REG);
    }

    /**
     * Sends a serialized action to the server
     */
    public void sendMessage(String message) throws IOException {
        controllerChannel.basicPublish(
                "",
                CONTROLLER_QUEUE_NAME,
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
//                ui.repaint(message.map, center);
            }
        };

        viewChannel.basicConsume(VIEW_QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }
}
