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
        MobPlayerInit mobPlayer = new MobPlayerInit(position);

        int error = 0;
        int deltaError = mobPlayer.distanceY + 1;
        int y = mobPlayer.mobY;
        int dirY = mobPlayer.playerY - mobPlayer.mobY;
        if (dirY > 0) {
            dirY = 1;
        }
        if (dirY < 0) {
            dirY = -1;
        }
        for (int x = Math.min(mobPlayer.playerX, mobPlayer.mobX); x < Math.max(mobPlayer.playerX, mobPlayer.mobX); x++) {
            error += deltaError;
            if (error >= (mobPlayer.distanceX + 1)) {
                y += dirY;
                error -= (mobPlayer.distanceX + 1);
            }
            if (!mob.getLevel().isNotWall(new Position(x, y))) {
                return false;
            }
        }
        return true;
    }

    protected class MobPlayerInit {
        private final int mobX = mob.getPosition().getX();
        private final int mobY = mob.getPosition().getY();
        private final int playerX;
        private final int playerY;
        private final int distanceX;
        private final int distanceY;

        public MobPlayerInit(Position position) {
            playerX = position.getX();
            playerY = position.getY();
            distanceX = Math.abs(mobX - playerX);
            distanceY = Math.abs(mobY - playerY);
        }

        public int getMobX() {
            return mobX;
        }

        public int getMobY() {
            return mobY;
        }

        public int getDistanceX() {
            return distanceX;
        }

        public int getDistanceY() {
            return distanceY;
        }

        public int getPlayerX() {
            return playerX;
        }

        public int getPlayerY() {
            return playerY;
        }
    }

    /**
     * Generates next action according to mob type strategy
     */
    protected abstract Action generateAction();
}
