package roguelike.util;

public class ViewMessage {
    public int levelNumber;
    public Character[][] map;

    public ViewMessage(int number, Character[][] view) {
        levelNumber = number;
        map = view;
    }
}
