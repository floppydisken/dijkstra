package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class GraphPanel extends JPanel
{
    private final int WIDTH;
    private final int HEIGHT;

    public GraphPanel(final int width, final int height)
    {
        WIDTH = width;
        HEIGHT = height;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g.create();
        Ellipse2D.Float vertex = new Ellipse2D.Float(0, 0, 100, 100);

        g2d.setColor(Color.GREEN);
        g2d.fill(vertex);
        g2d.draw(vertex);
    }
}
