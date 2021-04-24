package roguelike.view;

import roguelike.model.Level;
import roguelike.model.LevelMap;
import roguelike.model.Player;
import roguelike.model.util.Cell;
import roguelike.util.Position;
import roguelike.util.ViewMessage;

import java.util.*;

/**
 * Class for generating and storing level views that will be sent to clients
 */
public class View {
    private static final Queue<ViewMessage> queue = new ArrayDeque<>();

    public static boolean isEmpty() {
        return queue.isEmpty();
    }

    public static ViewMessage getLevelView() {
        return queue.remove();
    }

    public static void addView(Level newLevel) {
        LevelMap currentMap = newLevel.getMap();
        Cell[][] currentCells = currentMap.getCells();
        Collection<Player> players = newLevel.getPlayers().values();
        int currentWight = currentMap.getWidth();
        int currentHeight = currentMap.getHeight();
        Character[][] currentView = new Character[currentWight][currentHeight];

        for (int i = 0; i < currentWight; i++) {
            for (int j = 0; j < currentHeight; j++) {
                switch (currentCells[i][j].getKind()) {
                    case WALL -> currentView[i][j] = '#';
                    case START -> currentView[i][j] = '^';
                    case END -> currentView[i][j] = '_';
                    case GROUND -> currentView[i][j] = ' ';
                }
            }
        }

        Map<String, Position> map = new HashMap<>();
        for (var player : players) {
            map.put(player.getName(), player.getPosition());
            currentView[player.getPosition().getX()][player.getPosition().getY()] = '@';
        }

        queue.add(new ViewMessage(newLevel.getNumber(), currentView, map));
    }
}
