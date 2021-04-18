package roguelike.model;

import roguelike.util.Position;

import java.util.UUID;

public class Entity {
    private Position position;
    private UUID id;


    public Entity() {
        id = UUID.randomUUID();
    }

    public Entity setPosition(Position position) {
        this.position = position;
        return this;
    }

    public Position getPosition() {
        return position;
    }

    public UUID getId() {
        return id;
    }
}
