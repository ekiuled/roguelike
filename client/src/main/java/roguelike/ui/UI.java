package roguelike.ui;

import asciiPanel.AsciiPanel;
import roguelike.input.InputHandler;
import roguelike.util.ServerConnection;

import javax.swing.*;
import java.io.IOException;

public class UI extends JFrame {
    private final AsciiPanel terminal;

    public UI(ServerConnection serverConnection) throws IOException {
        final int width = 800;
        final int height = 600;

        add(terminal = new AsciiPanel(width, height));
        addKeyListener(new InputHandler(serverConnection));
        setSize(width, height);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        serverConnection.setViewListener(this);
    }

    public void repaint(String message) {
        terminal.clear();
        terminal.write(message, 0, 0);
        terminal.repaint();
    }

}
