package roguelike.model;

import roguelike.model.util.TypeOfMovement;
import roguelike.util.Action;
import roguelike.view.View;

import java.util.*;

/**
 * Main model class for managing levels and entity actions
 */
public class Model {
    private final List<Level> levels = new ArrayList<>();
    private final Map<UUID, Integer> entityLevel = new HashMap<>();

    public List<Level> getLevels() {
        return levels;
    }

    public Level getLevel(int index) {
        if (levels.size() <= index) {
            levels.add(new Level(index));
        }
        return levels.get(index);
    }

    public void addNewPlayer(Player newPlayer, int numberLevel) {
        entityLevel.put(newPlayer.getId(), numberLevel);
        Level level = getLevel(numberLevel);
        newPlayer.setPosition(level.getMap().getStartCell());
        newPlayer.setLevel(level);
        level.addPlayer(newPlayer);
        View.addView(getLevel(numberLevel));
    }

    public void update(UUID id, Action action) {
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
