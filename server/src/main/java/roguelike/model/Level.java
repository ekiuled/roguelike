package roguelike.model;

import roguelike.model.util.Direction;
import roguelike.model.util.TypeOfMovement;
import roguelike.util.Action;
import roguelike.util.Position;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * class that contains all the necessary information for each individual level
 * Includes:
 * table of monsters
 * table of players
 * table of items
 * level map
 */
public class Level {
    private final int SCALE = 1000;
    private final Map<UUID, Player> players = new HashMap<>();
    private final int number;
    private LevelMap map;
    private final Map<UUID, Mob> mobs = new HashMap<>();
    private final Map<Position, ItemEntity> items = new HashMap<>();
    ;

    public Level(int num) {
        number = num;
        generateLevel();
    }

    public Map<UUID, Player> getPlayers() {
        return players;
    }

    // creates a random level of a certain size by calling
    // the constructor for the map
    private void generateLevel() {
        map = new LevelMap((number + 1) * SCALE, (number + 1) * SCALE);
        generateMobs(number);
        generateItems(number);
    }

    public int getNumber() {
        return number;
    }

    private void generateItems(int number) {
        //TODO: generate items on map
    }

    private void generateMobs(int number) {
        //TODO: generate mobs on map
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


    // Updates the state of the level depending on the type of action
    public TypeOfMovement updateLevel(UUID id, Action action) {
        TypeOfMovement type = TypeOfMovement.NONE;
        Player currentPlayer = players.get(id);
        switch (action) {
            case MOVE_UP -> {
                if (currentPlayer.move(Direction.UP)) {
                    type = TypeOfMovement.DONE;
                }
            }
            case MOVE_DOWN -> {
                if (currentPlayer.move(Direction.DOWN)) {
                    type = TypeOfMovement.DONE;
                }
            }
            case MOVE_LEFT -> {
                if (currentPlayer.move(Direction.LEFT)) {
                    type = TypeOfMovement.DONE;
                }
            }
            case MOVE_RIGHT -> {
                if (currentPlayer.move(Direction.RIGHT)) {
                    type = TypeOfMovement.DONE;
                }
            }
            case EXIT -> {
                removePlayer(id);
                type = TypeOfMovement.EXIT;
            }
        }
        return type;
    }

    public LevelMap getMap() {
        return map;
    }
}
