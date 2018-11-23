package core;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Dijkstra implements Algorithm
{
//     Failed list attempt. Gonna use normal arrays instead
    public class Matrix
    {
        private List<Vertex> vertices;
        private List<Integer> weights;
        private List<Vertex> prevs;

        public Matrix(List<Vertex> vertices)
        {
            this.vertices = vertices;

            // Might as well initialize the lists as vertex length
//            System.out.println("Vertices has length " + vertices.size());

            weights = new ArrayList<>(vertices.size());
            prevs = new ArrayList<>(vertices.size());

            // Prepare x columns
            for (Vertex vert : vertices)
            {
                weights.add(Integer.MAX_VALUE);

                prevs.add(null);
            }
        }

        public Matrix(Vertex... vertices)
        {
            this.vertices = new ArrayList<>();

            // Might as well initialize the lists as vertex length
            weights = new ArrayList<>(vertices.length);
            prevs = new ArrayList<>(vertices.length);

            for (Vertex vertex : vertices)
            {
                this.vertices.add(vertex);
                weights.add(Integer.MAX_VALUE);
                prevs.add(null);
            }
        }

        public Matrix(Vertex[][] vertices)
        {
            // y * x = dimension
            int dimension = vertices.length * vertices[0].length;
            System.out.println("dimension in matrix is " + dimension);

            weights = new ArrayList<>(dimension);
            prevs = new ArrayList<>(dimension);

            for (int y = 0; y < vertices.length; ++y)
            {
                this.vertices.addAll(Arrays.asList(vertices[y]));
            }

            // Prepare x columns
            for (int i = 0; i < dimension; ++i)
            {
                weights.add(Integer.MAX_VALUE);

                prevs.add(null);
            }
        }

        public Matrix()
        {
            this.vertices = new ArrayList<>();
            this.weights = new ArrayList<>();
            this.prevs = new ArrayList<>();
        }

        public boolean hasPrev(Vertex vertex)
        {
            int index = vertices.indexOf(vertex);

            return prevs.get(index) != null;
        }

        public synchronized Integer getWeightOf(Vertex vertex)
        {
            int index = vertices.indexOf(vertex);

            return weights.get(index);
        }

        public Vertex getPrevOf(Vertex vertex)
        {
            int index = vertices.indexOf(vertex);

            return prevs.get(index);
        }

        public synchronized void setWeightFor(Vertex vertex, int weight, Vertex prev) throws Exception
        {
             int index = vertices.indexOf(vertex);

             if (index == -1)
                 throw new Exception(vertex.getName() + " isn't in array!");

             weights.set(index, weight);
             prevs.set(index, prev);
        }

        public Vertex findVertex(String id)
        {
            boolean found = false;
            Vertex foundVertex = null;

            for (int i = 0; i < vertices.size() && !found; ++i)
            {
                if (vertices.get(i).getName().compareTo(id) == 0)
                {
                    found = true;
                    foundVertex = vertices.get(i);
                }
            }

            return foundVertex;
        }

        public void printMatrix()
        {
            System.out.println("Matrix:");
            for (int i = 0; i < vertices.size(); ++i)
            {
                System.out.println(
                        String.format("Vertex: %s; Weight: %d; Prev: %s",
                                vertices.get(i).getName(), weights.get(i),
                                (prevs.get(i) == null ? "null" : prevs.get(i).getName()))
                );

            }
        }

    }

    private IGraph graph;
    private Matrix matrix;
    private Queue<Vertex> preparedVertices;
    private Vertex source;
    private boolean running;

    public Dijkstra(IGraph graph, String from)
    {
        this.graph = graph;
        this.preparedVertices = new LinkedList<>();
        this.running = false;

        // Prepare vertices
        List<Vertex> vertices = new ArrayList<>();

        vertices.addAll(graph.getVertices());

        this.matrix = new Matrix(vertices);

        try
        {
            prepareVertices(from);
        }
        catch (VertexDoesNotExistException e)
        {
            e.printStackTrace();
        }
    }

    public void prepareVertices(String from) throws VertexDoesNotExistException
    {
        // We choose a path to start from
        source = graph.findVertex(from);

        Queue<Vertex> notVisited = new LinkedList<>();
        preparedVertices = new LinkedList<>();
        notVisited.add(source);

        // Prepare the available vetices
        while(!notVisited.isEmpty())
        {
            Vertex currVertex = notVisited.poll();
            SortedSet<Edge> edges = (SortedSet<Edge>) currVertex.getEdges();
            System.out.println(String.format("%s: %d", currVertex.getName(), edges.size()));
            preparedVertices.add(currVertex);

            for (Edge edge : edges)
            {
                if (!preparedVertices.contains(edge.getNext()))
                {
                    notVisited.add(edge.getNext());
                    preparedVertices.add(edge.getNext());
                }
            }
        }
    }

    public void finish()
    {
        source = null;
        preparedVertices.clear();
    }

    public boolean isRunning()
    {
        return running;
    }

    public Matrix getMatrix()
    {
        return matrix;
    }

    /**
     * Updates the matrix with the next values
     * @throws Exception
     */
    public void tick() throws Exception
    {
        if (preparedVertices.isEmpty())
        {
//            System.out.println("EMPTY");
            return;
        }

        Vertex currVertex = preparedVertices.poll();
//        System.out.println("Polled: " + currVertex.getName());
        // Sorted edge set
        SortedSet<Edge> edges = currVertex.getEdges();

        // First element doesn't have a predecessor
        if (currVertex.equals(source))
            matrix.setWeightFor(currVertex, 0, null);

//        edges.parallelStream()
//                .forEach((edge) ->
//                        {
//                            // This should be Integer.MAX_VALUE
//                            int weightOfNext = matrix.getWeightOf(edge.getNext());
//
//                            // This should be 5, with a -> b connection
//                            int weightOfCurr = matrix.getWeightOf(currVertex) + edge.getWeight();
//
//                            if (weightOfCurr < weightOfNext)
//                            {
//                                try
//                                {
//                                    matrix.setWeightFor(edge.getNext(), weightOfCurr, currVertex);
//                                }
//                                catch (Exception e)
//                                {
//                                    e.printStackTrace();
//                                }
//                            }
//
//
//                        }
//                );
//
        for (Edge edge : edges)
        {
//            System.out.println(
//                    String.format("OnVertex: %s, Weight: %d, Next: %s",
//                            currVertex.getName(), edge.getWeight(), edge.getNext().getName())
//            );

            // This should be Integer.MAX_VALUE
            int weightOfNext = matrix.getWeightOf(edge.getNext());

            // This should be 5, with a -> b connection
            int weightOfCurr = matrix.getWeightOf(currVertex) + edge.getWeight();

            if (weightOfCurr < weightOfNext)
                matrix.setWeightFor(edge.getNext(), weightOfCurr, currVertex);

        }
    }
}
