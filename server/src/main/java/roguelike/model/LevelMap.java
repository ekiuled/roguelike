package roguelike.model;

import roguelike.model.util.Cell;
import roguelike.model.util.CellKind;
import roguelike.model.util.Direction;

public class LevelMap {
    private final int width;
    private final int height;
    private Cell[][] cells;
    private Entity.Position startCell;
    private Entity.Position endCell;

    public LevelMap(int w, int h) {
        width = w;
        height = h;
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

    public Entity.Position getStartCell() {
        return startCell;
    }

    public Entity.Position getEndCell() {
        return endCell;
    }

    private boolean canMove(char coord, int newCoord) {
        return coord == 'y' && newCoord < height && newCoord > 0 || coord == 'x' && newCoord < width && newCoord > 0;
    }

    private void generateMap() {
        cells = new Cell[width][height];
        int currentX = width / 2;
        int currentY = height / 2;
        cells[currentX][currentY].setKind(CellKind.START);

        int groundCount = (int) (0.7 * width * height);
        while (groundCount > 0) {
            Direction dir = Direction.getRandomDirection();
            switch (dir) {
                case UP -> {
                    if (canMove('y', currentX + 1)) {
                        currentY++;
                        groundCount--;
                        cells[currentX][currentY].setKind(CellKind.GROUND);
                    }
                }
                case DOWN -> {
                    if (canMove('y', currentX - 1)) {
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
    }

    public Cell getCell(int i, int j) {
        if (i < width && i >= 0 && j < height && j >= 0) {
            return cells[i][j];
        }
        return null;
    }
}
