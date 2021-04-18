package roguelike.model;

import roguelike.model.util.Direction;
import roguelike.util.Action;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Level {
    private final int SCALE = 1000;
    private final Map<UUID, Player> players = new HashMap<>();
    private int number;
    private LevelMap map;
    private Map<UUID, Mob> mobs;
    private Map<Entity.Position, ItemEntity> items;

    public Level(int num) {
        number = num;
        generateLevel();
    }

    private void generateLevel() {
        map = new LevelMap((number + 1) * SCALE, (number + 1) * SCALE);
        generateMobs(number);
        generateItems(number);
    }

    public int getNumber() {
        return number;
    }

    private void generateItems(int number) {
        items = new HashMap<>();
    }

    private void generateMobs(int number) {
        mobs = new HashMap<>();
    }

    public void addPlayer(Player player) {

        players.put(player.getId(), player);
    }

    public Player removePlayer(UUID playerId) {
        return players.remove(playerId);
    }

    public void addPMob(Mob monster) {
        mobs.put(monster.getId(), monster);
    }

    public void addItem(ItemEntity item) {
        items.put(item.getPosition(), item);
    }


    public boolean updateLevel(UUID id, Action action) {
        Player currentPlayer = players.get(id);
        switch (action) {
            case MOVE_UP -> currentPlayer.move(Direction.UP);
            case MOVE_DOWN -> currentPlayer.move(Direction.DOWN);
            case MOVE_LEFT -> currentPlayer.move(Direction.LEFT);
            case MOVE_RIGHT -> currentPlayer.move(Direction.RIGHT);
            case EXIT -> removePlayer(id);
        }
        return false;
    }

    public LevelMap getMap() {
        return map;
    }
}
