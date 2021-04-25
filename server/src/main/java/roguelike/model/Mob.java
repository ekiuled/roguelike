package roguelike.model;

import roguelike.model.util.Cell;
import roguelike.model.util.CellKind;
import roguelike.model.util.Direction;
import roguelike.util.MobType;
import roguelike.util.Position;

/**
 * Class for any map entity that can move (mobs / players)
 */
public class Mob extends Entity {
    private MobType type = MobType.NEUTRAL;
    private final int INITIAL_HEALTH = 100;
    private final int INITIAL_DAMAGE = 20;
    private int health;
    private LevelMap map;
    private int damage;

    public Mob() {
        super();
        health = INITIAL_HEALTH;
        damage = INITIAL_DAMAGE;
    }

    public MobType getType() {
        return type;
    }

    public void setType(MobType type) {
        this.type = type;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public LevelMap getMap() {
        return map;
    }

    public void setMap(LevelMap map) {
        this.map = map;
    }

    public void incomingDamage(int damage) {
        health -= damage;

    }

    public int getHealth() {
        return health;
    }

    private boolean tryMove(char dir, int newCoord) {
        if (dir == 'y') {
            Cell cell = map.getCell(getPosition().getX(), newCoord);
            if (cell != null && !cell.getKind().equals(CellKind.WALL)) {
                getPosition().setY(newCoord);
                return true;
            }
        }
        if (dir == 'x') {
            Cell cell = map.getCell(newCoord, getPosition().getY());
            if (cell != null && !cell.getKind().equals(CellKind.WALL)) {
                getPosition().setX(newCoord);
                return true;
            }
        }
        return false;
    }

    public boolean move(Direction direction) {
        boolean wasMoved = false;
        Position currPosition = this.getPosition();
        switch (direction) {
            case UP -> wasMoved = tryMove('y', currPosition.getY() - 1);
            case DOWN -> wasMoved = tryMove('y', currPosition.getY() + 1);
            case LEFT -> wasMoved = tryMove('x', currPosition.getX() - 1);
            case RIGHT -> wasMoved = tryMove('x', currPosition.getX() + 1);
        }
        return wasMoved;
    }

    boolean isNotAlive() {
        return health <= 0;
    }

    public void attack(Mob target) {
        switch (type) {
            case PLAYER, AGGRESSIVE -> target.incomingDamage(this.damage);
        }
    }

}
