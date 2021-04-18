package roguelike.model;

import roguelike.model.util.Cell;
import roguelike.model.util.CellKind;
import roguelike.model.util.Direction;

public class Mob extends Entity {
    private final int INITIAL_HEALTH = 100;
    private int health;
    private LevelMap map;


    public Mob() {
        super();
        health = INITIAL_HEALTH;
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

    private void tryMove(char dir, int newCoord) {
        if (dir == 'y') {
            Cell cell = map.getCell(getPosition().getX(), newCoord);
            if (cell != null && cell.getKind().equals(CellKind.GROUND)) {
                getPosition().setY(newCoord);
            }
        }
        if (dir == 'x') {
            Cell cell = map.getCell(newCoord, getPosition().getY());
            if (cell != null && cell.getKind().equals(CellKind.GROUND))
                getPosition().setX(newCoord);
        }
    }

    public void move(Direction direction) {
        Entity.Position currPosition = this.getPosition();
        switch (direction) {
            case UP -> tryMove('y', currPosition.getY() + 1);
            case DOWN -> tryMove('y', currPosition.getY() - 1);
            case LEFT -> tryMove('x', currPosition.getX() - 1);
            case RIGHT -> tryMove('x', currPosition.getX() + 1);
        }
    }

    boolean isAlive() {
        return health > 0;
    }

    public void attack(Mob target) {
        //TODO
    }

}
