package roguelike.util;

import java.util.Map;

public class ViewMessage {
    public int levelNumber;
    public Character[][] map;
    public Map<String, Position> playersPosition;

    public ViewMessage(int number, Character[][] view, Map<String, Position> positions) {
        levelNumber = number;
        map = view;
        playersPosition = positions;
    }
}
