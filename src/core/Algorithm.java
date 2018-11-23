package core;

import java.awt.*;
import java.util.List;

public interface Algorithm
{
    void tick() throws Exception;

    Dijkstra.Matrix getMatrix();

}
