package roguelike.util;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import roguelike.ui.UI;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ServerConnection {
    private final static String CONTROLLER_QUEUE_NAME = "roguelike.controller";
    private final static String VIEW_QUEUE_NAME = "roguelike.view";
    private final Channel controllerChannel;
    private final Channel viewChannel;
    private final String username;

    public ServerConnection(String username) throws Exception {
        this.username = username;

        String host = "localhost";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);

        Connection connection = factory.newConnection();

        controllerChannel = connection.createChannel();
        controllerChannel.queueDeclare(
                CONTROLLER_QUEUE_NAME,
                false,
                false,
                false,
                null);

        viewChannel = connection.createChannel();
        viewChannel.queueDeclare(
                VIEW_QUEUE_NAME,
                false,
                false,
                false,
                null);

        sendAction(Action.REG);
    }

    public void sendMessage(String message) throws IOException {
        controllerChannel.basicPublish(
                "",
                CONTROLLER_QUEUE_NAME,
                null,
                message.getBytes());
    }

    public void sendAction(Action action) throws IOException {
        sendMessage(new Gson().toJson(new ControlMessage(username, action)));
    }

    public void setViewListener(UI ui) throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            ui.repaint(message);
        };

        viewChannel.basicConsume(VIEW_QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }
}
