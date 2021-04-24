package roguelike.input;

import roguelike.util.Action;
import roguelike.util.ServerConnection;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * User input handler, converts keyboard input into action and sends it to the server
 */
public class InputHandler extends KeyAdapter {
    private final ServerConnection serverConnection;

    public InputHandler(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        try {
            switch (keyEvent.getKeyCode()) {
                case KeyEvent.VK_W -> serverConnection.sendAction(Action.MOVE_UP);
                case KeyEvent.VK_S -> serverConnection.sendAction(Action.MOVE_DOWN);
                case KeyEvent.VK_A -> serverConnection.sendAction(Action.MOVE_LEFT);
                case KeyEvent.VK_D -> serverConnection.sendAction(Action.MOVE_RIGHT);
                case KeyEvent.VK_ESCAPE -> {
                    serverConnection.sendAction(Action.EXIT);
                    System.exit(0);
                }
            }
        } catch (IOException ignored) {
        }
    }
}
