package roguelike.util;

public class Position {
    private int x;
    private int y;

    public Position(Position that) {
        this(that.x, that.y);
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position generateRandom(int w, int h) {
        int x = (int) (Math.random() * w);
        int y = (int) (Math.random() * h);
        return new Position(x, y);
    }

    public double getDistance(Position other) {
        return Math.sqrt(Math.pow((x - other.x), 2) + Math.pow((y - other.y), 2));
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


    @Override
    public boolean equals(Object o) {
        if (o instanceof Position) {
            Position pos = (Position) o;
            return x == pos.x && y == pos.y;
        }
        return false;
    }
}

