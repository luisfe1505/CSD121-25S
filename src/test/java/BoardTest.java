import lab4.game.Board;
import lab4.game.Col;
import lab4.game.Position;
import lab4.game.Row;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class BoardTest {


    @Test
    public void ToStringTest() {
        assertEquals("...\n" + "...\n" + "...\n", new Board().toString());

    }

    @Test
    public void testInitialStatus(){
        var board = new Board();
        assertEquals(Board.Status.InProgress, board.getStatus());
    }


@Test
    public void TestHorizontal(){
        var board = new Board();
        board.placeX(new Position(Row.Top, Col.Left));
        board.placeX(new Position(Row.Top, Col.Middle));
        board.placeX(new Position(Row.Top, Col.Right));
        assertEquals(Board.Status.XWins, board.getStatus());
    }

    @Test
    public void TestVertical(){
        var board = new Board();
        board.placeO(new Position(Row.Top, Col.Left));
        board.placeO(new Position(Row.Middle, Col.Left));
        board.placeO(new Position(Row.Bottom, Col.Left));
        assertEquals(Board.Status.OWins, board.getStatus());
    }
    @Test
    public void TestDraw(){
        var board = new Board();
        board.placeX(new Position(Row.Top, Col.Left));
        board.placeO(new Position(Row.Top, Col.Middle));
        board.placeX(new Position(Row.Top, Col.Right));
        board.placeO(new Position(Row.Top, Col.Left));
        board.placeX(new Position(Row.Middle, Col.Middle));
        board.placeO(new Position(Row.Middle, Col.Right));
        board.placeO(new Position(Row.Bottom, Col.Left));
        board.placeX(new Position(Row.Bottom, Col.Middle));
        board.placeO(new Position(Row.Bottom, Col.Right));

    }
    @Test
    public void testMainDiagonalWinX() {

        var board = new Board();
        board.placeX(new Position(Row.Top, Col.Left));
        board.placeX(new Position(Row.Middle, Col.Middle));
        board.placeX(new Position(Row.Bottom, Col.Right));
        assertEquals(Board.Status.XWins, board.getStatus());
    }


}



