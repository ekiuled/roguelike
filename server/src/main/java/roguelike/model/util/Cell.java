package roguelike.model.util;

import roguelike.model.Entity;

public class Cell extends Entity {
    private CellKind kind = CellKind.WALL;

    public Cell(Position pos, CellKind cellKind) {
        super();
        setPosition(pos);
        kind = cellKind;
    }

    public CellKind getKind() {
        return kind;
    }

    public void setKind(CellKind kind) {
        this.kind = kind;
    }
}
