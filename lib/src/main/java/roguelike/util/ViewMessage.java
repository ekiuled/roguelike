package roguelike.util;

import java.util.Map;

/**
 * Level view message to be sent from server to clients.
 * Stores the map view and all players' positions from the current level at the time of generating this view.
 */
public class ViewMessage {
    public final int levelNumber;
    public final Character[][] map;
    public final Map<String, Position> playersPosition;
    public final Map<String, Integer> playersHealth;

    public ViewMessage(int number, Character[][] view, Map<String, Position> positions, Map<String, Integer> health) {
        levelNumber = number;
        map = view;
        playersPosition = positions;
        playersHealth = health;
    }
}
