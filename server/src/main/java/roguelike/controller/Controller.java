package roguelike.controller;

import com.google.gson.Gson;
import com.rabbitmq.client.*;
import roguelike.model.Model;
import roguelike.model.Player;
import roguelike.util.Action;
import roguelike.util.ControlMessage;
import roguelike.util.ViewMessage;
import roguelike.view.View;
import sun.misc.Signal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Class for managing interaction with clients
 */
public class Controller {
    private final static String CONTROLLER_QUEUE_NAME = "roguelike.controller";
    private final static String VIEW_QUEUE_NAME = "roguelike.view";
    private static Channel viewChannel;

    private static final Map<String, UUID> players = new HashMap<>();

    public static void init() throws Exception {
        String host = "localhost";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);

        Connection connection = factory.newConnection();

        Channel controllerChannel = connection.createChannel();
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

        controllerChannel.basicConsume(CONTROLLER_QUEUE_NAME, true, new MessageHandler(), consumerTag -> {
        });

        Signal.handle(new Signal("INT"), signal -> System.out.println("Interrupted by Ctrl+C"));
    }

    /**
     * Client action handler
     */
    private static class MessageHandler implements DeliverCallback {
        /**
         * Parses client action, invokes corresponding model method and publishes generated model level views
         */
        @Override
        public void handle(String consumerTag, Delivery delivery) throws IOException {
            String jsonString = new String(delivery.getBody(), StandardCharsets.UTF_8);
            ControlMessage message = new Gson().fromJson(jsonString, ControlMessage.class);

            if (message.action == Action.REG) {
                Player player = new Player(message.username);
                players.put(message.username, player.getId());
                Model.addNewPlayer(player, 0);
            } else {
                UUID id = message.id == null ? players.get(message.username) : message.id;
                Model.update(id, message.action);
            }
            while (!View.isEmpty()) {
                ViewMessage levelView = View.getLevelView();
                viewChannel.basicPublish(
                        "",
                        VIEW_QUEUE_NAME,
                        null,
                        new Gson().toJson(levelView).getBytes());
            }
        }
    }
}
