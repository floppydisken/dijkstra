package core;

import java.util.List;

public interface Algorithm
{
    Dijkstra.Matrix pathAlgorithm(String from, String to) throws Exception, VertexDoesNotExistException;
}
