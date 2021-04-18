package roguelike.model;

import roguelike.model.util.Action;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Model {
    private Map<UUID, Level> entityLevel = new HashMap<>();

    public void update(UUID id, Action action) {
        Level currentLevel = entityLevel.get(id);
        currentLevel.updateLevel(id, action);
    }

}
