package roguelike.view;

import roguelike.model.Level;
import roguelike.model.LevelMap;
import roguelike.model.util.Cell;
import roguelike.util.ViewMessage;

import java.util.ArrayDeque;
import java.util.Queue;

public class View {
    private static final Queue<LevelView> queue = new ArrayDeque<>();

    public static LevelView getLevelView() {
        return queue.remove();
    }

    public static void addView(Level newLevel) {
        LevelMap currentMap = newLevel.getMap();
        Cell[][] currentCells = currentMap.getCells();
        int currentWight = currentMap.getWidth();
        int currentHeight = currentMap.getHeight();
        Character[][] currentView = new Character[currentWight][currentHeight];
        for (int i = 0; i < currentWight; i++) {
            for (int j = 0; j < currentHeight; j++) {
                switch (currentCells[i][j].getKind()) {
                    case WALL -> currentView[i][j] = '#';
                    case START -> currentView[i][j] = '^';
                    case END -> currentView[i][j] = '_';
                    case GROUND -> currentView[i][j] = ' ';
                }
            }
        }
        queue.add(new LevelView(newLevel.getNumber(), currentView));
    }

    public static class LevelView {
        private final int number;
        private final Character[][] view;

        public LevelView(int num, Character[][] view) {
            number = num;
            this.view = view;
        }

        public int getNumber() {
            return number;
        }

        public Character[][] getView() {
            return view;
        }

        public ViewMessage buildViewMessage() {
            return new ViewMessage(number, view);
        }

    }

}
