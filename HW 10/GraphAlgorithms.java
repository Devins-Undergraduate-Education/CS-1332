import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Devin Fromond
 * @userid dfromond3
 * @GTID 903761713
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("start vertex or graph cannot be null");
        }
        
        List<Vertex<T>> visited = new ArrayList<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        Set<Vertex<T>> discovered = new HashSet<>();

        queue.add(start);
        discovered.add(start);

        while (!queue.isEmpty()) {
            Vertex<T> current = queue.poll();
            visited.add(current);
            for (VertexDistance<T> neighbor : graph.getAdjList().get(current)) {
                if (!discovered.contains(neighbor.getVertex())) {
                    queue.add(neighbor.getVertex());
                    discovered.add(neighbor.getVertex());
                }
            }
        }
        return visited;
    }
    

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("start cannot be null.");
        } else if (graph == null) {
            throw new IllegalArgumentException("graph cannot be null");
        } else if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("start is not in the group");
        }

        Set<Vertex<T>> visited = new HashSet<>();
        List<Vertex<T>> result = new ArrayList<>();

        dfs(start, graph, visited, result);

        return result;

    }

    /**
     * Recursive helper method for the main dfs method
     * @param <T> which is the generic typing of the data
     * @param vertex which is the vertex
     * @param graph which is the graph
     * @param visited which is the visited node
     * @param result which is the nodes visited and the "path"
     */
    private static <T> void dfs(Vertex<T> vertex, Graph<T> graph, Set<Vertex<T>> visited, List<Vertex<T>> result) {
        result.add(vertex);
        visited.add(vertex);

        for (VertexDistance<T> distance : graph.getAdjList().get(vertex)) {
            if (!visited.contains(distance.getVertex())) {
                dfs(distance.getVertex(), graph, visited, result);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("start cannot be null.");
        } else if (graph == null) {
            throw new IllegalArgumentException("graph cannot be null");
        } else if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("start is not in the group");
        }

        Queue<VertexDistance<T>> queue = new PriorityQueue<>();
        Map<Vertex<T>, Integer> result = new HashMap<>();

        for (Vertex<T> vertex : graph.getAdjList().keySet()) {
            if (vertex.equals(start)) {
                result.put(vertex, 0);
            } else {
                result.put(vertex, Integer.MAX_VALUE);
            }
        }

        queue.add(new VertexDistance<>(start, 0));

        while (!queue.isEmpty()) {
            VertexDistance<T> tmp = queue.remove();

            for (VertexDistance<T> vDistance : graph.getAdjList().get(tmp.getVertex())) {
                int distance = tmp.getDistance() + vDistance.getDistance();

                if (result.get(vDistance.getVertex()) > distance) {
                    result.put(vDistance.getVertex(), distance);
                    queue.add(new VertexDistance<>(vDistance.getVertex(), distance));
                }
            }
        }
        return result;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use PriorityQueue, java.util.Set, and any class that 
     * implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("start cannot be null.");
        } else if (graph == null) {
            throw new IllegalArgumentException("graph cannot be null");
        } else if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("start is not in the group");
        }

        PriorityQueue<Edge<T>> queue = new PriorityQueue<>();
        Set<Vertex<T>> visited = new HashSet<>();
        Set<Edge<T>> edgeset = new HashSet<>();
        
        for (Edge<T> edge : graph.getEdges()) {
            if (edge.getU().equals(start)) {
                queue.add(edge);
            }
        }

        visited.add(start);

        while (!queue.isEmpty()) {
            Edge<T> temp = queue.remove();

            if (!visited.contains(temp.getV())) {
                edgeset.add(new Edge<>(temp.getU(), temp.getV(), temp.getWeight()));
                visited.add(temp.getV());

                for (Edge<T> edge : graph.getEdges()) {
                    if (temp.getV().equals(edge.getU())) {
                        edgeset.add(new Edge<>(temp.getV(), temp.getU(), temp.getWeight()));
                        queue.add(edge);
                    }
                }
            }
        }

        if ((edgeset.size() / 2) != graph.getVertices().size() - 1) {
            return null;
        } 

        return edgeset;
    }
}