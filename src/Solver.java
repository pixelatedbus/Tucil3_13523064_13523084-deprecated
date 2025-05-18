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

    public Board GameSolver(Board parentBoard, String algorithm, String heuristicType){
        int heuristic;
        if(algorithm.equals("GBFS")){
            heuristic = parentBoard.getHeuristicByType(heuristicType);
        } else if(algorithm.equals("UCS")){
            heuristic = parentBoard.getIteration();
        } else if (algorithm.equals("A*")){
            heuristic = parentBoard.getHeuristicByType(heuristicType) + parentBoard.getIteration();
        } else {
            return IDAStar(parentBoard, heuristicType);
        }
        parentBoard.setHeuristicCost(heuristic);
        addQueue(parentBoard);

        while (!queue.isEmpty()){
            Board currentBoard = this.queue.poll();
            currentBoard.printBoard();
            if(currentBoard.isGoalState()){
                addVisited(currentBoard);
                System.out.println("Visited: " + visited);
                System.out.println("Heuristic: " + heuristicType);
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
                if(!this.visitedStates.containsKey(key) || this.visitedStates.get(key).getHeuristicCost() > next.getHeuristicCost()){
                    int childHeuristic;
                    if(algorithm.equals("GBFS")){
                        childHeuristic = next.getHeuristicByType(heuristicType);
                    } else if(algorithm.equals("UCS")){
                        childHeuristic = next.getIteration();
                        System.out.println(childHeuristic);
                    } else {
                        childHeuristic = next.getHeuristicByType(heuristicType) + next.getIteration();
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

    public Board IDAStar(Board parentBoard, String heuristicType) {
        int threshold = parentBoard.getHeuristicByType(heuristicType);
        parentBoard.setHeuristicCost(threshold);

        while (true) {
            int minHeuristic = Integer.MAX_VALUE;
            this.queue.clear();
            this.visitedStates.clear();

            parentBoard.setHeuristicCost(threshold);
            addQueue(parentBoard);

            while (!queue.isEmpty()) {
                Board currentBoard = this.queue.poll();

                if (currentBoard.isGoalState()) {
                    return currentBoard;
                }

                String currentKey = currentBoard.getStateKey();
                if (currentBoard.getHeuristicCost() > threshold) {
                    minHeuristic = Math.min(minHeuristic, currentBoard.getHeuristicCost());
                    continue;
                }

                if (this.visitedStates.containsKey(currentKey) && this.visitedStates.get(currentKey).getHeuristicCost() <= currentBoard.getHeuristicCost()) {
                    continue;
                }

                addVisited(currentBoard);

                for (Board next : currentBoard.generatePossibleBoards()) {
                    String key = next.getStateKey();
                    if (!this.visitedStates.containsKey(key)) {
                        int h = next.getHeuristicByType(heuristicType);
                        int f = h + next.getIteration();

                        next.setHeuristicCost(f);
                        if (f > threshold) {
                            minHeuristic = Math.min(minHeuristic, f);
                            continue;
                        }

                        addQueue(next);
                    }
                }
            }

            if (minHeuristic == Integer.MAX_VALUE) {
                return null;  // No solution found
            }

            threshold = minHeuristic;
            System.out.println("New threshold: " + threshold);
        }
    }

}
