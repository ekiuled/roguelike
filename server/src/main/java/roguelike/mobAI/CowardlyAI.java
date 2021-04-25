package roguelike.mobAI;

import roguelike.model.Mob;
import roguelike.util.Action;

public class CowardlyAI extends MobAI {
    public CowardlyAI(Mob mob) {
        super(mob);
    }

    @Override
    public Action generateAction() {
        return null;
    }
}
