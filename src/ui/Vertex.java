package ui;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Vertex implements Shape
{
    @Override
    public Rectangle getBounds()
    {
        return null;
    }

    @Override
    public Rectangle2D getBounds2D()
    {
        return null;
    }

    @Override
    public boolean contains(double v, double v1)
    {
        return false;
    }

    @Override
    public boolean contains(Point2D point2D)
    {
        return false;
    }

    @Override
    public boolean intersects(double v, double v1, double v2, double v3)
    {
        return false;
    }

    @Override
    public boolean intersects(Rectangle2D rectangle2D)
    {
        return false;
    }

    @Override
    public boolean contains(double v, double v1, double v2, double v3)
    {
        return false;
    }

    @Override
    public boolean contains(Rectangle2D rectangle2D)
    {
        return false;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform affineTransform)
    {
        return null;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform affineTransform, double v)
    {
        return null;
    }
}
