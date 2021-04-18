package roguelike.model;

import roguelike.Action;
import roguelike.view.View;

import java.util.*;

public class Model {
    private final List<Level> levels = new ArrayList<>();
    private final Map<UUID, Integer> entityLevel = new HashMap<>();

    public Level getLevel(int index) {
        if (levels.size() < index) {
            levels.add(new Level(index));
        }
        return levels.get(index);
    }

    public void addNewPlayer(Player newPlayer) {
        entityLevel.put(newPlayer.getId(), 0);
        getLevel(0).addPlayer(newPlayer);
    }

    public void update(UUID id, Action action) {
        int index = entityLevel.get(id);
        Level currentLevel = levels.get(index);
        boolean goToNextLevel = currentLevel.updateLevel(id, action);
        View.addView(currentLevel);
        if (goToNextLevel) {
            Player currentPlayer = levels.get(index).removePlayer(id);
            currentLevel = getLevel(index + 1);
            currentLevel.addPlayer(currentPlayer);
            entityLevel.put(id, index + 1);
            View.addView(currentLevel);
        }

    }

}
