package src;

import java.util.*;

public class Solver {
    private HashMap<String, Board> visitedStates;
    private PriorityQueue<Board> queue;

    public Solver(){
        this.visitedStates = new HashMap<>();
        this.queue = new PriorityQueue<>(Comparator.comparingInt(Board::getHeuristicCost));
    }

    public void addVisited(Board visitedBoard){
        this.visitedStates.put(visitedBoard.getStateKey(), visitedBoard);
    }

    public void addQueue(Board queuedBoard){
        this.queue.add(queuedBoard);
    }

    public void GBFSSolver(Board parentBoard){
        parentBoard.setHeuristicCost(parentBoard.heuristicByRecursiveBlock());
        addQueue(parentBoard);

        while (!queue.isEmpty()){
            Board currentBoard = this.queue.poll();
            System.out.println("Taken state: ");
            System.out.println(" Taken Key: " + currentBoard.getStateKey());
            currentBoard.printBoard();
            if(currentBoard.isGoalState()){
                addVisited(currentBoard);
                break;
            }

            addVisited(currentBoard);

            for(Board next : currentBoard.generatePossibleBoards()){
                String key = next.getStateKey();
                if(!this.visitedStates.containsKey(key)){
                    next.setHeuristicCost(next.heuristicByRecursiveBlock());
                    System.out.println("State: ");
                    next.printBoard();
                    System.out.println("Key: " + next.getStateKey());
                    System.out.println("Cost: " + next.heuristicByRecursiveBlock());
                    addQueue(next);
                }
            }
        }
    }

    public void printVisited() {
        for (Map.Entry<String, Board> entry : visitedStates.entrySet()) {
            System.out.println("Key: " + entry.getKey());
            System.out.println("Board: ");
            entry.getValue().printBoard();
        }
    }
}
