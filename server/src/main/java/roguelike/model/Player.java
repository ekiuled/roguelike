package roguelike.model;

public class Player extends Mob {
    private int score;
    private String name;

    public Player(Position pos, LevelMap map, String name) {
        super(pos, map);
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
