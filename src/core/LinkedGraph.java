package core;

import java.util.*;

public class LinkedGraph<T> implements IGraph<T>
{
    // Verticies with their respective edges.
    private final Set<Vertex> vertices;
    private boolean isDirected;

    public LinkedGraph(boolean isDirected)
    {
        this.vertices = new HashSet<>();
        this.isDirected = isDirected;
    }

    @Override
    public void traverse(SearchMethod method)
    {
        switch (method)
        {
            case BREADTH_FIRST:
//                traverseBreadthFirst();
                break;
            case DEPTH_FIRST:
//                traverseDepthFirst();
                break;
            default:
                break;
        }
    }

//    private void traverseDepthFirst()
//    {
//        Stack<Vertex> workingSet = new Stack<>();
//        Stack<Vertex> visited = new Stack<>();
//
//        Vertex workingOn = (Vertex) vertices.keySet().toArray()[(int)(Math.random() * vertices.keySet().size())];
//        workingSet.add(workingOn);
//
//
//        while (!workingSet.empty())
//        {
//            Vertex vertex = workingSet.pop();
//            vertex.setVisited(true);
//            visited.add(workingOn);
////            System.out.println("Traversing: " + vertex.getName());
//            traverseDepthFirst(vertex, workingSet, visited);
////            System.out.println("Done with: " + vertex.getName());
//        }
//
//        Iterator<Vertex> it = vertices.keySet().iterator();
//
////        System.out.println("Clearing visitation");
//        while(it.hasNext())
//        {
//            Vertex vertex = it.next();
////            System.out.println("Visited " + vertex.getName() + ": " + vertex.isVisited());
//            vertex.setVisited(false);
//        }
//    }
//
//    private void traverseDepthFirst(Vertex vertex, Stack<Vertex> workingSet, Stack<Vertex> visited)
//    {
//        try
//        {
//            Thread.sleep(100);
//        }
//        catch (InterruptedException e)
//        {
//            e.printStackTrace();
//        }
//
//        Set<Vertex> edges = vertices.get(vertex);
//
//        for (Vertex edge : edges)
//        {
//            if (!edge.isVisited())
//            {
////                System.out.println(
////                        String.format("Currently visiting: %s", edge.getName()));
//                edge.setVisited(true);
//                visited.add(edge);
//                workingSet.add(edge);
//                traverseDepthFirst(edge, workingSet, visited);
//            }
//
//        }
//
//
//    }

//    private void traverseBreadthFirst()
//    {
//
//    }

    public Set<Vertex> getVertices()
    {
        return new HashSet<>(vertices);
    }

    @Override
    public Vertex findVertex(String id) throws VertexDoesNotExistException
    {
        Vertex outVertex = null;
        boolean found = false;

        Iterator<Vertex> it = vertices.iterator();

        while(!found && it.hasNext())
        {
            outVertex = it.next();

            if (outVertex.getName().compareTo(id) == 0)
            {
                found = true;
            }
        }

        if (!found)
            throw new VertexDoesNotExistException();

        return outVertex;
    }

    @Override
    public void addConnection(Vertex<T> source, int weight, Vertex<T> destination)
    {

        source.addEdge(new Edge(weight, destination));

//        if (!isDirected)
//            destination.addEdge(new Edge(destination, weight, source));
    }

    @Override
    public void addConnection(String source, int weight, String destination) throws VertexDoesNotExistException
    {
        findVertex(source).addEdge(new Edge(weight, findVertex(destination)));

        if (!isDirected)
            findVertex(destination).addEdge(new Edge(weight, findVertex(source)));

    }

    @Override
    public void addVertex(Vertex vertex) throws Exception
    {
        if (vertices.contains(vertex))
            throw new Exception("Vertex already added");

        vertices.add(vertex);
    }

    public void addVertexRange(Vertex... vertices)
    {
        this.vertices.addAll(Arrays.asList(vertices));
    }

    @Override
    public void rmVertex(Vertex vertex)
    {
        if (vertices.contains(vertex))
            vertices.remove(vertex);
    }

}
