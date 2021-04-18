package roguelike.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import roguelike.model.Model;

import java.nio.charset.StandardCharsets;

public class Controller {
    private final static String CONTROLLER_QUEUE_NAME = "roguelike.controller";
    private final static String VIEW_QUEUE_NAME = "roguelike.view";
    private final Channel viewChannel;
    private final Model model;

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

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            viewChannel.basicPublish("",
                    VIEW_QUEUE_NAME,
                    null,
                    message.getBytes()); // View.get(...)
        };

        controllerChannel.basicConsume(CONTROLLER_QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }
}
