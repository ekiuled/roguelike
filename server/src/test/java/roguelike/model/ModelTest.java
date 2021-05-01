package roguelike.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roguelike.model.util.CellKind;
import roguelike.util.Action;
import roguelike.util.Position;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ModelTest {
    Model model;

    @BeforeEach
    void createModel() {
        model = new Model();
    }

    @Test
    public void testAddNewPlayer() {
        Player player = new Player("Test");
        model.addNewPlayer(player, 0);
        assertTrue(model.getLevel(0).getPlayers().containsValue(player));
        assertTrue(model.getLevel(0).getPlayers().containsKey(player.getId()));
    }

    @Test
    public void testExit() {
        Player player = new Player("Test");
        model.addNewPlayer(player, 0);
        model.update(player.getId(), Action.EXIT);
        assertTrue(model.getLevel(0).getPlayers().isEmpty());
    }

    @Test
    public void testMove() {
        Player player = new Player("Test");
        model.addNewPlayer(player, 0);
        Position positionBefore = player.getPosition();
        Position positionUp = new Position(positionBefore.getX(), positionBefore.getY() - 1);

        model.update(player.getId(), Action.MOVE_UP);
        Position positionAfter = player.getPosition();

        if (model.getLevel(0).getPlayers().isEmpty()) {
            // Player switched to another level
            assertEquals(model.getLevel(0).getMap().getCell(positionUp).getKind(), CellKind.END);
            assertTrue(model.getLevel(1).getPlayers().containsValue(player));
        } else {
            // Player stayed on the same level
            List<Position> possiblePositions = Arrays.asList(
                    positionBefore,
                    positionUp
            );
            assertTrue(possiblePositions.contains(positionAfter));
            if (positionAfter.equals(positionUp)) {
                // Check that player is not in the wall
                assertNotEquals(model.getLevel(0).getMap().getCell(positionUp).getKind(), CellKind.WALL);
            }
        }
    }
}
