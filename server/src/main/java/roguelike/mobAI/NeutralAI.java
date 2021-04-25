package roguelike.mobAI;

import roguelike.model.Mob;
import roguelike.util.Action;

public class NeutralAI extends MobAI {
    public NeutralAI(Mob mob) {
        super(mob);
    }

    @Override
    public Action generateAction() {
        switch ((int) Math.round(Math.random())) {
            case 0 -> {
                return Action.MOVE_UP;
            }
            case 1 -> {
                return Action.MOVE_DOWN;
            }
            case 2 -> {
                return Action.MOVE_LEFT;
            }
            case 3 -> {
                return Action.MOVE_RIGHT;
            }
        }
        return null;
    }
}