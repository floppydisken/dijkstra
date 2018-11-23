package graph.ui;

import graph.core.*;
import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphPanel extends JPanel implements ActionListener
{
    private final int WIDTH;
    private final int HEIGHT;
    private final int DIMENSION;
    private int radiusOfVertex;
    private Algorithm alg;
    private IGraph<String> graph;
    private List<VertexRect> rects;

    private int framerate;
    private Timer ticker;

    private long lastFrameUpdate;

    public GraphPanel(final int width, final int height, IGraph<String> graph, int framerate)
    {
        WIDTH = width;
        HEIGHT = height;
        DIMENSION = 91;

        this.graph = graph;
        this.framerate = 1000 / framerate;
        this.ticker = new Timer(this.framerate, e -> tick());
        this.lastFrameUpdate = System.currentTimeMillis();

        rects = new ArrayList<>();
        graph.addVertexRange(createConnections(createGrid()));
        setupRectangles();

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        this.alg = new Dijkstra(graph, String.format("%d %d", DIMENSION / 2, DIMENSION / 2));
        ticker.start();
    }

    public void paintInParallel(Graphics g) throws VertexDoesNotExistException
    {
        Dijkstra.Matrix matrix = alg.getMatrix();

        rects.parallelStream()
                .forEach((r) ->
                {
                    Graphics2D pg2d = (Graphics2D) g.create();
                    if (matrix.hasPrev(matrix.findVertex(r.getId())))
                    {
                        pg2d.setColor(Color.BLUE);
                        pg2d.fill(r);
                        pg2d.draw(r);
                        r.setWeight(matrix.getWeightOf(matrix.findVertex(r.getId())));
                    }
                    if (matrix.getWeightOf(matrix.findVertex(r.getId())) == 0)
                    {
                        pg2d.setColor(Color.BLACK);
                        pg2d.fill(r);
                        pg2d.draw(r);
                    }
                    else
                    {
                        pg2d.setColor(Color.BLACK);
                        pg2d.draw(r);
                    }

                    pg2d.dispose();

                });

    }

    public void paintWithSingleCore(Graphics g) throws VertexDoesNotExistException
    {
        Dijkstra.Matrix matrix = alg.getMatrix();

        Graphics2D g2d = (Graphics2D) g.create();

        for (VertexRect rect : rects)
        {
            if (matrix.hasPrev(matrix.findVertex(rect.getId())))
            {
                g2d.setColor(Color.BLUE);
                g2d.fill(rect);
                g2d.draw(rect);
                rect.setWeight(matrix.getWeightOf(matrix.findVertex(rect.getId())));
            }
            if (matrix.getWeightOf(matrix.findVertex(rect.getId())) == 0)
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


    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
//        Graphics2D g2d = (Graphics2D) g.create();

//        System.out.println("Painting " + (System.currentTimeMillis() - lastFrameUpdate));
//        lastFrameUpdate = System.currentTimeMillis();
        try
        {

//            System.out.println("Tick");

            // Multithreaded
            paintInParallel(g);

            // Not Multithreaded
//            paintWithSingleCore(g);

//            matrix.printMatrix();
            alg.tick();
        }
        catch (VertexDoesNotExistException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

//        g2d.dispose();

    }

    public void setupRectangles()
    {

        double xDrawOffset = WIDTH / DIMENSION;
        double yDrawOffset = HEIGHT / DIMENSION;
        double width = WIDTH / DIMENSION;
        double height = HEIGHT / DIMENSION;

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
