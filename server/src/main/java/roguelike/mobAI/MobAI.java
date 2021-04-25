package roguelike.mobAI;

import roguelike.model.Mob;
import roguelike.util.Action;

import java.util.UUID;

public abstract class MobAI {
    private final Mob mob;

    public MobAI(Mob mob) {
        this.mob = mob;
    }

    public UUID getId() {
        return mob.getId();
    }

    public abstract Action generateAction();
}