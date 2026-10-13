package moviesys;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGraph<V> implements Graph<V> {

    public enum vType {
        USER, MOVIE, GENRE, ACTOR
    }

    //all one index belongs to one vertex for all
    // Store vertices
    protected List<V> vertices = new ArrayList<>();
    //Identify nodes
    protected List<vType> types = new ArrayList<>();
    // Adjacency lists
    protected List<List<Edge>> neighbors = new ArrayList<>();

    //-----------------------------constructors-----------------------------
    //empty
    protected AbstractGraph() {

    }

    //passed with array (MIGHT REMOVE SOON)
    protected AbstractGraph(V[] vertices, vType[] type, int[][] edges) {
        for (int i = 0; i < vertices.length; i++) {
            addVertex(vertices[i], type[i]);
        }
        createAdjacencyLists(edges, vertices.length);
    }

    //passed with List (flexible, can store weight or no weight)
    protected AbstractGraph(List<V> vertices, List<vType> type, List<Edge> edges) {
        for (int i = 0; i < vertices.size(); i++) {
            addVertex(vertices.get(i), type.get(i));
        }
        createAdjacencyLists(edges, vertices.size());
    }

    //-----------------------------helper functions-----------------------------
    //create adjaceny list (array) (MIGHT REMOVE SOON)
    private void createAdjacencyLists(int[][] edges, int numberOfVertices) {
        for (int i = 0; i < edges.length; i++) {
            addEdge(edges[i][0], edges[i][1]);
        }
    }

    //create adjaceny list (list)
    private void createAdjacencyLists(List<Edge> edges, int numberOfVertices) {
        for (Edge edge : edges) {
            addEdge(edge.start, edge.end);
        }
    }
    
    //basically user cannot have and edge with gerne etc
    private boolean checkAllowedEdges(vType s, vType t) {
        if ((s == vType.USER  && t == vType.MOVIE) || (s == vType.MOVIE && t == vType.USER)) return true;
        if ((s == vType.ACTOR && t == vType.MOVIE) || (s == vType.MOVIE && t == vType.ACTOR)) return true;
        if ((s == vType.GENRE && t == vType.MOVIE) || (s == vType.MOVIE && t == vType.GENRE)) return true;
        return false;
    }

    //-----------------------------getters-----------------------------
    @Override
    public int getSize() {
        return vertices.size();
    }

    @Override
    public List<V> getVertices() {
        return vertices;
    }

    @Override
    public V getVertex(int index) {
        return vertices.get(index);
    }

    @Override
    public int getIndex(V v) {
        return vertices.indexOf(v);
    }

    @Override
    public List<Integer> getNeighbors(int index) {
        List<Integer> result = new ArrayList<>();
        for (Edge e : neighbors.get(index)) {
            result.add(e.end);
        }
        return result;
    }

    @Override
    public int getDegree(int v) {
        return neighbors.get(v).size();
    }
    
    public double getWeight(int u, int v) {
        for (Edge e : neighbors.get(u)) {
            if (e.end == v) return e.weight;
        }
        return Double.POSITIVE_INFINITY; // or 0 if you prefer "no edge"
    }

    //-----------------------------setters/adds-----------------------------
    public boolean addVertex(V vertex, vType type) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            types.add(type);
            neighbors.add(new ArrayList<Edge>());
            return true;
        }
        return false;
    }

    //!&^#@&^@&^#(@&#^(@!#^(!@&^#(!@&^WILL REMOVE 
    public boolean addVertex(V vertex) {
        // choose a sensible default or throw
        // Defaulting to MOVIE here, but you can change.
        return addVertex(vertex, vType.MOVIE);
    }

    //main addEdge fun
    protected boolean addEdge(Edge e) {
        //check if start index of vertex exist
        if (e.start < 0 || e.start > getSize() - 1) {
            throw new IllegalArgumentException("Vertex index does not exist: " + e.start);
        }
        //check if end index of vertex exist
        if (e.end < 0 || e.end > getSize() - 1) {
            throw new IllegalArgumentException("Vertex index does not exist: " + e.end);
        }

        // type-compatibility (your rules are already symmetric)
        vType startType = types.get(e.start);
        vType endType   = types.get(e.end);
        if (!checkAllowedEdges(startType, endType)) {
            throw new IllegalArgumentException(startType + " to " + endType + " connections is not allowed!");
        }

        // insert OR update weight if edge already exists  ⬅ CHANGE (upsert)
        List<Edge> list = neighbors.get(e.start);
        int idx = list.indexOf(e); // equals() compares start/end
        if (idx == -1) {
            list.add(e);
            return true;  // newly added (one direction)
        } else {
            list.get(idx).weight = e.weight; // update existing weight
            return false; // existed (one direction), weight updated
        }
    }

    //unweighted (default weight = 1)
    @Override
    public boolean addEdge(int start, int end) {
        boolean a = addEdge(new Edge(start, end));
        boolean b = addEdge(new Edge(end, start));
        return a || b; // true if at least one side was newly added
    }
    //weighted
    @Override
    public boolean addEdge(int start, int end, double weight) {
        boolean a = addEdge(new Edge(start, end, weight));
        boolean b = addEdge(new Edge(end, start, weight));
        return a || b;
    }

    //-----------------------------graph render i think? functions-----------------------------
    public void clear() {
        vertices.clear();
        types.clear();
        neighbors.clear();
    }
    
    private boolean isRatingEdge(Edge e) {
        var start = types.get(e.start);
        var end = types.get(e.end);
        return (start == vType.USER && end == vType.MOVIE) || (start == vType.MOVIE && end == vType.USER);
    }
    //supports weight printing
    @Override
    public void printEdges() {
        for (int i = 0; i < neighbors.size(); i++) {
            for (Edge e : neighbors.get(i)) {
                if (i < e.end) { // print each undirected edge once
                    if (isRatingEdge(e)) {
                        System.out.println(getVertex(i) + " -- " + getVertex(e.end) + " [" + e.weight + "]");
                    } else {
                        System.out.println(getVertex(i) + " -- " + getVertex(e.end));
                    }
                }
            }
        }
    }

    //=============================Edge class=============================
    public static class Edge {

        // start index and end index of vertices
        public int start, end;
        //rating
        public double weight;

        //-----------------------------Constructor-----------------------------
        // Without weight
        public Edge(int start, int end) {
            this.start = start;
            this.end = end;
            //assume like one path cost = 1, so default is 1
            this.weight = 1;
        }

        // With weight
        public Edge(int start, int end, double weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        //return indexs for duplication checking
        @Override
        public boolean equals(Object o) {
            if (o instanceof Edge) {
                Edge e = (Edge) o;
                return start == e.start && end == e.end;
            }
            return false;
        }
    }

    //=============================BFS=============================
    // Basic BFS (matches your interface)
    @Override
    public Tree bfs(int start) {
        List<Integer> searchOrder = new ArrayList<>();
        int[] parent = new int[vertices.size()];
        for (int i = 0; i < parent.length; i++) parent[i] = -1;

        java.util.LinkedList<Integer> queue = new java.util.LinkedList<>();
        boolean[] isVisited = new boolean[vertices.size()];

        queue.offer(start);
        isVisited[start] = true;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            searchOrder.add(u);

            for (Edge e : neighbors.get(u)) {
                if (!isVisited[e.end]) {
                    queue.offer(e.end);
                    parent[e.end] = u;
                    isVisited[e.end] = true;
                }
            }
        }
        return new Tree(start, parent, searchOrder);
    }


    // A depth limit to search 
    public Tree bfs(int start, int maxDepth) {
        List<Integer> searchOrder = new ArrayList<>();
        int[] parent = new int[vertices.size()];
        for (int i = 0; i < parent.length; i++) parent[i] = -1;

        java.util.LinkedList<Integer> queue = new java.util.LinkedList<>();
        boolean[] isVisited = new boolean[vertices.size()];
        int[] depth = new int[vertices.size()];  // NEW

        queue.offer(start);
        isVisited[start] = true;
        depth[start] = 0;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            searchOrder.add(u);

            for (Edge e : neighbors.get(u)) {
                if (!isVisited[e.end] && depth[u] < maxDepth) { // 👈 stop if depth too deep
                    queue.offer(e.end);
                    parent[e.end] = u;
                    isVisited[e.end] = true;
                    depth[e.end] = depth[u] + 1;  // track depth
                }
            }
        }
        return new Tree(start, parent, searchOrder);
    }

    //=============================Tree class (bfs searching purpose)=============================
    public class Tree {
        // starting vertex index in bfs
        private int root;
        // Store the parent of each vertex
        private int[] parent; 
        // Store the search order (frontier expanded)
        private List<Integer> searchOrder; 

        //-----------------------------constructor-----------------------------
        public Tree(int root, int[] parent, List<Integer> searchOrder) {
            this.root = root;
            this.parent = parent;
            this.searchOrder = searchOrder;
        }
        
        //-----------------------------getters-----------------------------
        public int getRoot() {
            return root;
        }

        public int getParent(int v) {
            return parent[v];
        }

        public List<Integer> getSearchOrder() {
            return searchOrder;
        }

        public int getNumberOfVerticesFound() {
            return searchOrder.size();
        }

        public List<V> getPath(int index) {
            ArrayList<V> path = new ArrayList<>();
            do {
                path.add(vertices.get(index));
                index = parent[index];
            } while (index != -1);
            return path;
        }

        //-----------------------------render i think? functions-----------------------------
        //not useful for ours but i add first
        public void printPath(int index) {
            List<V> path = getPath(index);
            System.out.print("A path from " + vertices.get(root) + " to "
                    + vertices.get(index) + ": ");
            for (int i = path.size() - 1; i >= 0; i--) {
                System.out.print(path.get(i) + " ");
            }
        }
        
        //also not useful for us
        public void printTree() {
            System.out.println("Root is: " + vertices.get(root));
            System.out.print("Edges: ");
            for (int i = 0; i < parent.length; i++) {
                if (parent[i] != -1) {
                    // Display an edge
                    System.out.print("(" + vertices.get(parent[i]) + ", "
                            + vertices.get(i) + ") ");
                }
            }
            System.out.println();
        }
    }
}
