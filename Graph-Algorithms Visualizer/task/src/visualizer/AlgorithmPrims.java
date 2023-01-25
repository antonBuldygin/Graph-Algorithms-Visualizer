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

public class AlgorithmPrims extends JMenuItem implements Algorithm {
    private String text;
    private String name;
    private Graph graph;
    private JPanel status;
    private JPanel resultText;
    private JLabel alg;
    private JLabel label;
    private MouseListener m;
    private Set<Vertex> vertexSet;

    private JMenuItemMode jMenuItemMode;

    private List<MouseListener> listOfMouseListeners;

    private boolean flag;

    MainFrame mf;

    Timer timer;
    AlgorithmPrims it = this;

    Map<Vertex, Vertex> childParent;

    public AlgorithmPrims(String text, String name, Graph graph, MouseListener m,
                          JLabel alg, JLabel label, List<MouseListener> listOfMouseListeners,
                          Set<Vertex> vertexSet, JMenuItemMode jMenuItemMode,
                          Timer timer, MainFrame mf, JPanel status, JPanel resultText) {
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
        jMenuItemMode = JMenuItemMode.PRIMS;
        this.timer = timer;
        this.status =status;
        this.resultText = resultText;

        setListener();
        this.mf = mf;
    }

    private void setMode() {
        mf.jm = JMenuItemMode.PRIMS;
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
//        alg.setText("ANTON");
        final StringBuilder stringBuilder = new StringBuilder();
        if (timer != null) {
            timer.stop();
        }
        Map<String, String> toPrint = new TreeMap<>();
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                vertexSet.stream().forEach(s->s.setVisited(false));
                run();

                Iterator<Map.Entry<Vertex, Vertex>> it = childParent.entrySet()
                        .iterator();
                int count = 0;
                while (it.hasNext()) {

                    Map.Entry<Vertex, Vertex> pair = it.next();
                    String[] child = pair.getKey().getName().split("\\s+");
                    String[] parent = pair.getValue().getName().split("\\s+");

                    if (!child[1].equals(parent[1])){toPrint.put(child[1], parent[1]);}}

                Iterator<Map.Entry<String, String>> simbols = toPrint.entrySet()
                        .iterator();

                while (simbols.hasNext()) {

                    Map.Entry<String, String> pair = simbols.next();
                    if (count != 0) {
                        stringBuilder.append(", " + pair.getKey() + "=" + pair.getValue());
                        count++;
                    }
                    if (count == 0) {
                        stringBuilder.append(pair.getKey() + "=" + pair.getValue());
                        count++;
                    }
                }
                alg.setText(stringBuilder.toString());
                System.out.println(stringBuilder.toString());
                for (Vertex its : vertexSet
                ) {
                    for (int i = 0; i < listOfMouseListeners.size(); i++) {


                        its.removeMouseListener(listOfMouseListeners.get(i));
                    }
                    its.setEnabled(false);
                    its.setBackground(Color.BLACK);
                }
            }


        });


//        alg.setText("ANTON");
        alg.setText("Please wait...");
       resultText.add(alg);
        graph.updateUI();
        timer.setRepeats(false);
        timer.start();

    }

    public void run() {

        childParent = new HashMap<>();


        if (vertexSet.size() > 0) {
            List<Vertex> filtered = vertexSet.stream().filter(s -> s.choosen == true).collect(Collectors.toList());
            if (filtered.size() == 1) {
                {
                    Vertex parent = filtered.get(0);
                    while (isDisconnected()) {
                        int nextMinimum = Integer.MAX_VALUE;
                        Vertex nextVertex = filtered.get(0);
                        for (Vertex vertex : vertexSet) {
                            if (vertex.isVisited()) {
                                Pair<Vertex, Integer> candidate = nextMinimum(vertex);
                                if (candidate.getEdge() < nextMinimum) {
                                    nextMinimum = candidate.getEdge();
                                    nextVertex = candidate.getVertex();
                                    parent = vertex;
                                }
                            }
                        }
//                        nextMinimum.setIncluded(true);
                        nextVertex.setVisited(true);
                        childParent.put(nextVertex, parent);
                    }
                }

            }
        }
    }

    private boolean isDisconnected() {
        for (Vertex vertex : vertexSet) {
            if (!vertex.isVisited()) {
                return true;
            }
        }
        return false;
    }

    public Pair<Vertex, Integer> nextMinimum(Vertex vertex) {
        int nextMinimum = Integer.MAX_VALUE;
        Vertex nextVertex = vertex;
        Iterator<Map.Entry<Vertex, Integer>> it = vertex.getNeighbors().entrySet()
                .iterator();
        while (it.hasNext()) {
            Map.Entry<Vertex, Integer> pair = it.next();
            if (!pair.getKey().isVisited()) {

                if (pair.getValue() < nextMinimum) {
                    nextMinimum = pair.getValue();
                    nextVertex = pair.getKey();
                }
            }
        }

        return new Pair<>(nextVertex, nextMinimum);
    }
}
