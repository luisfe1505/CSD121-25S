package lab3.game;

public class Board {
    public static void main(String[] args) {


        char[][] board = {
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}

        };
        char[][] newboard = new char[3][3];
        for (int i = 0; i < board.length; i++) {
            //print new line
            for (int j = 0; j < board[i].length; j++) {
                System.out.printf("%3c", board[i][j]);
                if (j < board[i].length - 1) {
                    System.out.print(" |");
                }
            }
            System.out.println();
            if (i < board.length - 1) {
                System.out.println("——————————————");
            }

        }
    }


    private char[][] grid;
    private char currentPlayer;

    public Board() {
        grid = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = ' ';
            }
        }
        currentPlayer = 'X';
    }

    public void display() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(" " + grid[i][j] + " ");
                if (j < grid[i].length - 1) System.out.print("|");
            }
            System.out.println();
            if (i < grid.length - 1) System.out.println("---+---+---");
        }
    }


    public void placeMark(int row, int col) {
        validateIndices(row, col);
        if (grid[row][col] != ' ') {
            throw new IllegalArgumentException("Position taken: (" + row + "," + col + ")");
        }
        grid[row][col] = currentPlayer;
    }


    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }


    public char getCurrentPlayer() {
        return currentPlayer;
    }


    public boolean isPositionTaken(int row, int col) {
        validateIndices(row, col);
        return grid[row][col] != ' ';
    }


    public char getWinner() {
        // Rows
        for (int i = 0; i < 3; i++) {
            if (grid[i][0] != ' ' && grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2]) {
                return grid[i][0];
            }
        }
        // Columns
        for (int j = 0; j < 3; j++) {
            if (grid[0][j] != ' ' && grid[0][j] == grid[1][j] && grid[1][j] == grid[2][j]) {
                return grid[0][j];
            }
        }
        // Diagonals
        if (grid[0][0] != ' ' && grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2]) {
            return grid[0][0];
        }
        if (grid[0][2] != ' ' && grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0]) {
            return grid[0][2];
        }
        return ' ';
    }


    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] == ' ') return false;
            }
        }
        return true;
    }


    private void validateIndices(int row, int col) {
        if (row < 0 || row > 2 || col < 0 || col > 2) {
            throw new IllegalArgumentException("Indices out of range: (" + row + "," + col + ")");
        }
    }
}








// TODO: encapsulate the representation of the tictactoe board and provide instance methods to access and update it

