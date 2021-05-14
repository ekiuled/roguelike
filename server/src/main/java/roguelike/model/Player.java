package roguelike.model;

/**
 * Class for player's score and inventory
 */
public class Player extends Mob {
    private int score;
    private final String name;

    public Player(String name) {
        super();
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
