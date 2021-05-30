package roguelike.model;

import roguelike.model.util.Cell;
import roguelike.model.util.CellKind;
import roguelike.model.util.Direction;
import roguelike.model.util.Plane;
import roguelike.util.Position;

/**
 * Class for level map
 */
public class LevelMap {
    private final int width;
    private final int height;
    private final double SCALE = 0.42;
    private final Cell[][] cells;
    private Position startCell;
    private Position endCell;

    public LevelMap(int w, int h) {
        width = w;
        height = h;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                cells[x][y] = new Cell();

        generateMap();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Position getStartCell() {
        return startCell;
    }

    public Position getEndCell() {
        return endCell;
    }

    private boolean canMove(Plane coord, int newCoord) {
        return coord == Plane.Y && newCoord < height - 1 && newCoord > 1 ||
                coord == Plane.X && newCoord < width - 1 && newCoord > 1;
    }

    /**
     * Map generation function, uses a random walk with a certain number of moves
     */
    private void generateMap() {
        int currentX = width / 2;
        int currentY = height / 2;
        startCell = new Position(currentX, currentY);
        cells[currentX][currentY].setKind(CellKind.START);

        int groundCount = (int) (SCALE * width * height);
        while (groundCount > 0) {
            Direction dir = Direction.getRandomDirection();
            switch (dir) {
                case UP -> {
                    if (canMove(Plane.Y, currentY + 1))
                        currentY++;
                }
                case DOWN -> {
                    if (canMove(Plane.Y, currentY - 1))
                        currentY--;
                }
                case LEFT -> {
                    if (canMove(Plane.X, currentX - 1))
                        currentX--;
                }
                case RIGHT -> {
                    if (canMove(Plane.X, currentX + 1))
                        currentX++;
                }
            }
            if (!cells[currentX][currentY].getKind().equals(CellKind.GROUND))
                groundCount--;
            cells[currentX][currentY].setKind(CellKind.GROUND);
        }
        cells[currentX][currentY].setKind(CellKind.END);
        endCell = new Position(currentX, currentY);
    }

    public Cell getCell(int i, int j) {
        if (i < width && i >= 0 && j < height && j >= 0) {
            return cells[i][j];
        }
        return null;
    }

    public Cell getCell(Position position) {
        return getCell(position.getX(), position.getY());
    }
}
