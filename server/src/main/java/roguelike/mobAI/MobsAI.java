package roguelike.mobAI;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import roguelike.model.Mob;
import roguelike.util.Action;
import roguelike.util.ControlMessage;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MobsAI {
    private final static String CONTROLLER_QUEUE_NAME = "roguelike.controller";
    private static Channel controllerChannel;
    private static final Map<UUID, MobAI> mobs = new ConcurrentHashMap<>();

    public static void init() throws Exception {
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
    }

    /**
     * Sends a serialized action to the server
     */
    public static void sendMessage(String message) throws IOException {
        controllerChannel.basicPublish(
                "",
                CONTROLLER_QUEUE_NAME,
                null,
                message.getBytes());
    }

    /**
     * Sends an action and UUID to the server
     */
    public static void sendAction(UUID uuid, Action action) throws IOException {
        sendMessage(new Gson().toJson(new ControlMessage(uuid, action)));
    }

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
            } catch (InterruptedException ignored) {
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
