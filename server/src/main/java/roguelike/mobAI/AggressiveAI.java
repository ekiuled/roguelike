package roguelike.mobAI;

import roguelike.model.Mob;
import roguelike.util.Action;

public class AggressiveAI extends MobAI {
    public AggressiveAI(Mob mob) {
        super(mob);
    }

    @Override
    public Action generateAction() {
        return null;
    }
}
