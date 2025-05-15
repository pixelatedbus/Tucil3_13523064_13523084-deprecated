package src;

import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args){
        Board board = IO.readInput("C:\\Users\\luthf\\Documents\\INSTITUT TEKNOLOGI BANDUNG\\SEMESTER 4\\Tucil3_13523064_13523084\\src\\tes.txt");
        board.updateBoard();
        board.printBoard();
        List<Board> s = board.generatePossibleBoards();
        for (Board b : s) {
            System.out.println("Possible Board:");
            b.printBoard();
            System.out.println("Move: " + b.getLatestMove());
            System.out.println("Parent State: " + b.getParentState());
        }
    }
}

//compile all in src and run
//javac src/*.java