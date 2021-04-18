package roguelike.util;

public class ControlMessage {
    public String username;
    public Action action;

    public ControlMessage(String username, Action action) {
        this.username = username;
        this.action = action;
    }
}
