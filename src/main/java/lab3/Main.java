package lab3;

import lab3.game.Board;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to TicTacToe!");
        boolean playAgain;
        do {
            // When a new game is started, a clean board is display
            Board boardGame = new Board();

            while (true) {
                boardGame.display();
                char winner = boardGame.getWinner();
                if (winner == 'X' || winner == 'O') {
                    System.out.println("Player " + winner + " wins!");
                    break;
                }
                if (boardGame.isFull()) {
                    System.out.println("Draw. No more moves possible.");
                    break;
                }
                char current = boardGame.getCurrentPlayer();
                int row = -1, col = -1;
                while (true) {
                    System.out.print("Player " + current + ", enter your move (row and column, 1-3 separated by space): ");
                    String line = input.nextLine().trim();
                    String[] moves = line.split("\\s+");
                    if (moves.length < 2) {
                        System.out.println("Invalid input: enter row and column.");
                        continue;
                    }
                    try {
                        row = Integer.parseInt(moves[0]) - 1;
                        col = Integer.parseInt(moves[1]) - 1;
                        if (row < 0 || row > 2 || col < 0 || col > 2) {
                            System.out.println("Out of range values. Use numbers between 1 and 3.");
                            continue;
                        }
                        if (boardGame.isPositionTaken(row, col)) {
                            System.out.println("Position taken. Choose another.");
                            continue;
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Input is not a valid number.");
                    }
                }
                boardGame.placeMark(row, col);
                boardGame.switchPlayer();
            }

            // Final Board
            System.out.println("Final board:");
            boardGame.display();

            // Asked if the user want to play again
            System.out.print("Do you want to play again? (y/n): ");
            String response = input.nextLine().trim().toLowerCase();
            playAgain = response.startsWith("y");

        } while (playAgain);

        System.out.println("Thank you for playing TicTacToe!");
        input.close();
    }
}
