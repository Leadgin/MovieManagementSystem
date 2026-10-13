package moviesys;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RecommendationStyles {

    private AbstractGraph<Object> graph;

    //-----------------------------constructor-----------------------------
    public RecommendationStyles(AbstractGraph<Object> graph) {
        this.graph = graph;
    }

    //-----------------------------RECOMMENDATION STYLES-----------------------------
    //recommend by rating WEIGHTED
    //just user and movie
    public List<Integer> recommendByRatingsWeighted(int userIdx, double minRating) {
        List<Integer> movies = new ArrayList<>();

        // walk the user’s outgoing edges
        for (AbstractGraph.Edge e : graph.neighbors.get(userIdx)) {
            if (graph.types.get(e.end) == AbstractGraph.vType.MOVIE && e.weight >= minRating) {
                movies.add(e.end);
            }
        }

        // Comparator --- helps sorting, sort by rating (weight) descending
        movies.sort(Comparator.comparingDouble(
                (Integer m) -> graph.getWeight(userIdx, m))
                .reversed()
        );

        // print (or return only; up to you)
        System.out.println("Movies liked by your peers: ");
        for (Integer m : movies) {
            System.out.println(graph.getVertex(m) + " (Rating: " + graph.getWeight(userIdx, m) + ")");
        }
        return movies;
    }

    //UNWEIGHTED
    //recommend by genre
    //user, and movies between genre
    public AbstractGraph<Object>.Tree recommendByGenres(int userIdx) {
        AbstractGraph<Object>.Tree t = graph.bfs(userIdx, 3);

        System.out.println("Movies by genres you like:");
        for (int v : t.getSearchOrder()) {
            if (graph.types.get(v) == AbstractGraph.vType.MOVIE && v != userIdx) {
                System.out.println(graph.getVertex(v));
            }
        }
        return t;
    }

    //recommend by actors
    //user, and movies between actors
    public AbstractGraph<Object>.Tree recommendByActors(int userIdx) {
        AbstractGraph<Object>.Tree t = graph.bfs(userIdx, 3);

        System.out.println("Movies by actors you like:");
        for (int v : t.getSearchOrder()) {
            if (graph.types.get(v) == AbstractGraph.vType.MOVIE && v != userIdx) {
                System.out.println(graph.getVertex(v));
            }
        }
        return t;
    }
}
