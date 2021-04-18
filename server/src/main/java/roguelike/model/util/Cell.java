package roguelike.model.util;

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
