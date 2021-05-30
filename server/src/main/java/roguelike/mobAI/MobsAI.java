package roguelike.mobAI;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import roguelike.model.Mob;
import roguelike.util.Action;
import roguelike.util.ControlMessage;
import roguelike.util.QueueConnectionFactory;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class for controlling behaviour of all mobs in the game
 */
public class MobsAI {
    private static Channel controllerChannel;
    private static final Map<UUID, MobAI> mobs = new ConcurrentHashMap<>();

    /**
     * Establishes connection with controller messaging queue
     */
    public static void init(String host) throws Exception {
        Connection connection = new QueueConnectionFactory(host).newConnection();

        controllerChannel = connection.createChannel();
        controllerChannel.queueDeclare(
                QueueConnectionFactory.CONTROLLER_QUEUE_NAME,
                true,
                false,
                true,
                null);
    }

    /**
     * Sends a serialized action to the server
     */
    public static void sendMessage(String message) throws IOException {
        controllerChannel.basicPublish(
                "",
                QueueConnectionFactory.CONTROLLER_QUEUE_NAME,
                null,
                message.getBytes());
    }

    /**
     * Sends an action and UUID to the server
     */
    public static void sendAction(UUID uuid, Action action) throws IOException {
        sendMessage(new Gson().toJson(new ControlMessage(uuid, action)));
    }

    /**
     * Main loop: iterates over all mobs and triggers their next action
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public static void loop() throws IOException {
        final int frame_interval_ms = 200;
        while (true) {
            long startMs = System.currentTimeMillis();
            for (var mobAI : mobs.values()) {
                Action action = mobAI.getAction();
                if (action != null)
                    sendAction(mobAI.getId(), action);
            }
            long durationMs = System.currentTimeMillis() - startMs;
            try {
                Thread.sleep(Math.max(0, frame_interval_ms - durationMs));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void addMob(Mob mob) {
        switch (mob.getType()) {
            case AGGRESSIVE -> mobs.put(mob.getId(), new AggressiveAI(mob));
            case NEUTRAL -> mobs.put(mob.getId(), new NeutralAI(mob));
            case COWARDLY -> mobs.put(mob.getId(), new CowardlyAI(mob));
        }
    }

    public static void removeMob(UUID id) {
        mobs.remove(id);
    }
}
