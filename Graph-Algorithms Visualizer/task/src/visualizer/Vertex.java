package visualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

public class Vertex extends JPanel {

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private int x;
    private int y;
    boolean choosen = false;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    List<Edge> edges = new ArrayList<>();

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    boolean enabled = false;

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    boolean visited = false;

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getDistance() {
        return distance;
    }

    private Integer distance = Integer.MAX_VALUE;

    public List<Vertex> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Vertex> shortestPath) {
        this.shortestPath = shortestPath;
    }

    private List<Vertex> shortestPath = new LinkedList<>();

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    String count;
    JLabel label;

    public Map<Vertex, Integer> getNeighbors() {
        return neighbors;
    }

    private Map<Vertex, Integer> neighbors;

    public void connect(Vertex vertex, Integer weight) {
        if (this == vertex) throw new IllegalArgumentException("Can't connect node to itself");
        this.neighbors.put(vertex, weight);
        vertex.neighbors.put(this, weight);
    }

    public Vertex(String count) {

        super();
        this.count =count;
        this.neighbors = new LinkedHashMap<>();
        setLayout(null);
        setBackground(Color.BLACK);
        this.label = new JLabel(count, SwingConstants.RIGHT);

        this.label .setName("VertexLabel " + count);
        this.label .setFont(new Font("Courier New", Font.BOLD, 40));
        this.label .setBounds(0, 0, 30, 50);
        this.label .setForeground(Color.BLACK);


        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

             if(enabled)  {
                 choosen = true;
                System.out.println(mouseEvent.getClass().toString() + "    " + this.getClass().getSimpleName());

                setBackground(Color.BLUE);
            }
        }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
        add(label);
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.GREEN);
        g.fillOval(0, 0, 50, 50);
        label.paint(g);

    }
}
