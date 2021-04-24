package roguelike;

import roguelike.ui.UI;
import roguelike.util.ServerConnection;

/**
 * Main client class
 */
public class Client {

    public static void main(String[] args) throws Exception {
        String username = (args.length == 0) ? "anonymous" : args[0];
        UI.init(new ServerConnection(username));
    }
}
