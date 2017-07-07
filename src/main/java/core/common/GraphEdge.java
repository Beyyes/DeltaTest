package core.common;

/**
 * Created by stefanie on 09/01/2017.
 */

public class GraphEdge {

    private int edgeStart;
    private int edgeEnd;

    public GraphEdge (int start, int end) {
        edgeStart = start;
        edgeEnd = end;
    }

    public int getEdgeStart() {
        return edgeStart;
    }

    public int getEdgeEnd() {
        return edgeEnd;
    }

    public void setEdgeStart(int start) {
        edgeStart = start;
    }

    public void setEdgeEnd(int end) {
        edgeEnd = end;
    }

}
