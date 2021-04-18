package roguelike.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ViewMessage {
    public int levelNumber;
    public Character[][] map;
    public Map<UUID, Position> playersPosition = new HashMap<>();

    public ViewMessage(int number, Character[][] view, Map<UUID, Position> positions) {
        levelNumber = number;
        map = view;
        playersPosition = positions;
    }
}
