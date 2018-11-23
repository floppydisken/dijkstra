package graph.ui;

import graph.core.Vertex;

import java.awt.geom.Rectangle2D;

public class VertexRect extends Rectangle2D.Double
{
    private int weight;
    private String id;
    public VertexRect(double x, double y, double w, double h, int weight, String id)
    {
        super(x, y, w, h);

        this.weight = weight;
        this.id = id;
    }


    public String getId()
    {
        return id;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }
}
