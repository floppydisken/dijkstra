package core;

import java.util.List;
import java.util.Set;

public interface IGraph<T>
{
    Set<Vertex> getVertices();

    /**
     * Find vertex by its id.
     * @param id
     * @return
     */
    Vertex findVertex(String id) throws VertexDoesNotExistException;

    /**
     * Add vertex with predefined connections
     * @param source
     */
    void addConnection(Vertex<T> source, int weight, Vertex<T> destination);
    void addConnection(String source, int weight, String destination) throws VertexDoesNotExistException;

    /**
     * Add a vertex without edges. No connections.
     * @param vertex
     */
    void addVertex(Vertex<T> vertex) throws Exception;

    void addVertexRange(Vertex<T>... vertices);
    void addVertexRange(Vertex<T>[][] vertices);

    int size();

    /**
     * Add edge to a given vertex
     * @param vertex
     */
//    void addVertexEdge(String vertex, String edge) throws VertexDoesNotExistException;
    void rmVertex(Vertex<T> vertex);

//    Set<Vertex> getEdges(Vertex vertex);
}
