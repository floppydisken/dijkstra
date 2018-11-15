package core;

import java.util.*;

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

        public Matrix()
        {
            this.vertices = new ArrayList<>();
            this.weights = new ArrayList<>();
            this.prevs = new ArrayList<>();
        }

        public Integer getWeightOf(Vertex vertex)
        {
            int index = vertices.indexOf(vertex);

            return weights.get(index);
        }

        public Vertex getPrevOf(Vertex vertex)
        {
            int index = vertices.indexOf(vertex);

            return prevs.get(index);
        }

        public void setWeightFor(Vertex vertex, int weight, Vertex prev) throws Exception
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

    public Dijkstra(IGraph graph)
    {
        this.graph = graph;


        // Prepare vertices
        List<Vertex> vertices = new ArrayList<>();

        vertices.addAll(graph.getVertices());

        this.matrix = new Matrix(vertices);
    }

    @Override
    public Matrix pathAlgorithm(String from, String to) throws Exception, VertexDoesNotExistException
    {
        // We choose a path to start from
        Vertex source = graph.findVertex(from);
        Vertex destination = graph.findVertex(to);

        Queue<Vertex> notVisited = new LinkedList<>();
        Queue<Vertex> visited = new LinkedList<>();
        notVisited.add(source);


        // Prepare the available vetices
        while(!notVisited.isEmpty())
        {
            Vertex currVertex = notVisited.poll();
            SortedSet<Edge> edges = (SortedSet<Edge>) currVertex.getEdges();
            visited.add(currVertex);


            for (Edge edge : edges)
            {
                if (!visited.contains(edge.getNext()))
                {
                    notVisited.add(edge.getNext());
                    visited.add(edge.getNext());
                }
            }
        }

        // Use dijkstra to solve the shortest path!
        while(!visited.isEmpty())
        {

            Vertex currVertex = visited.poll();
            // Sorted edge set
            SortedSet<Edge> edges = currVertex.getEdges();

            // First element doesn't have a predecessor
            if (currVertex.equals(source))
                matrix.setWeightFor(currVertex, 0, null);

            for (Edge edge : edges)
            {
                System.out.println(
                        String.format("OnVertex: %s, Weight: %d, Next: %s",
                                currVertex.getName(), edge.getWeight(), edge.getNext().getName())
                );

                // This should be Integer.MAX_VALUE
                int weightOfNext = matrix.getWeightOf(edge.getNext());

                // This should be 5, with a -> b connection
                int weightOfCurr = matrix.getWeightOf(currVertex) + edge.getWeight();

                if (weightOfCurr < weightOfNext)
                    matrix.setWeightFor(edge.getNext(), weightOfCurr, currVertex);

            }
        }

        return matrix;
    }
}
