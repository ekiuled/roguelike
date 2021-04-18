package roguelike.model;

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

    public static class Position {
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }
    }
}
