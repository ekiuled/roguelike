package roguelike;

import roguelike.input.InputHandler;
import roguelike.ui.UI;
import roguelike.util.ServerConnection;

public class Client {

    public static void main(String[] args) throws Exception {
        String username = (args.length == 0) ? "anonymous" : args[0];
        UI ui = new UI(new ServerConnection(username));
    }
}
