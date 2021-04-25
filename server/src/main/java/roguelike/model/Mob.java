package roguelike.model;

import roguelike.model.util.Direction;
import roguelike.model.util.MobType;
import roguelike.util.Position;

/**
 * Class for any map entity that can move (mobs / players)
 */
public class Mob extends Entity {
    private MobType type = MobType.NEUTRAL;
    private final int INITIAL_HEALTH = 100;
    private final int INITIAL_DAMAGE = 20;
    private int health;
    private Level level;
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

    public Level getMap() {
        return level;
    }


    public boolean tryAttack(Position position) {
        switch (type) {
            case AGGRESSIVE -> {
                Mob target = level.hasPlayer(position);
                if (target != null) {
                    target.incomingDamage(damage);
                    if (target.isNotAlive()) {
                        level.removePlayer(target.getId());
                        return false;
                    }
                    return true;
                }
            }
            case PLAYER -> {
                Mob target = level.hasMonster(position);
                if (target != null) {
                    target.incomingDamage(damage);
                    if (target.isNotAlive()) {
                        level.removeMob(target.getId());
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void incomingDamage(int damage) {
        health -= damage;

    }

    public int getHealth() {
        return health;
    }

    private boolean tryMove(char dir, int newCoord) {
        if (dir == 'y') {
            Position position = new Position(getPosition().getX(), newCoord);
            if (!level.isWall(position)) {
                if (!tryAttack(position)) {
                    getPosition().setY(newCoord);
                    return true;
                }
            }
        }
        if (dir == 'x') {
            Position position = new Position(newCoord, getPosition().getY());
            if (!level.isWall(position)) {
                if (!tryAttack(position)) {
                    getPosition().setX(newCoord);
                    return true;
                }
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

//    public void attack(Mob target) {
//        switch (type) {
//            case PLAYER, AGGRESSIVE -> target.incomingDamage(this.damage);
//        }
//    }

}
