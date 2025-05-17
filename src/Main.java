package src;

import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args){
        Board board = IO.readInput("C:\\Users\\luthf\\Documents\\INSTITUT TEKNOLOGI BANDUNG\\SEMESTER 4\\Tucil3_13523064_13523084\\src\\tes.txt");
        assert board != null;
        board.updateBoard();
        board.printBoard();
        List<Character> stuck = board.getPiecesBlockingPiece(board.getPieces().get('P'));
        for (Character c : stuck) {
            System.out.print(c + " ");
        }
        System.out.println();
//        List<Board> s = board.generatePossibleBoards();
//        for (Board b : s) {
//            System.out.println("Possible Board:");
//            b.printBoard();
//            System.out.println("Move: " + b.getLatestMove());
//            System.out.println("Parent State: " + b.getParentState());
//            System.out.println("goal: " + b.getGoal().getX() + " " + b.getGoal().getY());
//            List<Piece> pieces = b.getAllBlocking();
//            for (Piece p : pieces) {
//                List<Character> sus = b.getPiecesBlockingPiece(p);
//                System.out.println("Blocking Piece: " + p.getId());
//                for (Character c : sus) {
//                    System.out.print(c + " ");
//                }
//                System.out.println();
//            }
//        }
    }
}

//compile all in src and run
//javac src/*.java