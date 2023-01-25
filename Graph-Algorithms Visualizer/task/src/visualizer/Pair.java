package visualizer;

public class Pair <Vertex,Integer>{
    visualizer.Vertex vertex;
    Integer edge;

    public Pair(visualizer.Vertex vertex,  Integer edge) {
        this.vertex = vertex;
        this.edge = edge;
    }

    public visualizer.Vertex getVertex() {
        return vertex;
    }

    public void setVertex(visualizer.Vertex vertex) {
        this.vertex = vertex;
    }

    public Integer getEdge() {
        return edge;
    }

    public void setEdge(Integer edge) {
        this.edge = edge;
    }
}
