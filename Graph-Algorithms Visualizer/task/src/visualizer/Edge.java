package visualizer;

import javax.swing.*;
import java.awt.*;

public class Edge extends JPanel {
    private int weight;

    public JLabel getWeightLabel() {
        return weightLabel;
    }

    public void setWeightLabel(JLabel weightLabel) {
        this.weightLabel = weightLabel;
    }

    private  JLabel weightLabel;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Vertex getvId1() {
        return vId1;
    }

    public void setvId1(Vertex vId1) {
        this.vId1 = vId1;
    }

    public Vertex getvId2() {
        return vId2;
    }

    public void setvId2(Vertex vId2) {
        this.vId2 = vId2;
    }

    private Vertex vId1;
    private Vertex vId2;

    public Edge getReverse() {
        return reverse;
    }

    public void setReverse(Edge reverse) {
        this.reverse = reverse;
    }

    private Edge reverse;


    public Edge() {
        super();

        setLayout(null);
        setBackground(Color.YELLOW);
        setBounds(new Rectangle(new Point((getX1()+getX2()/2),(getY1()+getY2()/2)), new Dimension(155, 155)));
        setVisible(true);
    }

    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    }





