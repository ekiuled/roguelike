package roguelike.model.util;

/**
 * Class to describe the kind of the map cells
 */
public class Cell {
    private CellKind kind;

    public Cell() {
        kind = CellKind.WALL;
    }

    public CellKind getKind() {
        return kind;
    }

    public void setKind(CellKind kind) {
        this.kind = kind;
    }
}
