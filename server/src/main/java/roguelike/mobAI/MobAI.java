package roguelike.mobAI;

import roguelike.model.Mob;
import roguelike.model.Player;
import roguelike.util.Action;
import roguelike.util.Position;

import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;

/**
 * Class for controlling behaviour of a single mob
 */
public abstract class MobAI {
    protected final Mob mob;

    public MobAI(Mob mob) {
        this.mob = mob;
    }

    public UUID getId() {
        return mob.getId();
    }

    /**
     * Generates next action if there are players on the mob's level
     *
     * @return generated action
     */
    public Action getAction() {
        return mob.getLevel().getPlayers().isEmpty() ? null : generateAction();
    }

    /**
     * Finds nearest player within given radius
     *
     * @param range vision radius
     * @return nearest player or null if there are no players in range
     */
    protected Player getNearestPlayer(int range) {
        return mob.getLevel().getPlayers().values().stream()
                .filter(player -> mob.getPosition().getDistance(player.getPosition()) < range)
                .min(Comparator.comparingDouble(a -> a.getPosition().getDistance(mob.getPosition())))
                .orElse(null);
    }

    /**
     * Determines whether given position is visible for the mob
     */
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

    /**
     * Generates next action according to mob type strategy
     */
    protected abstract Action generateAction();
}
