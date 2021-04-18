package roguelike.util;

import java.util.Map;

public class ViewMessage {
    public final int levelNumber;
    public final Character[][] map;
    public final Map<String, Position> playersPosition;

    public ViewMessage(int number, Character[][] view, Map<String, Position> positions) {
        levelNumber = number;
        map = view;
        playersPosition = positions;
    }
}
