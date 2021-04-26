package roguelike.mobAI;

import roguelike.model.Mob;
import roguelike.model.Player;
import roguelike.util.Action;
import roguelike.util.Position;

import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;

public abstract class MobAI {
    protected final Mob mob;

    public MobAI(Mob mob) {
        this.mob = mob;
    }

    public UUID getId() {
        return mob.getId();
    }

    public Action getAction() {
        return mob.getLevel().getPlayers().isEmpty() ? null : generateAction();
    }

    protected Mob getNearestPlayer(int range) {
        Optional<Player> p = mob.getLevel().getPlayers().values().stream()
                .filter(player -> mob.getPosition().getDistance(player.getPosition()) < range)
                .min(Comparator.comparingDouble(a -> a.getPosition().getDistance(mob.getPosition())));
        return p.orElse(null);
    }

    protected boolean seePlayer(Position position) {
        int mobX = mob.getPosition().getX();
        int mobY = mob.getPosition().getY();
        int playerX = position.getX();
        int playerY = position.getY();
        while (playerX != mobX || playerY != mobY) {
            mobX -= Math.signum(mobX - playerX);
            mobY -= Math.signum(mobY - playerY);
            if (!mob.getLevel().isNotWall(new Position(mobX, mobY))) {
                return false;
            }
        }
        return true;
    }

    protected abstract Action generateAction();
}
