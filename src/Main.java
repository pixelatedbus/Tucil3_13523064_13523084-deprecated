package src;

import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args){
        Board board = IO.readInput("C:\\Semester-4\\Stima\\Tucil\\Tucil3_13523064_13523084\\src\\tes.txt");
        assert board != null;
        board.updateBoard();
        Solver solver = new Solver();
        solver.GBFSSolver(board);
        solver.printVisited();
//        List<Board> s = board.generatePossibleBoards();
//        for (Board b : s) {
//            System.out.println("Possible Board:");
//            b.printBoard();
//            System.out.println("Move: " + b.getLatestMove());
//            System.out.println("Parent State: " + b.getParentState());
//            System.out.println("goal: " + b.getGoal().getX() + " " + b.getGoal().getY());
//        }
//        Board firstBoard = s.getFirst();
//        firstBoard.updateBoard();
//        firstBoard.printBoard();
//        s = firstBoard.generatePossibleBoards();
//        for (Board b : s) {
//            System.out.println("Possible Board:");
//            b.printBoard();
//            System.out.println("Move: " + b.getLatestMove());
//            System.out.println("Parent State: " + b.getParentState());
//            System.out.println("goal: " + b.getGoal().getX() + " " + b.getGoal().getY());
//        }
    }
}

//compile all in src and run
//javac src/*.java