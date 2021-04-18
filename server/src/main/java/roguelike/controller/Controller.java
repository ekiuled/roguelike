package roguelike.controller;

import com.google.gson.Gson;
import com.rabbitmq.client.*;
import roguelike.model.Model;
import roguelike.model.Player;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Controller {
    private final static String CONTROLLER_QUEUE_NAME = "roguelike.controller";
    private final static String VIEW_QUEUE_NAME = "roguelike.view";
    private final Channel viewChannel;

    private final Model model;
    private final Map<String, UUID> players = new HashMap<>();

    public Controller(Model model) throws Exception {
        this.model = model;

        String host = "localhost";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);

        Connection connection = factory.newConnection();

        Channel controllerChannel = connection.createChannel();
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

        controllerChannel.basicConsume(CONTROLLER_QUEUE_NAME, true, new MessageHandler(), consumerTag -> {
        });
    }

    private class MessageHandler implements DeliverCallback {
        @Override
        public void handle(String consumerTag, Delivery delivery) throws IOException {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
//            Map<String, Object> message = new Gson().fromJson(jsonString);

            System.out.println(" [x] Received '" + message + "'");
            viewChannel.basicPublish("",
                    VIEW_QUEUE_NAME,
                    null,
                    message.getBytes()); // View.get(...)
        }

    }
}
