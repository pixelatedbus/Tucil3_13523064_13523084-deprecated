package src;

import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args){
        Board board = IO.readInput("C:\\Users\\luthf\\Documents\\INSTITUT TEKNOLOGI BANDUNG\\SEMESTER 4\\Tucil3_13523064_13523084\\src\\tes.txt");
        board.updateBoard();
        board.printBoard();
        HashMap <Character, Piece> pieces = board.getPieces();
        for (Character key : pieces.keySet()) {
            pieces.get(key).debugPrint();
        }
        List<Board> visited = board.generatePossibleBoards();
        for (Board b : visited) {
            System.out.println("State: " + b.getStateKey());
            b.printBoard();
        }
    }
}

//compile all in src and run
//javac src/*.java
// javac -d ../bin *.java