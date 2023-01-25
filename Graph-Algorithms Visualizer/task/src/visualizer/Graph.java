package visualizer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Graph extends JPanel {

    public Graph() {
        super(null, false);

    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    private List<Edge> edgeList = new ArrayList<>();

    public List<Vertex> getVertexListInGraph() {
        return vertexListInGraph;
    }

    public void setVertexListInGraph(List<Vertex> vertexListInGraph) {
        this.vertexListInGraph = vertexListInGraph;
    }

    private List<Vertex> vertexListInGraph = new ArrayList<>();

    public List<JLabel> getWeightLaibles() {
        return weightLaibles;
    }

    public void setWeightLaibles(List<JLabel> weightLaibles) {
        this.weightLaibles = weightLaibles;
    }

    private List<JLabel> weightLaibles = new ArrayList<>();

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;
        for (var edge : edgeList
        ) {
            g2.setColor(Color.GREEN);
            g2.setStroke(new BasicStroke(4f));
            g2.drawLine(edge.getX1(), edge.getY1(), edge.getX2(), edge.getY2());

        }

//        for (var vertex : vertexListInGraph
//        ) {
//            vertex.setBounds(vertex.getX(), vertex.getY(), 50, 50);
//            g2.setColor(Color.GREEN);
//            vertex.setVisible(true);
//            this.add(vertex);
//            repaint();
//        }

    }
}
//            label.setForeground(Color.RED);
//            this.add(label);
//            JLabel label = new JLabel(vertex.getCount(), SwingConstants.RIGHT);
//
//            label.setName("VertexLabel " + "count");
//            label.setFont(new Font("Courier New", Font.BOLD, 23));
//
//
//            label.setBounds(vertex.getX(), vertex.getY(), 50, 50);