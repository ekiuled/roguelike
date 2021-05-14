package roguelike.model;

import roguelike.util.Position;

import java.util.UUID;

/**
 * Parent class for any object of the level
 */
public class Entity {
    private Position position;
    private final UUID id;


    public Entity() {
        id = UUID.randomUUID();
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public UUID getId() {
        return id;
    }
}
