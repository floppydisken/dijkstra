package core;

import java.util.*;

public class LinkedGraph<T> implements IGraph<T>
{
    // Verticies with their respective edges.
    private final Set<Vertex> vertices;
    private boolean isDirected;

    public LinkedGraph(boolean isDirected)
    {
        this.vertices = new HashSet<>();
        this.isDirected = isDirected;
    }

    public Set<Vertex> getVertices()
    {
        return new HashSet<>(vertices);
    }

    @Override
    public Vertex findVertex(String id) throws VertexDoesNotExistException
    {
        Vertex outVertex = null;
        boolean found = false;

        Iterator<Vertex> it = vertices.iterator();

        while(!found && it.hasNext())
        {
            outVertex = it.next();

            if (outVertex.getName().compareTo(id) == 0)
            {
                found = true;
            }
        }

        if (!found)
            throw new VertexDoesNotExistException();

        return outVertex;
    }

    @Override
    public void addConnection(Vertex<T> source, int weight, Vertex<T> destination)
    {

        source.addEdge(new Edge(weight, destination));

        if (!isDirected)
            destination.addEdge(new Edge(weight, source));
    }

    @Override
    public void addConnection(String source, int weight, String destination) throws VertexDoesNotExistException
    {
        findVertex(source).addEdge(new Edge(weight, findVertex(destination)));

        if (!isDirected)
            findVertex(destination).addEdge(new Edge(weight, findVertex(source)));

    }

    @Override
    public void addVertex(Vertex vertex) throws Exception
    {
        if (vertices.contains(vertex))
            throw new Exception("Vertex already added");

        vertices.add(vertex);
    }

    public void addVertexRange(Vertex<T>... vertices)
    {
        this.vertices.addAll(Arrays.asList(vertices));
    }
    public void addVertexRange(Vertex<T>[][] vertices)
    {
        for (int y = 0; y < vertices.length; ++y)
        {
            this.vertices.addAll(Arrays.asList(vertices[y]));
        }
    }

    public int size()
    {
        return vertices.size();
    }

    @Override
    public void rmVertex(Vertex vertex)
    {
        if (vertices.contains(vertex))
            vertices.remove(vertex);
    }
}
