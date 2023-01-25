package visualizer;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class AlgorithmDJSTR extends JMenuItem implements Algorithm{
    private String text;
    private String name;
    private Graph graph;
    private JLabel alg;
    private JLabel label;
    private JPanel status;
    private JPanel resultText;
    private MouseListener m;
    private Set<Vertex> vertexSet;

    private JMenuItemMode jMenuItemMode;

    private List<MouseListener> listOfMouseListeners;

    private boolean flag;

    MainFrame mf;

    Timer timer;
    AlgorithmDJSTR it = this;

    public AlgorithmDJSTR(String text, String name, Graph graph, MouseListener m,
                          JLabel alg, JLabel label, List<MouseListener> listOfMouseListeners,
                          Set<Vertex> vertexSet, JMenuItemMode jMenuItemMode,
                          Timer timer, MainFrame mf, JPanel status,  JPanel resultText) {
        super(text);
        this.text = text;
        this.name = name;
        this.setName(name);
        this.graph = graph;
        this.m = m;
        this.alg = alg;
        this.label = label;
        this.listOfMouseListeners = listOfMouseListeners;
        this.vertexSet = vertexSet;
        jMenuItemMode = JMenuItemMode.DIJKSTRA;
        this.timer = timer;
        this.status = status;
        this.resultText = resultText;
        setListener();
        this.mf = mf;
    }

    private void setMode() {
        mf.jm = JMenuItemMode.DIJKSTRA;
    }

    public void setListener() {

        this.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                setMode();
                mf.current = it;
                graph.removeMouseListener(m);
                if (alg != null) {
                    graph.remove(alg);
                }

                alg.setText("Please choose a starting vertex");
                alg.setName("Display");
//                label.setName("Mode");
                alg.setFont(new Font("Courier New", Font.BOLD, 15));
                alg.setBounds(5, 510, 776, 30);
                alg.setForeground(Color.WHITE);
                alg.setBackground(Color.BLACK);
                alg.setOpaque(true);
                resultText.add(alg);
                graph.updateUI();

                for (Vertex it : vertexSet
                ) {
                    for (int i = 0; i < listOfMouseListeners.size(); i++) {


                        it.removeMouseListener(listOfMouseListeners.get(i));
                    }
                    it.setEnabled(false);
                    it.setBackground(Color.BLACK);
                }
                listOfMouseListeners.add(m);

                for (Vertex it : vertexSet
                ) {
                    it.addMouseListener(m);
                    it.setEnabled(true);
                    it.choosen = false;

                }
                ;
                label.setText("Current Mode -> None");
                label.setName("Mode");
                label.setFont(new Font("Courier New", Font.BOLD, 15));
                label.setBounds(400, 0, 300, 50);
                label.setForeground(Color.WHITE);
                status.add(label);
                graph.updateUI();

            }
        });
    }

    public void act() {
        graph.removeMouseListener(m);
        if (timer != null) {
            timer.stop();
        }

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                List<Vertex> filtered = vertexSet.stream().filter(s -> s.choosen == true).collect(Collectors.toList());
                if (filtered.size() == 1) {
//                    System.out.println(filtered.get(0).getName().toString());
                    for (Vertex it : vertexSet
                    ) {
                        it.setDistance(Integer.MAX_VALUE);
                    }

                    calculateShortestPathFromSource(filtered.get(0));


                    List<Vertex> collect = vertexSet.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
                    collect.stream().map(s -> s.getName()).forEach(System.out::println);
                    collect.stream().map(s -> s.getDistance()).forEach(System.out::println);

                    StringBuilder result = new StringBuilder();
                    int count = 0;
                    for (Vertex it : collect
                    ) {
                        if (it != filtered.get(0)) {
                            String[] name = it.getName().split("\\s+");


                            if (count == 0) {
                                result.append(name[1] + "=" + it.getDistance());
                                System.out.println(name[1] + "=" + it.getDistance());
                            } else {
                                result.append(", " + name[1] + "=" + it.getDistance());
                            }
                            count++;
                        }
                    }
                    alg.setText(result.toString());

                    for (Vertex it : vertexSet
                    ) {
                        for (int i = 0; i < listOfMouseListeners.size(); i++) {


                            it.removeMouseListener(listOfMouseListeners.get(i));
                        }
                        it.setEnabled(false);
                        it.setBackground(Color.BLACK);
                    }
                }
            }
        });

        alg.setText("Please wait...");
        resultText.add(alg);
        graph.updateUI();
        timer.setRepeats(false);
        timer.start();

    }

    public static void calculateShortestPathFromSource(Vertex source) {
        source.setDistance(0);

        Set<Vertex> settledNodes = new HashSet<>();
        Set<Vertex> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Vertex currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Vertex, Integer> adjacencyPair :
                    currentNode.getNeighbors().entrySet()) {
                Vertex adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }

    }

    private static Vertex getLowestDistanceNode(Set<Vertex> unsettledNodes) {
        Vertex lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Vertex node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(Vertex evaluationNode,
                                                 Integer edgeWeigh, Vertex sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Vertex> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

}



