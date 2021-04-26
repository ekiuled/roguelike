package roguelike.mobAI;

import roguelike.model.Mob;
import roguelike.model.Player;
import roguelike.util.Action;
import roguelike.util.Position;

import java.util.Comparator;
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
     * Determines whether given position is visible for the mob (using Bresenham's line algorithm)
     */
    protected boolean seePlayer(Position position) {
        int x0 = mob.getPosition().getX();
        int y0 = mob.getPosition().getY();
        int x1 = position.getX();
        int y1 = position.getY();
        int deltax = Math.abs(x1 - x0);
        int deltay = Math.abs(y1 - y0);
        int error = 0;
        int deltaerr = deltay + 1;
        int y = y0;
        int diry = y1 - y0;
        if (diry > 0) {
            diry = 1;
        }
        if (diry < 0) {
            diry = -1;
        }
        for (int x = Math.min(x0, x1); x < Math.max(x0, x1); x++) {
            error += deltaerr;
            if (error >= (deltax + 1)) {
                y += diry;
                error -= (deltax + 1);
            }
            if (!mob.getLevel().isNotWall(new Position(x, y))) {
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
