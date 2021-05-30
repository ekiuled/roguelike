package roguelike.model;

import roguelike.model.util.Direction;
import roguelike.model.util.MobType;
import roguelike.model.util.Plane;
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

    public Level getLevel() {
        return level;
    }

    public boolean attack(Mob target) {
        target.incomingDamage(damage);
        if (target.isDead()) {
            if (target.getType().equals(MobType.PLAYER)) {
                level.addDeadPlayer(level.removePlayer(target.getId()));
            } else {
                level.removeMob(target.getId());
            }
        }
        return target.isDead();
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
        if (nearestPlayer != null && type.equals(MobType.AGGRESSIVE)) {
            return attack(nearestPlayer);
        }
        if (nearestMonster != null && type.equals(MobType.PLAYER)) {
            return attack(nearestMonster);
        }
        return true;
    }

    private boolean tryChangePosition(Position position, Plane dir, int newCoord) {
        if (tryAttack(position)) {
            if (dir == Plane.Y) {
                getPosition().setY(newCoord);
            } else {
                getPosition().setX(newCoord);
            }
            return true;
        }
        return false;
    }

    private boolean changePosition(Plane dir, Position position, int newCoord) {
        if (this.type.equals(MobType.PLAYER)) {
            if (level.isNotWall(position) && level.hasPlayer(position) == null) {
                return tryChangePosition(position, dir, newCoord);
            } else {
                return false;
            }
        } else {
            if (level.isNotWall(position)) {
                return tryChangePosition(position, dir, newCoord);
            } else {
                return false;
            }
        }
    }

    private boolean tryMove(Plane dir, int newCoord) {
        if (dir == Plane.Y) {
            Position position = new Position(getPosition().getX(), newCoord);
            return changePosition(Plane.Y, position, newCoord);
        } else {
            Position position = new Position(newCoord, getPosition().getY());
            return changePosition(Plane.X, position, newCoord);
        }
    }

    public boolean move(Direction direction) {
        boolean wasMoved = false;
        Position currPosition = this.getPosition();
        switch (direction) {
            case UP -> wasMoved = tryMove(Plane.Y, currPosition.getY() - 1);
            case DOWN -> wasMoved = tryMove(Plane.Y, currPosition.getY() + 1);
            case LEFT -> wasMoved = tryMove(Plane.X, currPosition.getX() - 1);
            case RIGHT -> wasMoved = tryMove(Plane.X, currPosition.getX() + 1);
        }
        return wasMoved;
    }

    boolean isDead() {
        return health <= 0;
    }
}