package roguelike.util;

public enum MobType {
    PLAYER,

    AGGRESSIVE,
    NEUTRAL,
    COWARDLY;

    public static MobType randomMobType() {
        double randomNum = Math.random();
        if (randomNum <= 0.5) {
            return AGGRESSIVE;
        } else if (randomNum > 0.75) {
            return NEUTRAL;
        } else {
            return COWARDLY;
        }
    }
}
