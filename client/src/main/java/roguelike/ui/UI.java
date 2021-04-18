package roguelike.ui;

import asciiPanel.AsciiPanel;
import roguelike.input.InputHandler;
import roguelike.util.Action;
import roguelike.util.Position;
import roguelike.util.ServerConnection;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class UI extends JFrame {
    private final AsciiPanel terminal;
    private final int width = 80;
    private final int height = 60;

    public UI(ServerConnection serverConnection) throws IOException {
        add(terminal = new AsciiPanel(width, height));
        addKeyListener(new InputHandler(serverConnection));

        setSize(width * 9, height * 16);
        setVisible(true);
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    serverConnection.sendAction(Action.EXIT);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        serverConnection.setViewListener(this);
    }

    public void repaint(Character[][] map, Position center) {
        terminal.clear();

        int x0 = Math.max(0, center.getX() - width / 2);
        int y0 = Math.max(0, center.getY() - height / 2);

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                terminal.write(map[x0 + x][y0 + y], x, y);

        terminal.repaint();
    }

}
