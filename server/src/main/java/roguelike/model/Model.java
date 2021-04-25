package roguelike.model;

import roguelike.model.util.TypeOfMovement;
import roguelike.util.Action;
import roguelike.view.View;

import java.util.*;

/**
 * Main model class for managing levels and entity actions
 */
public class Model {
    private static final List<Level> levels = new ArrayList<>();
    private static final Map<UUID, Integer> entityLevel = new HashMap<>();

    public static List<Level> getLevels() {
        return levels;
    }

    public static Level getLevel(int index) {
        if (levels.size() <= index) {
            levels.add(new Level(index));
        }
        return levels.get(index);
    }

    public static void addEntity(Entity entity, int numberLevel) {
        entityLevel.put(entity.getId(), numberLevel);
    }

    public static void removeEntity(UUID id) {
        entityLevel.remove(id);
    }

    public static void addNewPlayer(Player newPlayer, int numberLevel) {
        addEntity(newPlayer, numberLevel);
        Level level = getLevel(numberLevel);
        newPlayer.setPosition(level.getMap().getStartCell());
        newPlayer.setLevel(level);
        level.addPlayer(newPlayer);
        View.addView(getLevel(numberLevel));
    }

    public static void update(UUID id, Action action) {
        int index = entityLevel.get(id);
        Level currentLevel = levels.get(index);
        TypeOfMovement type = currentLevel.updateLevel(id, action);
        switch (type) {
            case DONE -> View.addView(currentLevel);
            case NEXT_LEVEL -> {
                Player currentPlayer = levels.get(index).removePlayer(id);
                currentLevel = getLevel(index + 1);
                addNewPlayer(currentPlayer, index + 1);
                View.addView(currentLevel);
            }
        }
    }
}
