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


    public void attack(Mob target) {
        target.incomingDamage(damage);
        if (target.isNotAlive()) {
            if (target.getType().equals(MobType.PLAYER)) {
                level.removePlayer(target.getId());
            } else {
                level.removeMob(target.getId());
            }
        }
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

    private boolean tryAttack(Position position) {
        Mob nearestPlayer = level.hasPlayer(position);
        Mob nearestMonster = level.hasMonster(position);
        if (nearestPlayer != null) {
            if (type.equals(MobType.AGGRESSIVE)) {
                attack(nearestPlayer);
            }
            return false;
        }
        if (nearestMonster != null) {
            if (type.equals(MobType.PLAYER)) {
                attack(nearestMonster);
            }
            return false;
        }
        return true;
    }

    private boolean tryMove(char dir, int newCoord) {
        if (dir == 'y') {
            Position position = new Position(getPosition().getX(), newCoord);
            if (level.isNotWall(position)) {
                if (tryAttack(position)) {
                    getPosition().setY(newCoord);
                    return true;
                }
            }
        }
        if (dir == 'x') {
            Position position = new Position(newCoord, getPosition().getY());
            if (level.isNotWall(position)) {
                if (tryAttack(position)) {
                    getPosition().setY(newCoord);
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


}