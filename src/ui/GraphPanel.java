package ui;

import core.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class GraphPanel extends JPanel implements ActionListener
{
    private final int WIDTH;
    private final int HEIGHT;
    private final int DIMENSION;
    private Graphics2D g2d;
    private int radiusOfVertex;
    private Algorithm alg;
    private IGraph<String> graph;
    private List<VertexRect> rects;

    private int framerate;
    private Timer ticker;

    public GraphPanel(final int width, final int height, IGraph<String> graph, int framerate)
    {
        WIDTH = width;
        HEIGHT = height;
        DIMENSION = 51;

        this.graph = graph;
        this.framerate = 1000 / framerate;
        this.ticker = new Timer(this.framerate, e -> tick());

        rects = new ArrayList<>();
        graph.addVertexRange(createConnections(createGrid()));
        setupRectangles();

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        this.alg = new Dijkstra(graph, String.format("%d %d", DIMENSION / 2, DIMENSION / 2));
        ticker.start();
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        g2d = (Graphics2D) g.create();

        try
        {
            Dijkstra.Matrix matrix = alg.getMatrix();

//            System.out.println("Tick");

            for (VertexRect rect : rects)
            {
                if (matrix.hasPrev(matrix.findVertex(rect.getId())))
                {
                    g2d.setColor(Color.BLUE);
                    g2d.fill(rect);
                    g2d.draw(rect);
                    rect.setWeight(matrix.getWeightOf(matrix.findVertex(rect.getId())));
                }
                else if (matrix.getWeightOf(matrix.findVertex(rect.getId())) == 0)
                {
                    g2d.setColor(Color.BLACK);
                    g2d.fill(rect);
                    g2d.draw(rect);
                }
                else
                {
                    g2d.setColor(Color.BLACK);
                    g2d.draw(rect);
                }
            }

//            matrix.printMatrix();
            alg.tick();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setupRectangles()
    {

        int xDrawOffset = WIDTH / DIMENSION;
        int yDrawOffset = HEIGHT / DIMENSION;
        int width = WIDTH / DIMENSION;
        int height = HEIGHT / DIMENSION;

        for (int y = 0; y < DIMENSION; ++y)
        {
            for (int x = 0; x < DIMENSION; ++x)
            {
//                Ellipse2D.Float vertex = new Ellipse2D.Float(xDrawOffset * x, yDrawOffset * y, width, height);

                // TODO create id off of the actual vertices
                VertexRect rect =  new VertexRect(xDrawOffset * x, yDrawOffset * y, width, height,
                        Integer.MAX_VALUE, String.format("%d %d", y, x));
                rects.add(rect);
            }
        }

    }

    private Vertex<String>[][] createGrid()
    {
        // Will represent x and y

        Vertex<String>[][] grid = new Vertex[DIMENSION][DIMENSION];


        for (int y = 0; y < DIMENSION; ++y)
        {
            for (int x = 0; x < DIMENSION; ++x)
            {
                Vertex<String> vert = new Vertex<String>("MegaValue", String.format("%d %d", y, x));
                grid[y][x] = vert;
                System.out.println("Created and added to grid " + vert.getName());
            }
        }

        return grid;
    }

    private Vertex<String>[][] createConnections(Vertex<String>[][] grid)
    {
        // Grid should be the same length on both x and y
        int dimension = grid.length;

        for (int y = 0; y < dimension-1; ++y)
        {
            for (int x = 0; x < dimension-1; ++x)
            {
                // Connect current vertex to vertex to the right, and below
                graph.addConnection(grid[y][x], (int)(Math.random() * 20) + 1, grid[y][x + 1]);
                graph.addConnection(grid[y][x], (int)(Math.random() * 20) + 1, grid[y + 1][x]);

//                String mess = String.format("Created con from %s to %s and %s", grid[y][x].getName(), grid[y][x + 1].getName(), grid[y + 1][x].getName());
//                System.out.println(mess);
            }
        }

        // Fixes the edges of the grid
        for (int x = 0; x < dimension - 1; ++x)
        {
            graph.addConnection(grid[dimension - 1][x], (int)(Math.random() * 20) + 1, grid[dimension - 1][x + 1]);
        }

        for (int y = 0; y < dimension - 1; ++y)
        {
            graph.addConnection(grid[y][dimension - 1], (int)(Math.random() * 20) + 1, grid[y + 1][dimension - 1]);
        }

        return grid;
    }

    public void tick()
    {
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        System.out.println("Ticked");
        tick();
    }
}
