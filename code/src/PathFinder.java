package src;

import java.util.*;

public class PathFinder {

    // Needed for checkValidFromString
    private Board board;

    public PathFinder(Board board) {
        this.board = board;
    }

    /**
     * findShortestPathFromString: Calculates whether a path from start to end is valid
     * given the provided number of steps.
     * Input Strings: "H3" or "G13"
     *
     * @param start the starting Cell on the map.
     * @param end the desired cell on the map.
     * @param numSteps The maximum number of steps allowed.
     * @return true valid, false otherwise.
     */
    public int findShortestPathFromString(String start, String end) {
        if (board == null) throw new RuntimeException("PathFinder does not have a Board!");
        return findShortestPath(board.getCell(start), board.getCell(end));
    }

    /**
     * findShortestPath: Calculates the shortest number of steps to a given cell.
     *
     * @param start the starting Cell on the map.
     * @param end the desired cell on the map.
     * @return int number of steps using shortest path,
     *  or Integer.MAX_VALUE if none can be found.
     */
    public int findShortestPath(Cell start, Cell end) {
        // NOTE: Commenting will be nearly identical to my 261 asg.  - (Zac Scott 300447976)

        if (end.getSprite() != null || end.getType().equals(Cell.Type.WALL)) return Integer.MAX_VALUE;

        PriorityQueue<AStarNode> priorityQueue = new PriorityQueue<>();
        HashMap<Cell, AStarNode> previousNodes = new HashMap<>();

        // Setup the first Node.
        AStarNode node = new AStarNode(null, start, 0, getDistance(start, end));
        priorityQueue.add(node);
        previousNodes.put(node.current, node);

        while (!priorityQueue.isEmpty()) { // Keeps searching until map is exhausted.
            // Grab the Node.
            node = priorityQueue.poll();
            Cell cell = node.current;

            if (sameCell(node.current, end)) break; // Breaks when path is found.

            // If in a room, iterate over its doors, else its neighbours.
            for (Cell neigh : ((cell.getRoom() != null) ? cell.getRoom().getDoors() : cell.getNeighbors().values())) {
                double distanceTravelled = node.distanceTravelled + 1;
                double heuristic = distanceTravelled + getDistance(neigh, end); // Euclidean distance.

                AStarNode newStarNode = new AStarNode(cell, neigh, distanceTravelled, heuristic);

                if (previousNodes.containsKey(neigh)) { // Node is already visited.
                    AStarNode oldStarNode = previousNodes.get(neigh);

                    if  (newStarNode.heuristic < oldStarNode.heuristic) { // If shortcut found.
                        priorityQueue.remove(oldStarNode);
                        priorityQueue.add(newStarNode);
                        previousNodes.put(neigh, newStarNode);
                    }
                } else { // Node hasn't been visited yet.
                    priorityQueue.add(newStarNode);
                    previousNodes.put(newStarNode.current, newStarNode);
                }
            }
        }

        if (!sameCell(node.current, end)) return Integer.MAX_VALUE; // Path was unable to be found. End was unreachable.

        Stack<Cell> path = new Stack<>(); // Stack of path. NOTE: I could just remember n steps. This was helpful for debugging too!

        while (node != null) { // Work our way back through the nodes.
            path.push(node.current);
            node = previousNodes.get(node.previous);
        }

        // path.forEach(cell -> System.out.println(cell.getStringCoordinates())); // DEBUGGING LINE.

        // Path Can Be Used here if needed.

        return path.size() - 1; // -1 as first node is start.
    }

    /**
     *
     * @param start
     * @param end
     * @param steps
     * @return
     */
    public boolean findExactPath(Cell start, Cell end, int steps) {
        return findExactPathHelper(new DFSNode(start, null), end, steps);
    }

    private boolean findExactPathHelper(DFSNode node, Cell end, int steps) {
        if (sameCell(node.current, end)) return node.depth == steps;

        Cell current = node.current;

        Set<Cell> neighbours = new HashSet<>(sameRoom(current, end) ? current.getRoom().getDoors() : current.getNeighbors().values());
        for (Cell neigh : neighbours) {
            if (node.current == neigh) continue;

            if (findExactPathHelper(new DFSNode(neigh, node), end, steps)) return true;
        }

        return false;
    }


    /**
     * sameCell: Checks whether both cells are the same, or share the same non null room.
     * @param cell first cell to compare
     * @param target second cell to compare.
     * @return boolean true if same, false otherwise.
     */
    private boolean sameCell(Cell cell, Cell target) {
        return sameRoom(cell, target) || cell == target;
    }

    private boolean sameRoom(Cell cell, Cell target) {
        return ((cell.getRoom() != null) && cell.getRoom() == target.getRoom());
    }

    /**
     * getDistance: Return the Euclidean distance of a Cell from another Cell.
     * (Based on Pythagorean Theorem using row and cols).
     * @param a Cell to measure distance from.
     * @param b Cell to measure distance to.
     * @return Distance between Cells.
     */
    public double getDistance(Cell a, Cell b) {
        return (Math.sqrt(Math.pow(a.getRow() - b.getRow(), 2) + Math.pow(a.getCol() - b.getCol(), 2)));
    }

    private class DFSNode {
        Cell current;
        Set<Cell> visited = new HashSet<>();
        int depth;

        private DFSNode(Cell current, DFSNode parent) {
            this.current = current;

            if (parent == null) return;

            this.visited.add(parent.current);
            this.visited.addAll(parent.visited);
            this.depth = parent.depth + 1;
        }

        private void visit(Cell cell) { visited.add(cell); }
        private boolean visited(Cell cell) { return visited.contains(cell); }
        private int getDepth() { return depth; }
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
