import core.*;
import ui.GraphPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class Main
{
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public static void main(String[] args)
    {
        IGraph graph = new LinkedGraph(false);

        JFrame frame = new JFrame("Graph");
        frame.setContentPane(new GraphPanel(WIDTH, HEIGHT));
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.pack();
        frame.setVisible(true);

        dijkstra();
    }

    public static void dijkstra()
    {

        IGraph<String> graph = new LinkedGraph(true);

        String alphabet = "abcdefghijklmnopqrstuvxyz";
        int amount = 9;


        for (int i = 0; i < amount; ++i)
        {
            try
            {
                graph.addVertex(new Vertex<String>("Value", "" + alphabet.charAt(i)));
                System.out.println("Created " + alphabet.charAt(i));

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        try
        {
            graph.addConnection("a", 4, "b");
            graph.addConnection("a", 8, "h");
            graph.addConnection("b", 11, "h");
            graph.addConnection("b", 8, "c");
            graph.addConnection("c", 2, "i");
            graph.addConnection("c", 7, "d");
            graph.addConnection("d", 9, "e");
            graph.addConnection("d", 14, "f");
            graph.addConnection("h", 7, "i");
            graph.addConnection("h", 1, "g");
            graph.addConnection("f", 2, "g");
            graph.addConnection("f", 10, "e");

//            graph.addConnection("a", 10, "c");
//            graph.addConnection("a", 8, "d");
//            graph.addConnection("a", 5, "b");
//            graph.addConnection("b", 1, "d");
//            graph.addConnection("d", 10, "c");
        }
        catch (VertexDoesNotExistException e)
        {
            e.printStackTrace();
        }


        Iterator<Vertex> it = graph.getVertices().iterator();
//        System.out.println("printing vertex names");
        while (it.hasNext())
        {
            Vertex vertex = it.next();

//            System.out.println("VertexName: " + vertex.getName());
        }

        Algorithm alg = new Dijkstra(graph);
        try
        {
            alg.pathAlgorithm("a", "e").printMatrix();
        }
        catch(VertexDoesNotExistException vdnee)
        {
            vdnee.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
