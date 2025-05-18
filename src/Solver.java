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

    public Board GameSolver(Board parentBoard, String algorithm){
        int heuristic;
        int visited = 0;
        if(algorithm.equals("GBFS")){
            heuristic = parentBoard.heuristicByBlockCountAndDistance();
        } else if(algorithm.equals("UCS")){
            heuristic = parentBoard.getIteration();
        } else {
//            heuristic = parentBoard.heuristicByBlockCountAndDistance() + parentBoard.getIteration();
            return IDAStar(parentBoard);
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
            String currentKey = currentBoard.getStateKey();
            if(this.visitedStates.containsKey(currentKey) && this.visitedStates.get(currentKey).getHeuristicCost() <= currentBoard.getHeuristicCost()){
                continue;
            }

            addVisited(currentBoard);
            visited++;

            for(Board next : currentBoard.generatePossibleBoards()){
                String key = next.getStateKey();
                if(!this.visitedStates.containsKey(key)){
                    int childHeuristic;
                    if(algorithm.equals("GBFS")){
                        childHeuristic = next.heuristicByBlockCountAndDistance();
                    } else if(algorithm.equals("UCS")){
                        childHeuristic = next.getIteration();
                        System.out.println(childHeuristic);
                    } else {
                        childHeuristic = next.heuristicByBlockCountAndDistance() + next.getIteration();
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

    public Board IDAStar(Board parentBoard){
        int visited = 0;
        int heuristic = parentBoard.heuristicByRecursiveBlock();
        int minHeuristic = Integer.MAX_VALUE;
        parentBoard.setHeuristicCost(heuristic);
        addQueue(parentBoard);
        heuristic = 1;
        while (true){
            while (!queue.isEmpty()){
                Board currentBoard = this.queue.poll();
//                currentBoard.printBoard();
                if(currentBoard.isGoalState()){
                    addVisited(currentBoard);
                    return currentBoard;
                }
                String currentKey = currentBoard.getStateKey();
                if(this.visitedStates.containsKey(currentKey) && this.visitedStates.get(currentKey).getHeuristicCost() <= currentBoard.getHeuristicCost()){
                    continue;
                }
                if(currentBoard.getHeuristicCost() > heuristic){
                    continue;
                }

                addVisited(currentBoard);
                visited++;

                for(Board next : currentBoard.generatePossibleBoards()){
                    String key = next.getStateKey();
                    if(!this.visitedStates.containsKey(key)){
                        int childHeuristic = next.heuristicByRecursiveBlock() + next.getIteration();
                        if (childHeuristic < minHeuristic && childHeuristic > heuristic) {
                            minHeuristic = childHeuristic;
                        }
                        next.setHeuristicCost(childHeuristic);
                        addQueue(next);
                    }
                }
            }
            if(minHeuristic == heuristic){
                return null;
            }
            heuristic = minHeuristic;
            System.out.println("New heuristic: " + heuristic);
            addQueue(parentBoard);
            //delay 1 second
        }
    }
}
