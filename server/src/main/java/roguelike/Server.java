package roguelike;

import roguelike.controller.Controller;
import roguelike.model.Model;

public class Server {

    public static void main(String[] args) throws Exception {
        Controller controller = new Controller(new Model());
    }
}
