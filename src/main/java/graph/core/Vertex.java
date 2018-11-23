package graph.core;

import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

public class Vertex<T>
{
    private String name;
    private T value;
    private SortedSet<Edge> edges;

    public Vertex(T value, String name)
    {
        this.value = value;
        this.name = name;

        edges = new TreeSet<>();
    }

    public void addEdge(Edge edge)
    {
        edges.add(edge);
    }

    public String getName()
    {
        return name;
    }

    public SortedSet<Edge> getEdges()
    {
        return new TreeSet<>(edges);
    }
}
