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
    protected Action generateAction() {
        Mob player = getNearestPlayer(RANGE);
        if (player != null) {
            if (seePlayer(player.getPosition())) {
                return moveAway(player.getPosition());
            }
        }
        return Action.NOTHING;
    }

    private Action moveAway(Position position) {
        int mobX = mob.getPosition().getX();
        int mobY = mob.getPosition().getY();
        int playerX = position.getX();
        int playerY = position.getY();
        int distanceX = Math.abs(mobX - playerX);
        int distanceY = Math.abs(mobY - playerY);
        if (distanceX < distanceY) {
            if (distanceX != 0) {
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
            if (distanceY != 0) {
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
