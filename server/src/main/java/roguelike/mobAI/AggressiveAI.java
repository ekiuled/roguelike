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
    public Action generateAction() {
        Mob player = getNearestPlayer(RANGE);
        if (player != null) {
            if (seePlayer(player.getPosition())) {
                return moveToPlayer(player.getPosition());
            }
        }
        return Action.NOTHING;
    }

    private Action moveToPlayer(Position position) {
        int mobX = getMob().getPosition().getX();
        int mobY = getMob().getPosition().getY();
        int playerX = position.getX();
        int playerY = position.getY();
        int distanseX = Math.abs(mobX - playerX);
        int distanseY = Math.abs(mobY - playerY);
        if (distanseX < distanseY) {
            if (distanseX != 0) {
                if ((int) (Math.signum((double) (mobX - playerX))) > 0) {
                    return Action.MOVE_LEFT;
                } else {
                    return Action.MOVE_RIGHT;
                }
            }
            if (((int) (Math.signum((double) (mobY - playerY))) > 0)) {
                return Action.MOVE_UP;
            } else {
                return Action.MOVE_DOWN;
            }
        } else {
            if (distanseY != 0) {
                if (((int) (Math.signum((double) (mobY - playerY))) > 0)) {
                    return Action.MOVE_UP;
                } else {
                    return Action.MOVE_DOWN;
                }
            }
            if ((int) (Math.signum((double) (mobX - playerX))) > 0) {
                return Action.MOVE_LEFT;
            } else {
                return Action.MOVE_RIGHT;
            }
        }
    }
}
