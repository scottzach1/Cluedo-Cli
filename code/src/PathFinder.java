package src;

import java.util.*;

public class PathFinder {

    public Board board;

    public PathFinder(Board board) {
        this.board = board;
    }

    /**
     * checkValidPathFromString: Calculates whether a path from start to end is valid
     * given the provided number of steps.
     * Input Strings: "H3" or "G13"
     *
     * @param start the starting Cell on the map.
     * @param end the desired cell on the map.
     * @param numSteps The maximum number of steps allowed.
     * @return true valid, false otherwise.
     */
    public boolean checkValidPathFromString(String start, String end, int numSteps) {
        if (board == null) throw new RuntimeException("PathFinder does not have a Board!");
        return checkValidPath(board.getCell(start), board.getCell(end), numSteps);
    }

    /**
     * checkValidPath: Calculates whether a path from start to end is valid
     * given the provided number of steps.
     *
     * @param start the starting Cell on the map.
     * @param end the desired cell on the map.
     * @param diceRoll The maximum number of steps allowed.
     * @return true valid, false otherwise.
     */
    public boolean checkValidPath(Cell start, Cell end, int diceRoll) {

        if (end.getSprite() != null || end.getType().equals(Cell.Type.WALL)) return false;

        PriorityQueue<AStarNode> priorityQueue = new PriorityQueue<>();
        HashMap<Cell, AStarNode> previousNodes = new HashMap<>();

        AStarNode node = new AStarNode(null, start, 0, start.getDistance(end));

        priorityQueue.add(node);
        previousNodes.put(node.current, node);

        while (!priorityQueue.isEmpty()) {

            node = priorityQueue.poll();
            Cell cell = node.current;

            if (sameCell(node.current, end)) break;

            for (Cell neigh : ((cell.getRoom() != null) ? cell.getRoom().getDoors() : cell.getNeighbors().values())) {
                double distanceTravelled = node.distanceTravelled + 1;
                double heuristic = distanceTravelled + neigh.getDistance(end);

                AStarNode newStarNode = new AStarNode(cell, neigh, distanceTravelled, heuristic);

                if (previousNodes.containsKey(neigh)) {
                    AStarNode oldStarNode = previousNodes.get(neigh);

                    if  (newStarNode.heuristic < oldStarNode.heuristic) {
                        priorityQueue.remove(oldStarNode);
                        priorityQueue.add(newStarNode);
                        previousNodes.put(neigh, newStarNode);
                    }
                } else {
                    priorityQueue.add(newStarNode);
                    previousNodes.put(newStarNode.current, newStarNode);
                }
            }
        }

        if (!sameCell(node.current, end)) return false;

        Stack<Cell> path = new Stack<>();

        while (node != null) {
            path.push(node.current);
            node = previousNodes.get(node.previous);
        }

        // Path Can Be Used here if needed.
        System.out.println("steps taken: " + (path.size() - 1));
        return path.size() - 1 <= diceRoll;
    }

    /**
     * sameCell: Checks whether both cells are the same, or share the same non null room.
     * @param cell first cell to compare
     * @param target second cell to compare.
     * @return boolean true if same, false otherwise.
     */
    private boolean sameCell(Cell cell, Cell target) {
        return ((cell.getRoom() != null) && cell.getRoom() == target.getRoom()) || cell == target;
    }

    /**
     * Private Class to assist with AStarSearch.
     */
    private class AStarNode implements Comparable<AStarNode> {
        Cell previous, current;
        double distanceTravelled, heuristic;

        private AStarNode(Cell previous, Cell current, double distanceTravelled, double heuristic) {
            this.previous = previous;
            this.current = current;
            this.distanceTravelled = distanceTravelled;
            this.heuristic = heuristic;
        }

        @Override
        public int compareTo(AStarNode o) { return this.heuristic <= o.heuristic ? -1 : 1; }
    }
}
