import core.*;
import ui.GraphPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class Main
{
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

    public static void main(String[] args)
    {
        IGraph graph = new LinkedGraph(false);

        JFrame frame = new JFrame("Graph");
        frame.setContentPane(new GraphPanel(WIDTH, HEIGHT, graph, 100));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
