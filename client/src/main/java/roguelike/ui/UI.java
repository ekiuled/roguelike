package roguelike.ui;

import asciiPanel.AsciiPanel;
import roguelike.input.InputHandler;
import roguelike.util.Action;
import roguelike.util.Position;
import roguelike.util.ServerConnection;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Map;

/**
 * Class for managing the game window
 */
public class UI extends JFrame {
    private final AsciiPanel terminal;
    private final int width = 99;
    private final int height = 49;
    private final int statusHeight = 3;

    private final Map<Character, Color> texturePack = Map.of(
            '#', Color.ORANGE,
            '^', Color.RED,
            '_', Color.GREEN,
            '@', Color.CYAN,
            'a', Color.RED,
            'n', Color.YELLOW,
            'c', Color.BLUE
    );

    /**
     * Setups user input listener, creates the drawing terminal
     */
    public UI(ServerConnection serverConnection) throws IOException {
        int terminalWidth = width;
        int terminalHeight = height + statusHeight + 2;

        add(terminal = new AsciiPanel(terminalWidth, terminalHeight));
        addKeyListener(new InputHandler(serverConnection));

        setSize(terminalWidth * 9, terminalHeight * 16);
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

    public static void init(ServerConnection serverConnection) throws IOException {
        new UI(serverConnection);
    }

    /**
     * Displays new frame in the terminal
     *
     * @param map      the entire level view
     * @param center   player position
     * @param health   player health
     * @param level    level number
     * @param username username
     */
    public void display(Character[][] map, Position center, int health, int level, String username) {
        repaintMap(map, center);
        repaintStatusBar(health, level, username);
        terminal.repaint();
    }

    /**
     * Draws an area around the center in the terminal
     *
     * @param map    the entire level view
     * @param center player position
     */
    private void repaintMap(Character[][] map, Position center) {
        int mapWidth = map.length;
        int mapHeight = map[0].length;

        int x0 = Math.min(mapWidth - width, Math.max(0, center.getX() - width / 2));
        int y0 = Math.min(mapHeight - height, Math.max(0, center.getY() - height / 2));

        int xMax = Math.min(width, mapWidth - x0);
        int yMax = Math.min(height, mapHeight - y0);

        for (int x = 0; x < xMax; x++)
            for (int y = 0; y < yMax; y++) {
                Character pixel = map[x0 + x][y0 + y];
                terminal.write(pixel, x, y, texturePack.getOrDefault(pixel, Color.WHITE));
            }
    }

    /**
     * Draws status bar
     */
    private void repaintStatusBar(int health, int level, String username) {
        Color background = Color.DARK_GRAY;
        Color textColor = Color.WHITE;
        Color gameOverColor = Color.RED;
        terminal.clear(' ', 0, height, width, statusHeight, null, background);
        String status = String.format("USERNAME: %s    LEVEL: %d    HEALTH: %d    ", username, level, health);
        terminal.write(status, 1, height + 1, textColor, background);
        if (health == 0)
            terminal.write("GAME OVER", status.length() + 2, height + 1, gameOverColor, background);
    }
}