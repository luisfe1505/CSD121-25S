package lab5.players;

import lab5.game.Position;

import java.util.List;
import java.util.Random;

public class Randy extends Player {
    public Randy(String name){
        super(name);

    }
    @Override
    public Position pickNextMove(lab5.game.Board currentBoard) {
        List<Position> emptyCells = currentBoard.getEmptyCells();
        Random rand = new Random();
        int randomPosition = rand.nextInt(emptyCells.size());
        return emptyCells.get(randomPosition);
    }
}