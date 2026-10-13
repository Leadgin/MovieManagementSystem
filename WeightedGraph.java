package moviesys;

import java.util.List;

public class WeightedGraph<V> extends AbstractGraph<V>  {
    public WeightedGraph(){
        super();
    }
    
    //construct a graph from vertices and edges stored in arrays (MIGHT REMOVE)
    public WeightedGraph(V[] vertices, vType[] types, int[][] edges) {
        super(vertices, types, edges);
    }
    
    //Construct a graph from vertices and edges stored in list
    public WeightedGraph(List<V> vertices, List<vType> types, List<Edge> edges) {
        super(vertices, types, edges);
    }
}
