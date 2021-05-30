package roguelike;

import roguelike.controller.Controller;
import roguelike.mobAI.MobsAI;

/**
 * Main server class
 */
public class Server {

    public static void main(String[] args) throws Exception {
        String host = (args.length == 0) ? "localhost" : args[0];
        MobsAI.init(host);
        Controller.init(host);
        MobsAI.loop();
    }
}
