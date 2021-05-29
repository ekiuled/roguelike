package roguelike.model.util;

public enum MobType {
    PLAYER,

    AGGRESSIVE,
    NEUTRAL,
    COWARDLY;

    private static final double PROBABILITY_AGGRESSIVE = 0.33;
    private static final double PROBABILITY_NEUTRAL = 0.33;

    public static MobType randomMobType() {
        double randomNum = Math.random();
        if (randomNum <= PROBABILITY_AGGRESSIVE) {
            return AGGRESSIVE;
        } else if (randomNum > PROBABILITY_AGGRESSIVE + PROBABILITY_NEUTRAL) {
            return COWARDLY;
        } else {
            return NEUTRAL;
        }
    }
}
