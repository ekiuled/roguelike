package roguelike.mobAI;

import roguelike.model.Mob;
import roguelike.model.Player;
import roguelike.util.Action;
import roguelike.util.Position;

import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;

public abstract class MobAI {
    private final Mob mob;

    public MobAI(Mob mob) {
        this.mob = mob;
    }

    public UUID getId() {
        return mob.getId();
    }

    public Action getAction() {
        return mob.getLevel().getPlayers().isEmpty() ? null : generateAction();
    }

    public Mob getMob() {
        return mob;
    }

    public Mob getNearestPlayer(int range) {
        Mob mob = getMob();
        Optional<Player> p = mob.getLevel().getPlayers().values().stream()
                .filter(player -> mob.getPosition().getDistanse(player.getPosition()) < range)
                .min(Comparator.comparingDouble(a -> a.getPosition().getDistanse(mob.getPosition())));
        return p.orElse(null);
    }

    public boolean seePlayer(Position position) {
        int mobX = getMob().getPosition().getX();
        int mobY = getMob().getPosition().getY();
        int playerX = position.getX();
        int playerY = position.getY();
        while (playerX != mobX || playerY != mobY) {
            mobX -= (int) (Math.signum((double) (mobX - playerX)));
            mobY -= (int) (Math.signum((double) (mobY - playerY)));
            if (!getMob().getLevel().isNotWall(new Position(mobX, mobY))) {
                return false;
            }
        }
        return true;
    }


    public abstract Action generateAction();
}
