package roguelike.util;

import java.util.UUID;

/**
 * Action message to be sent from client to server
 */
public class ControlMessage {
    public final String username;
    public final UUID id;
    public final Action action;

    public ControlMessage(String username, Action action) {
        this.username = username;
        this.id = null;
        this.action = action;
    }

    public ControlMessage(UUID id, Action action) {
        this.username = null;
        this.id = id;
        this.action = action;
    }
}
