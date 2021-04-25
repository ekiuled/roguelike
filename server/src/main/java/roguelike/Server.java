package roguelike;

import roguelike.controller.Controller;
import roguelike.mobAI.MobsAI;
import roguelike.model.Model;

import java.io.IOException;

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
