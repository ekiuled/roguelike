package roguelike.mobAI;

import roguelike.model.Mob;
import roguelike.util.Action;

/**
 * Wanders randomly around the map
 */
public class NeutralAI extends MobAI {
    private Action action = Action.MOVE_UP;

    public NeutralAI(Mob mob) {
        super(mob);
    }

    @Override
    public Action generateAction() {
        if (Math.random() > 0.7) {
            switch ((int) Math.round(Math.random() * 4)) {
                case 0 -> action = Action.MOVE_UP;
                case 1 -> action = Action.MOVE_DOWN;
                case 2 -> action = Action.MOVE_LEFT;
                case 3 -> action = Action.MOVE_RIGHT;
            }
        }
        return action;
    }
}