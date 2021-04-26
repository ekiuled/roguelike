package roguelike.mobAI;

import roguelike.model.Mob;
import roguelike.util.Action;
import roguelike.util.Position;

public class CowardlyAI extends MobAI {
    private final int RANGE = 15;

    public CowardlyAI(Mob mob) {
        super(mob);
    }

    @Override
    public Action generateAction() {
        Mob player = getNearestPlayer(RANGE);
        if (player != null) {
            if (seePlayer(player.getPosition())) {
                return moveAway(player.getPosition());
            }
        }
        return Action.NOTHING;
    }

    private Action moveAway(Position position) {
        int mobX = getMob().getPosition().getX();
        int mobY = getMob().getPosition().getY();
        int playerX = position.getX();
        int playerY = position.getY();
        int distanseX = Math.abs(mobX - playerX);
        int distanseY = Math.abs(mobY - playerY);
        if (distanseX < distanseY) {
            if (distanseX != 0) {
                if (mobX - playerX > 0) {
                    return Action.MOVE_RIGHT;
                } else {
                    return Action.MOVE_LEFT;
                }
            }
            if (mobY - playerY > 0) {
                return Action.MOVE_DOWN;
            } else {
                return Action.MOVE_UP;
            }
        } else {
            if (distanseY != 0) {
                if (mobY - playerY > 0) {
                    return Action.MOVE_DOWN;
                } else {
                    return Action.MOVE_UP;
                }
            }
            if (mobX - playerX > 0) {
                return Action.MOVE_RIGHT;
            } else {
                return Action.MOVE_LEFT;
            }
        }
    }
}
