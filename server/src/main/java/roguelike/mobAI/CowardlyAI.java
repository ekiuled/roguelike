package roguelike.mobAI;

import roguelike.model.Mob;
import roguelike.util.Action;
import roguelike.util.Position;

/**
 * Tries to get away from players in sight
 */
public class CowardlyAI extends MobAI {
    private final int RANGE = 20;

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

    /**
     * Calculates where to move to get away from the given position
     */
    private Action moveAway(Position position) {
        MobPlayerInit mobPlayer = new MobPlayerInit(position);

        if (mobPlayer.getDistanceX() < mobPlayer.getDistanceY()) {
            if (mobPlayer.getDistanceX() != 0) {
                if (mobPlayer.getMobX() - mobPlayer.getPlayerX() > 0) {
                    return Action.MOVE_RIGHT;
                } else {
                    return Action.MOVE_LEFT;
                }
            }
            if (mobPlayer.getMobY() - mobPlayer.getPlayerY() > 0) {
                return Action.MOVE_DOWN;
            } else {
                return Action.MOVE_UP;
            }
        } else {
            if (mobPlayer.getDistanceY() != 0) {
                if (mobPlayer.getMobY() - mobPlayer.getPlayerY() > 0) {
                    return Action.MOVE_DOWN;
                } else {
                    return Action.MOVE_UP;
                }
            }
            if (mobPlayer.getMobX() - mobPlayer.getPlayerX() > 0) {
                return Action.MOVE_RIGHT;
            } else {
                return Action.MOVE_LEFT;
            }
        }
    }
}
