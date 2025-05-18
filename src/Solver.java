package src;

import java.util.*;

public class Solver {
    private HashMap<String, Board> visitedStates;
    private PriorityQueue<Board> queue;
    private int visited;

    public Solver(){
        this.visitedStates = new HashMap<>();
        this.queue = new PriorityQueue<>(Comparator.comparingInt(Board::getHeuristicCost));
        this.visited = 0;
    }

    public void addVisited(Board visitedBoard){
        this.visitedStates.put(visitedBoard.getStateKey(), visitedBoard);
    }

    public int getVisited(){
        return this.visited;
    }

    public void addQueue(Board queuedBoard){
        this.queue.add(queuedBoard);
    }

    public Board GameSolver(Board parentBoard, String algorithm){
        int heuristic;
        if(algorithm.equals("GBFS")){
            heuristic = parentBoard.heuristicByRecursiveBlock();
        } else if(algorithm.equals("UCS")){
            heuristic = parentBoard.getIteration();
        } else {
            heuristic = parentBoard.heuristicByRecursiveBlock() + parentBoard.getIteration();
        }
        parentBoard.setHeuristicCost(heuristic);
        addQueue(parentBoard);

        while (!queue.isEmpty()){
            Board currentBoard = this.queue.poll();
            currentBoard.printBoard();
            if(currentBoard.isGoalState()){
                addVisited(currentBoard);
                System.out.println("Visited: " + visited);
                return currentBoard;
            }

            addVisited(currentBoard);
            visited++;

            for(Board next : currentBoard.generatePossibleBoards()){
                String key = next.getStateKey();
                if(!this.visitedStates.containsKey(key)){
                    int childHeuristic;
                    if(algorithm.equals("GBFS")){
                        childHeuristic = next.heuristicByRecursiveBlock();
                    } else if(algorithm.equals("UCS")){
                        childHeuristic = next.getIteration();
                    } else {
                        childHeuristic = next.heuristicByRecursiveBlock() + next.getIteration();
                    }
                    next.setHeuristicCost(childHeuristic);
                    addQueue(next);
                }
            }
        }

        return null;
    }

    public List<Board> getResultInOrder(Board goalBoard) {
        List<Board> path = new ArrayList<>();


        Board currentBoard = goalBoard;

        Stack<Board> reversePath = new Stack<>();

        while (currentBoard != null) {
            reversePath.push(currentBoard);

            if (currentBoard.getParentState().equals("")) {
                break;
            }

            String parentKey = currentBoard.getParentState();
            currentBoard = visitedStates.get(parentKey);

        }

        while (!reversePath.isEmpty()) {
            path.add(reversePath.pop());
        }

        return path;
    }

}
