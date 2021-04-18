package roguelike.util;

public class ControlMessage {
    public final String username;
    public final Action action;

    public ControlMessage(String username, Action action) {
        this.username = username;
        this.action = action;
    }
}
