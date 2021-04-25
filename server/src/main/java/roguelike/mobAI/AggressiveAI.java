package roguelike.mobAI;

import roguelike.model.Mob;
import roguelike.util.Action;
import roguelike.util.Position;

public class AggressiveAI extends MobAI {
    private final int RANGE = 10;

    public AggressiveAI(Mob mob) {
        super(mob);
    }

    private Mob getNearestPlayer() {
        Mob mob = getMob();
        return mob.getLevel().getPlayers().values().stream()
                .filter(player -> mob.getPosition().getDistanse(player.getPosition()) < RANGE)
                .findFirst().orElse(null);
    }

    private boolean isHunt(Position position) {
        int mobX = getMob().getPosition().getX();
        int mobY = getMob().getPosition().getY();
        int playerX = position.getX();
        int playerY = position.getY();
        if (mobX != playerX && mobY != playerY) {
            mobX -= (int) (Math.signum((double) (mobX - playerX)));
            mobY -= (int) (Math.signum((double) (mobY - playerY)));
            if (!getMob().getLevel().isNotWall(new Position(mobX, mobY))) {
                return false;
            }
        } else if (mobX != playerX) {
            mobX -= (int) (Math.signum((double) (mobX - playerX)));
            if (!getMob().getLevel().isNotWall(new Position(mobX, mobY))) {
                return false;
            }
        } else {
            mobY -= (int) (Math.signum((double) (mobY - playerY)));
            if (!getMob().getLevel().isNotWall(new Position(mobX, mobY))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Action generateAction() {
        Mob player = getNearestPlayer();
        if (player != null) {
            if (isHunt(player.getPosition())) {
                return moveToPlayer(player.getPosition());

            }
        }
        return Action.NOTHING;
    }

    private Action moveToPlayer(Position position) {
        int mobX = getMob().getPosition().getX();
        int mobY = getMob().getPosition().getY();
        int playerX = position.getX();
        int playerY = position.getY();
        int distanseX = Math.abs(mobX - playerX);
        int distanseY = Math.abs(mobY - playerY);
        if (distanseX < distanseY) {
            if (distanseX != 0) {
                if ((int) (Math.signum((double) (mobX - playerX))) > 0) {
                    return Action.MOVE_LEFT;
                } else {
                    return Action.MOVE_RIGHT;
                }
            }
            if (((int) (Math.signum((double) (mobY - playerY))) > 0)) {
                return Action.MOVE_UP;
            } else {
                return Action.MOVE_DOWN;
            }
        } else {
            if (distanseY != 0) {
                if (((int) (Math.signum((double) (mobY - playerY))) > 0)) {
                    return Action.MOVE_UP;
                } else {
                    return Action.MOVE_DOWN;
                }
            }
            if ((int) (Math.signum((double) (mobX - playerX))) > 0) {
                return Action.MOVE_LEFT;
            } else {
                return Action.MOVE_RIGHT;
            }
        }
    }
}
