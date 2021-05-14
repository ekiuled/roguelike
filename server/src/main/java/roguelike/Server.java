package roguelike;

import roguelike.controller.Controller;
import roguelike.model.Model;

/**
 * Main server class
 */
public class Server {

    public static void main(String[] args) throws Exception {
        Controller.init(new Model());
    }
}
