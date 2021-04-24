package roguelike.util;

/**
 * Action message to be sent from client to server
 */
public class ControlMessage {
    public final String username;
    public final Action action;

    public ControlMessage(String username, Action action) {
        this.username = username;
        this.action = action;
    }
}
