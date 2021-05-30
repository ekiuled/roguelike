package roguelike;

import roguelike.ui.UI;
import roguelike.util.ServerConnection;

/**
 * Main client class
 */
public class Client {

    public static void main(String[] args) throws Exception {
        String username = (args.length == 0) ? "anonymous" : args[0];
        String host = (args.length > 1) ? args[1] : "localhost";
        UI.init(new ServerConnection(username, host));
    }
}
