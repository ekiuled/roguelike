package roguelike.model;

import roguelike.model.util.CellKind;
import roguelike.model.util.Direction;
import roguelike.model.util.MobType;
import roguelike.model.util.TypeOfMovement;
import roguelike.util.Action;
import roguelike.util.Position;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


/**
 * Class that contains all the necessary information for each individual level
 * Includes:
 * - table of monsters
 * - table of players
 * - table of items
 * - level map
 */
public class Level {
    private final int MAP_SCALE = 100;
    private final int MOB_SCALE = 5;
    private final Map<UUID, Player> players = new HashMap<>();
    private final int number;
    private LevelMap map;
    private final Map<UUID, Mob> mobs = new HashMap<>();
    private final Map<Position, ItemEntity> items = new HashMap<>();

    public Level(int num) {
        number = num;
        generateLevel();
    }

    public Map<UUID, Player> getPlayers() {
        return players;
    }

    /**
     * Creates a random level of a certain size by calling the constructor for the map
     */
    private void generateLevel() {
        map = new LevelMap((number + 1) * MAP_SCALE, (number + 1) * MAP_SCALE);
        generateMobs((number + 1) * MOB_SCALE);
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
        int num = 0;
        while (num < number) {
            Position position = Position.generateRandom(map.getWidth(), map.getHeight());
            if (map.getCell(position.getX(), position.getY()).getKind().equals(CellKind.GROUND)) {
                Mob newMob = new Mob();
                newMob.setPosition(position);
                newMob.setType(MobType.randomMobType());
                addPMob(newMob);
                num++;
            }
        }
    }

    public Map<UUID, Mob> getMobs() {
        return mobs;
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

    public Mob removeMob(Mob monster) {
        return mobs.remove(monster);
    }

    public void addItem(ItemEntity item) {
        items.put(item.getPosition(), item);
    }


    /**
     * Updates the state of the level depending on the type of action
     */
    public TypeOfMovement updateLevel(UUID id, Action action) {
        TypeOfMovement type = TypeOfMovement.NONE;
        Mob currentMob;
        if (mobs.get(id) != null) {
            currentMob = mobs.get(id);
        } else {
            currentMob = players.get(id);
        }

        switch (action) {
            case MOVE_UP -> {
                if (currentMob.move(Direction.UP)) {
                    type = TypeOfMovement.DONE;
                }
            }
            case MOVE_DOWN -> {
                if (currentMob.move(Direction.DOWN)) {
                    type = TypeOfMovement.DONE;
                }
            }
            case MOVE_LEFT -> {
                if (currentMob.move(Direction.LEFT)) {
                    type = TypeOfMovement.DONE;
                }
            }
            case MOVE_RIGHT -> {
                if (currentMob.move(Direction.RIGHT)) {
                    type = TypeOfMovement.DONE;
                }
            }
            //надо подумать, как сделать это нормально, не дублировать код
            case ATTACK -> {
                Position positionOfMob = currentMob.getPosition();
                switch (currentMob.getType()) {
                    case PLAYER -> {
                        Optional<Map.Entry<UUID, Mob>> nearestMob = mobs.entrySet().stream()
                                .filter(entry -> entry.getValue().getPosition().equals(positionOfMob))
                                .findFirst();
                        if (nearestMob.isPresent()) {
                            Mob target = nearestMob.get().getValue();
                            currentMob.attack(target);
                            if (target.isNotAlive()) {
                                mobs.remove(target.getId());
                            }
                            type = TypeOfMovement.DONE;
                        }
                    }
                    case AGGRESSIVE -> {
                        Optional<Map.Entry<UUID, Player>> nearestMob = players.entrySet().stream()
                                .filter(entry -> entry.getValue().getPosition().equals(positionOfMob))
                                .findFirst();
                        if (nearestMob.isPresent()) {
                            Mob target = nearestMob.get().getValue();
                            currentMob.attack(target);
                            if (target.isNotAlive()) {
                                players.remove(target.getId());
                            }
                            type = TypeOfMovement.DONE;
                        }
                    }
                }

            }
            case EXIT -> {
                removePlayer(id);
                type = TypeOfMovement.EXIT;
            }
        }
        if (type == TypeOfMovement.DONE && currentMob instanceof Player
                && map.getCell(currentMob.getPosition()).getKind().equals(CellKind.END)) {
            type = TypeOfMovement.NEXT_LEVEL;
        }
        return type;
    }

    public LevelMap getMap() {
        return map;
    }
}
