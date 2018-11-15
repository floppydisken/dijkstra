package core;

import java.util.List;
import java.util.Set;

public interface IGraph<T>
{
    /**
     * This should print out the traversal of the graph. For demonstration purposes.
     * @param method
     */
    void traverse(SearchMethod method);
    Set<Vertex> getVertices();

    /**
     * Find vertex by its id.
     * @param id
     * @return
     */
    Vertex findVertex(String id) throws VertexDoesNotExistException;

    /**
     * Add vertex with predefined connections
     * @param vertex
     * @param edges
     */
    void addConnection(Vertex<T> source, int weight, Vertex<T> destination);
    void addConnection(String source, int weight, String destination) throws VertexDoesNotExistException;

    /**
     * Add a vertex without edges. No connections.
     * @param vertex
     */
    void addVertex(Vertex<T> vertex) throws Exception;

    void addVertexRange(Vertex<T>... vertices);

    /**
     * Add edge to a given vertex
     * @param vertex
     * @param edge
     */
//    void addVertexEdge(String vertex, String edge) throws VertexDoesNotExistException;
    void rmVertex(Vertex<T> vertex);

//    Set<Vertex> getEdges(Vertex vertex);
}
