package src;

import java.util.*;

public class Main {
    public static void main(String[] args){
        Board board = IO.readInput("C:\\Semester-4\\Stima\\Tucil\\Tucil3_13523064_13523084\\src\\tes.txt");
        board.updateBoard();
        board.printBoard();
        System.out.println("Goal X: " +  board.getGoal().getX() + ", Goal Y: " + board.getGoal().getY());
        HashMap <Character, Piece> pieces = board.getPieces();
        for (Character key : pieces.keySet()) {
            pieces.get(key).debugPrint();
        }

    }
}

//compile all in src and run
//javac src/*.java