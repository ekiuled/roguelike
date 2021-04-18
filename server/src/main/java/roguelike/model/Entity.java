package roguelike.model;

import java.util.UUID;

public class Entity {
    private Position position;
    private UUID id;

    public Entity(Position pos) {
        position = pos;
        id = UUID.randomUUID();
    }

    public Position getPosition() {
        return position;
    }

    public UUID getId() {
        return id;
    }

    public class Position {
        private int x;
        private int y;

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
