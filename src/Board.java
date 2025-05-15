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
    private String parentState;
    private String latestMove;

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
        this.goal = new Coords(-1, -1);
        this.parentState = "";
        this.latestMove = "";
    }

    public Board(Board board){
        this.row = board.row;
        this.col = board.col;
        this.iteration = board.iteration;
        this.pieces = new HashMap<>();
        for (var entry : board.pieces.entrySet()) {
            this.pieces.put(entry.getKey(), new Piece(entry.getValue())); // pastikan Piece punya copy-constructor
        }
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

    public void setParentState(String parentState){
        this.parentState = parentState;
    }
    public String getParentState(){
        return parentState;
    }

    public void setLatestMove(String latestMove){
        this.latestMove = latestMove;
    }
    public String getLatestMove(){
        return latestMove;
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

    public void removePiece(char id){
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                if (matrix[i][j] == id){
                    matrix[i][j] = '.';
                }
            }
        }
    }

    public boolean isValidMove(Character id, boolean forward){
        Piece piece = pieces.get(id);
        if (piece == null) return false;
        int mult = forward ? 1 : -1;

        if (piece.isHorizontal()){
            for (Coords coord : piece.getPosition()){
                int newY = coord.getY() + mult;
                if (newY < 0 || newY >= col || matrix[coord.getX()][newY] != '.' || matrix[coord.getX()][newY] != id){
                    return false;
                }
            }
        } else {
            for (Coords coord : piece.getPosition()){
                int newX = coord.getX() + mult;
                if (newX < 0 || newX >= row || (matrix[newX][coord.getY()] != '.' && matrix[newX][coord.getY()] != id)){
                    return false;
                }
            }
        }
        return true;
    }

    public List<Board> generatePossibleBoards(){
        List<Board> possibleBoards = new ArrayList<>();
        HashMap<Character, Piece> tempPieces = new HashMap<>(this.pieces);
        String stateKey = getStateKey();
        for (Piece piece : tempPieces.values()){
            if (isValidMove(piece.getId(), true)){
                Board newBoard = new Board(this);
                newBoard.iteration++;
                newBoard.updateBoard();
                Piece newPiece = newBoard.pieces.get(piece.getId());
                newPiece.move(true);
                newBoard.updateBoard();
                possibleBoards.add(newBoard);
                newBoard.setParentState(stateKey);
                if (newPiece.isHorizontal()) {
                    newBoard.setLatestMove("Move " + newPiece.getId() + " right");
                } else {
                    newBoard.setLatestMove("Move " + newPiece.getId() + " down");
                }
            }
            if (isValidMove(piece.getId(), false)){
                Board newBoard = new Board(this);
                newBoard.iteration++;
                newBoard.updateBoard();
                Piece newPiece = newBoard.pieces.get(piece.getId());
                newPiece.move(false);
                newBoard.updateBoard();
                possibleBoards.add(newBoard);
                newBoard.setParentState(stateKey);
                if (newPiece.isHorizontal()) {
                    newBoard.setLatestMove("Move " + newPiece.getId() + " left");
                } else {
                    newBoard.setLatestMove("Move " + newPiece.getId() + " up");
                }
            }
        }
        return possibleBoards;
    }

    public boolean isGoalState(){
        Piece player = getPlayer();
        return player.isIntersecting(goal);
    }

    public char[][] getMatrix(){
        return matrix;
    }
}
