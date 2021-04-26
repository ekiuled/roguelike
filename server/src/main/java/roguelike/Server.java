package roguelike;

import roguelike.controller.Controller;
import roguelike.mobAI.MobsAI;

/**
 * Main server class
 */
public class Server {

    public static void main(String[] args) throws Exception {
        MobsAI.init();
        Controller.init();
        MobsAI.loop();
    }
}
