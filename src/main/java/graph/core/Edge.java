package graph.core;

public class Edge implements Comparable<Edge>
{
    private int weight;
    private Vertex next;

    public Edge(int weight, Vertex next)
    {
        this.weight = weight;
        this.next = next;
    }

    public int getWeight()
    {
        return weight;
    }

    public Vertex getNext()
    {
        return next;
    }

    @Override
    public int compareTo(Edge edge)
    {
        int result = 0;

        if(getWeight() < edge.getWeight())
            result = -1;
        else if (getWeight() == edge.getWeight())
            result = 0;
        else if (getWeight() > edge.getWeight())
            result = 1;

        return result;
    }
}
