package roguelike.mobAI;

import roguelike.model.Mob;
import roguelike.util.Action;
import roguelike.util.Position;

/**
 * Tries to attack the nearest player in sight
 */
public class AggressiveAI extends MobAI {
    private final int RANGE = 20;

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

    /**
     * Calculates where to move to get closer to the given position
     */
    private Action moveToPlayer(Position position) {
        MobPlayerInit mobPlayer = new MobPlayerInit(position);

        if (mobPlayer.getDistanceX() < mobPlayer.getDistanceY()) {
            if (mobPlayer.getDistanceX() != 0) {
                if (mobPlayer.getMobX() - mobPlayer.getPlayerX() > 0) {
                    return Action.MOVE_LEFT;
                } else {
                    return Action.MOVE_RIGHT;
                }
            }
            if (mobPlayer.getMobY() - mobPlayer.getPlayerY() > 0) {
                return Action.MOVE_UP;
            } else {
                return Action.MOVE_DOWN;
            }
        } else {
            if (mobPlayer.getDistanceY() != 0) {
                if (mobPlayer.getMobY() - mobPlayer.getPlayerY() > 0) {
                    return Action.MOVE_UP;
                } else {
                    return Action.MOVE_DOWN;
                }
            }
            if (mobPlayer.getMobX() - mobPlayer.getPlayerX() > 0) {
                return Action.MOVE_LEFT;
            } else {
                return Action.MOVE_RIGHT;
            }
        }
    }
}
