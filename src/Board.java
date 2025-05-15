package src;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Board {
    private int row;
    private int col;
    private int iteration;
    private HashMap<Character, Piece> pieces;
    private Coords goal;
    private char[][] matrix;

    public Board(int row, int col){
        this.row = row;
        this.col = col;
        this.iteration = 0;
        this.pieces = new HashMap<>();
        this.matrix = new char[row][col];
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                matrix[i][j] = '.';
            }
        }
    }

    public Board(Board board){
        this.row = board.row;
        this.col = board.col;
        this.iteration = board.iteration;
        this.pieces = new HashMap<>(board.pieces);
        this.goal = new Coords(board.goal.getX(), board.goal.getY());
        this.matrix = new char[row][col];
        for (int i = 0; i < row; i++){
            System.arraycopy(board.matrix[i], 0, matrix[i], 0, col);
        }
    }

    public void setGoal(Coords goal){
        this.goal = goal;
    }

    public Coords getGoal(){
        return goal;
    }

    public HashMap<Character, Piece> getPieces(){
        return pieces;
    }

    public void addPiece(Piece piece){
        pieces.put(piece.getId(), piece);
    }

    public Piece getPlayer(){
        return pieces.get('P');
    }

    public String getStateKey(){
        StringBuilder state = new StringBuilder();
        for (Piece piece : pieces.values()){
            state.append(piece.getId()).append(":");
            for (Coords coord : piece.getPosition()){
                state.append(coord.getX()).append(":").append(coord.getY()).append(";");
            }
        }
        return state.toString();
    }

    public void printBoard(){
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }

    public void updateBoard(){
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                matrix[i][j] = '.';
            }
        }
        for (Piece piece : pieces.values()){
            for (Coords coord : piece.getPosition()){
                matrix[coord.getX()][coord.getY()] = piece.getId();
            }
        }
    }

    public boolean isValidMove(Character id, boolean forward){
        Piece piece = pieces.get(id);
        if (piece == null){
            return false;
        }
        int mult = forward ? 1 : -1;
        for (Coords coord : piece.getPosition()){
            int newX = coord.getX() + (piece.isHorizontal() ? mult : 0);
            int newY = coord.getY() + (piece.isHorizontal() ? 0 : mult);
            if (newX < 0 || newX >= row || newY < 0 || newY >= col || matrix[newX][newY] != '.'){
                return false;
            }
        }
        return true;
    }

    public List<Board> generatePossibleBoards(){
        List<Board> possibleBoards = new ArrayList<>();
        for (Piece piece : pieces.values()){
            for (Coords coord : piece.getPosition()){
                if (isValidMove(piece.getId(), true)){
                    Board newBoard = new Board(this);
                    newBoard.iteration++;
                    newBoard.updateBoard();
                    newBoard.pieces.get(piece.getId()).move(true);
                    newBoard.updateBoard();
                    possibleBoards.add(newBoard);
                }
                if (isValidMove(piece.getId(), false)){
                    Board newBoard = new Board(this);
                    newBoard.iteration++;
                    newBoard.updateBoard();
                    newBoard.pieces.get(piece.getId()).move(false);
                    newBoard.updateBoard();
                    possibleBoards.add(newBoard);
                }
            }
        }
        return possibleBoards;
    }

    public boolean isGoalState(){
        Piece player = getPlayer();
        return player.isIntersecting(goal);
    }
}
