package roguelike.model;

import roguelike.model.util.Cell;
import roguelike.model.util.CellKind;
import roguelike.model.util.Direction;
import roguelike.util.Position;

/**
 * Class for level map
 */
public class LevelMap {
    private final int width;
    private final int height;
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

    private boolean canMove(char coord, int newCoord) {
        return coord == 'y' && newCoord < height - 1 && newCoord > 1 || coord == 'x' && newCoord < width - 1 && newCoord > 1;
    }

    /**
     * Map generation function, uses a random walk with a certain number of moves
     */
    private void generateMap() {
        int currentX = width / 2;
        int currentY = height / 2;
        startCell = new Position(currentX, currentY);
        cells[currentX][currentY].setKind(CellKind.START);

        int groundCount = (int) (0.7 * width * height);
        while (groundCount > 0) {
            Direction dir = Direction.getRandomDirection();
            switch (dir) {
                case UP -> {
                    if (canMove('y', currentY + 1)) {
                        currentY++;
                        groundCount--;
                        cells[currentX][currentY].setKind(CellKind.GROUND);
                    }
                }
                case DOWN -> {
                    if (canMove('y', currentY - 1)) {
                        currentY--;
                        groundCount--;
                        cells[currentX][currentY].setKind(CellKind.GROUND);
                    }
                }
                case LEFT -> {
                    if (canMove('x', currentX - 1)) {
                        currentX--;
                        groundCount--;
                        cells[currentX][currentY].setKind(CellKind.GROUND);
                    }
                }
                case RIGHT -> {
                    if (canMove('x', currentX + 1)) {
                        currentX++;
                        groundCount--;
                        cells[currentX][currentY].setKind(CellKind.GROUND);
                    }
                }
            }
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
}
