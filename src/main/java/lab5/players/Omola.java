package lab5.players;

import lab5.game.Board;
import lab5.game.Position;
import lab5.game.PlayerToken;

import java.util.List;

public class Omola extends Player {

    public Omola(String name) {
        super(name);
    }

    @Override
    public Position pickNextMove(Board board) {
        PlayerToken myToken = board.getNextTurnToken();
        PlayerToken oppToken = myToken.opponent();


        List<Position> emptyCells = board.getEmptyPositions();

        // Find the winning move for the current player
        for (Position pos : emptyCells) {
            Board copy = new Board(board);
            copy.placeNextToken(pos);
            if (copy.isWin(myToken)) {
                return pos;
            }
        }

        // Block the winning move for the opponent
        for (Position pos : emptyCells) {
            Board copy = new Board(board);

            copy.placeToken(pos, oppToken);
            if (copy.isWin(oppToken)) {
                // Block position
                return pos;
            }
        }

        // If there is no winner or block, simply return the first empty position.
        if (!emptyCells.isEmpty()) {
            return emptyCells.get(0);
        }

        // If there are no valid moves, return null or throw exception
        return null;
    }
}