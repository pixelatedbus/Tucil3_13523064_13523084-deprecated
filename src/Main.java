package src;

import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args){
        Board board = IO.readInput("C:\\Semester-4\\Stima\\Tucil\\Tucil3_13523064_13523084\\src\\tes.txt");
        assert board != null;
        board.updateBoard();
        board.printBoard();
        Solver solver = new Solver();
        solver.GBFSSolver(board);
        solver.printVisited();
//        int cost = board.heuristicByRecursiveBlock();
//        System.out.println("Cost: " + cost);
//        cost = board.heuristicByMaxDepth();
//        System.out.println("Cost: " + cost);
//        board.updateBoard();
//        board.printBoard();
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