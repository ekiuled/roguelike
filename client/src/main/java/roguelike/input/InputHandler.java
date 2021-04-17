package roguelike.input;

import roguelike.util.ServerConnection;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class InputHandler extends KeyAdapter {
    private final ServerConnection serverConnection;

    public InputHandler(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        try {
            switch (keyEvent.getKeyCode()) {
                case KeyEvent.VK_W -> serverConnection.sendMessage("Up");
                case KeyEvent.VK_S -> serverConnection.sendMessage("Down");
                case KeyEvent.VK_A -> serverConnection.sendMessage("Left");
                case KeyEvent.VK_D -> serverConnection.sendMessage("Right");
                case KeyEvent.VK_ESCAPE -> {
                    serverConnection.sendMessage("Exit");
                    System.exit(0);
                }
            }
        } catch (IOException ignored) {
        }
    }
}
