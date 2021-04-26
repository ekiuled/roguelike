package roguelike.mobAI;

import roguelike.model.Mob;
import roguelike.util.Action;
import roguelike.util.Position;

public class AggressiveAI extends MobAI {
    private final int RANGE = 10;

    public AggressiveAI(Mob mob) {
        super(mob);
    }

    @Override
    protected Action generateAction() {
        Mob player = getNearestPlayer(RANGE);
        if (player != null) {
            if (seePlayer(player.getPosition())) {
                return moveToPlayer(player.getPosition());
            }
        }
        return Action.NOTHING;
    }

    private Action moveToPlayer(Position position) {
        int mobX = mob.getPosition().getX();
        int mobY = mob.getPosition().getY();
        int playerX = position.getX();
        int playerY = position.getY();
        int distanceX = Math.abs(mobX - playerX);
        int distanceY = Math.abs(mobY - playerY);
        if (distanceX < distanceY) {
            if (distanceX != 0) {
                if (mobX - playerX > 0) {
                    return Action.MOVE_LEFT;
                } else {
                    return Action.MOVE_RIGHT;
                }
            }
            if (mobY - playerY > 0) {
                return Action.MOVE_UP;
            } else {
                return Action.MOVE_DOWN;
            }
        } else {
            if (distanceY != 0) {
                if (mobY - playerY > 0) {
                    return Action.MOVE_UP;
                } else {
                    return Action.MOVE_DOWN;
                }
            }
            if (mobX - playerX > 0) {
                return Action.MOVE_LEFT;
            } else {
                return Action.MOVE_RIGHT;
            }
        }
    }
}
