package moviesys;

import java.util.List;


public interface Graph<V> {
    //return num of vertices
    public int getSize();   
    
    //return the vertices in the graph
    public java.util.List<V> getVertices();
    
    //return the object for the specified vertex index
    public V getVertex(int index);
    
    //return the object for the specified vertex object
    public int getIndex(V v);
    
    //Return neighbors of vertex with the specified index
    public List<Integer> getNeighbors(int index);
    
    public int getDegree(int index);
    
    public void printEdges();
    
    public void clear();
    
    public boolean addVertex(V vertex);
    
    public boolean addEdge(int start, int end);
    
    boolean addEdge(int start, int end, double weight);
    
    public AbstractGraph<V>.Tree bfs(int v);
    
}
