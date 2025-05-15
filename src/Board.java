package src;

public class Board {
    private char[][] board;
    private int row;
    private int col;
    private int iteration;
    private Piece[] pieces;

    public Board(int rowInput, int colInput) {
        row = rowInput;
        col = colInput;
        board = new char[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = '_';
            }
        }
        iteration = 0;
    }
}
