package visualizer;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MainFrame extends JFrame {

    private Graph graph;
    private JPanel controlPanel = new JPanel();
    ;
    private JLabel label;

    private JPanel statusPanel;

    private JPanel displayPanel;
    private JLabel alg = new JLabel("Please choose a starting vertex", SwingConstants.CENTER);


    Vertex from = null;
    Vertex to = null;
    List<MouseListener> listOfMouseListeners = new ArrayList<>();
    JMenuItemMode jm;

    Algorithm current;



    M m = new M();

    private Set<Vertex> vertexSet = new HashSet<>();

    private Timer timer;

    public MainFrame() {
        setTitle("Graph-Algorithms Visualizer");
        setName("Graph-Algorithms Visualizer");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        graph = new Graph();
        graph.setLayout(null);
        graph.setName("Graph");

        graph.setBackground(Color.BLACK);

        controlPanel.setName("control");

        statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        statusPanel.setName("status");
        displayPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        displayPanel.setName("display");
        displayPanel.setBackground(Color.BLACK);
;
        statusPanel.setBackground(Color.BLACK);

        add(statusPanel, BorderLayout.NORTH);
        add(graph, BorderLayout.CENTER);
        add(displayPanel, BorderLayout.SOUTH);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setName("MenuBar");

        JMenu mode = new JMenu("Mode");
        mode.setName("Mode");
        mode.setMnemonic(KeyEvent.VK_F);

        JMenu fileMode = new JMenu("File");
        fileMode.setName("File");
        fileMode.setMnemonic(KeyEvent.VK_C);

        JMenu algorithms = new JMenu("Algorithms");
        algorithms.setName("Algorithms");
        algorithms.setMnemonic(KeyEvent.VK_A);


        JMenuItem newItem = new JMenuItem("New");
        newItem.setName("New");

        JMenuItem exit = new JMenuItem("Exit");
        exit.setName("Exit");


        JMenuItem addVertex = new JMenuItem("Add a Vertex");
        addVertex.setName("Add a Vertex");

        JMenuItem addEdge = new JMenuItem("Add an Edge");
        addEdge.setName("Add an Edge");


        JMenuItem removeVertex = new JMenuItem("Remove a Vertex");
        removeVertex.setName("Remove a Vertex");

        JMenuItem removeEdge = new JMenuItem("Remove an Edge");
        removeEdge.setName("Remove an Edge");


        JMenuItem none = new JMenuItem("None");
        none.setName("None");

        JMenuItem bfs = new JMenuItem("Breadth-First Search");
        bfs.setName("Breadth-First Search");

        JMenuItem dfs = new JMenuItem("Depth-First Search");
        dfs.setName("Depth-First Search");

        label = new JLabel("Add a Vertex");
        label.setName("Mode");
        AlgorithmDJSTR djr = new AlgorithmDJSTR("Dijkstra's Algorithm", "Dijkstra's Algorithm",
                graph, m, alg, label, listOfMouseListeners, vertexSet, JMenuItemMode.DIJKSTRA, timer, this, statusPanel, displayPanel);

        AlgorithmPrims prims = new AlgorithmPrims("Prim's Algorithm", "Prim's Algorithm",
                graph, m, alg, label, listOfMouseListeners, vertexSet, JMenuItemMode.PRIMS, timer, this, statusPanel, displayPanel);

        mode.add(addVertex);
        mode.add(addEdge);
        mode.add(removeVertex);
        mode.add(removeEdge);
        mode.add(none);


        fileMode.add(newItem);
        fileMode.add(exit);

        algorithms.add(dfs);
        algorithms.add(bfs);
        algorithms.add(djr);
        algorithms.add(prims);


        menuBar.add(fileMode);
        menuBar.add(mode);
        menuBar.add(algorithms);

        setJMenuBar(menuBar);
        init();

        addVertex.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                jm = JMenuItemMode.ADDVERTEX;
                for (Vertex it : vertexSet
                ) {
                    for (int i = 0; i < listOfMouseListeners.size(); i++) {


                        it.removeMouseListener(listOfMouseListeners.get(i));
                    }
                    it.setEnabled(false);
                    it.setBackground(Color.BLACK);
                }
                graph.removeMouseListener(m);

                graph.addMouseListener(m);



                label.setText("Current Mode -> Add a Vertex");
                label.setFont(new Font("Courier New", Font.BOLD, 15));
                label.setBounds(400, 0, 300, 50);
                label.setForeground(Color.WHITE);

                graph.updateUI();
                displayPanel.remove(alg);

                statusPanel.add(label);

            }
        });


        addEdge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                listOfMouseListeners.add(m);
                jm = JMenuItemMode.ADDEDGE;

                for (Vertex it : vertexSet
                ) {
                    it.addMouseListener(m);
                    it.setEnabled(true);
                    it.choosen = false;

                }
                label.setText("Current Mode -> Add an Edge");

                label.setFont(new Font("Courier New", Font.BOLD, 15));
                label.setBounds(400, 0, 300, 50);
                label.setForeground(Color.WHITE);
                statusPanel.add(label);

                graph.updateUI();

                graph.removeMouseListener(m);
                displayPanel.remove(alg);
            }
        });
        none.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                jm = JMenuItemMode.NONE;

                label.setText("None");

                label.setFont(new Font("Courier New", Font.BOLD, 15));
                label.setBounds(400, 0, 300, 50);
                label.setForeground(Color.WHITE);

                statusPanel.add(label);
                graph.updateUI();

                graph.removeMouseListener(m);
                displayPanel.remove(alg);
            }
        });

        removeVertex.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                listOfMouseListeners.add(m);
                jm = JMenuItemMode.REMOVEVERTEX;

                for (Vertex it : vertexSet
                ) {
                    it.addMouseListener(m);
                    it.setEnabled(true);
                    it.choosen = false;

                }

                label.setText("Current Mode -> Remove a Vertex");
                label.setFont(new Font("Courier New", Font.BOLD, 15));
                label.setBounds(400, 0, 300, 50);
                label.setForeground(Color.WHITE);

                statusPanel.add(label);
                graph.updateUI();

                graph.removeMouseListener(m);
                displayPanel.remove(alg);
            }
        });

        removeEdge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                jm = JMenuItemMode.REMOVEEDGE;

                graph.removeMouseListener(m);

                graph.addMouseListener(m);

                for (Vertex it : vertexSet
                ) {
                    for (int i = 0; i < listOfMouseListeners.size(); i++) {


                        it.removeMouseListener(listOfMouseListeners.get(i));
                    }
                    it.setEnabled(false);
                    it.setBackground(Color.BLACK);
                }


                label.setText("Current Mode -> Remove an Edge");
                label.setFont(new Font("Courier New", Font.BOLD, 15));
                label.setBounds(400, 0, 300, 50);
                label.setForeground(Color.WHITE);

                statusPanel.add(label);
                graph.updateUI();
                displayPanel.remove(alg);

            }
        });


        newItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                jm = JMenuItemMode.NEWITEM;
                graph.getEdgeList().clear();
                graph.removeAll();
                graph.repaint();
                vertexSet.clear();
                init();

            }
        });


        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                jm = JMenuItemMode.EXIT;
                listOfMouseListeners.clear();
                graph.remove(label);
                graph.remove(alg);
                dispose();
                System.exit(0);

            }
        });

        bfs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                displayPanel.remove(alg);
                graph.removeMouseListener(m);
                if (alg != null) {
                    graph.remove(alg);
                }

                alg.setText("Please choose a starting vertex");
                alg.setName("Display");
                alg.setFont(new Font("Courier New", Font.BOLD, 15));
                alg.setBounds(15, 510, 776, 30);
                alg.setForeground(Color.WHITE);
                alg.setBackground(Color.BLACK);
                alg.setOpaque(true);
                displayPanel.add(alg);
                graph.updateUI();

                for (Vertex its : vertexSet
                ) {
                    for (int i = 0; i < listOfMouseListeners.size(); i++) {


                        its.removeMouseListener(listOfMouseListeners.get(i));
                    }
                    its.setEnabled(false);
                    its.setBackground(Color.BLACK);
                }

                listOfMouseListeners.add(m);


                jm = JMenuItemMode.BFS;
                for (Vertex it : vertexSet
                ) {
                    it.addMouseListener(m);
                    it.setEnabled(true);
                    it.choosen = false;

                }
                ;
                label.setText("Current Mode -> None");
                label.setFont(new Font("Courier New", Font.BOLD, 15));
                label.setBounds(400, 0, 300, 50);
                label.setForeground(Color.WHITE);
                statusPanel.add(label);
                graph.updateUI();

            }
        });

        dfs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                jm = JMenuItemMode.DFS;
                displayPanel.remove(alg);
                if (alg != null) {
                    graph.remove(alg);
                }

                alg.setText("Please choose a starting vertex");
                alg.setName("Display");
                alg.setFont(new Font("Courier New", Font.BOLD, 15));
                alg.setBounds(5, 510, 776, 30);
                alg.setForeground(Color.WHITE);
                alg.setBackground(Color.BLACK);
                alg.setOpaque(true);
                displayPanel.add(alg);
                graph.updateUI();

                for (Vertex its : vertexSet
                ) {
                    for (int i = 0; i < listOfMouseListeners.size(); i++) {


                        its.removeMouseListener(listOfMouseListeners.get(i));
                    }
                    its.setEnabled(false);
                    its.setBackground(Color.BLACK);
                }
                graph.removeMouseListener(m);
                listOfMouseListeners.add(m);
                for (Vertex it : vertexSet
                ) {
                    it.addMouseListener(m);
                    it.setEnabled(true);
                    it.choosen = false;

                }
                label.setText("Current Mode -> None");
                label.setFont(new Font("Courier New", Font.BOLD, 15));
                label.setBounds(400, 0, 300, 50);
                label.setForeground(Color.WHITE);
                statusPanel.add(label);
                graph.updateUI();

            }
        });


        setVisible(true);
        add(label);
        alg.setName("Display");
        add(alg);


    }


    public class M implements MouseListener {

        public static StringBuilder search(Vertex start) {
            Queue<Vertex> queue = new ArrayDeque<>();
            queue.add(start);

            Vertex currentNode;
            Set<Vertex> alreadyVisited = new HashSet<>();
            StringBuilder stringBuilder = new StringBuilder("BFS : ");
            boolean flag = true;

            while (!queue.isEmpty()) {
                currentNode = queue.remove();
                System.out.println("Visited node with value: {}" + currentNode.getName());


                String[] vertexTo = currentNode.getName().split("\\s+");

                String arrow = " -> ";
                if (flag) {
                    stringBuilder.append(vertexTo[1]);
                    flag = false;
                } else {
                    stringBuilder.append(arrow + vertexTo[1]);
                }


                alreadyVisited.add(currentNode);

                currentNode.getNeighbors();

                Map<Vertex, Integer> vertexIntegerMap = sortByValueBfs(currentNode.getNeighbors());
                Set<Vertex> vertices = vertexIntegerMap.keySet();

                queue.addAll(vertices);
                queue.removeAll(alreadyVisited);
            }
            return stringBuilder;
        }

        public StringBuilder dfsWithoutRecursion(Vertex start) {
            StringBuilder stringBuilder = new StringBuilder("DFS : ");
            boolean flag = true;
            Stack<Vertex> stack = new Stack<>();
            Map<Vertex, Boolean> isVisited = new HashMap<>();
            stack.push(start);
            while (!stack.isEmpty()) {
                Vertex current = stack.pop();
                if (isVisited.isEmpty() || !isVisited.containsKey(current)) {
                    isVisited.put(current, true);
                    System.out.println("DFS " + current);

                    String[] vertexTo = current.getName().split("\\s+");

                    String arrow = " -> ";
                    if (flag) {
                        stringBuilder.append(vertexTo[1]);
                        flag = false;
                    } else {
                        stringBuilder.append(arrow + vertexTo[1]);
                    }

                    Set<Vertex> neighbors = current.getNeighbors().keySet();

                    for (Vertex it : neighbors
                    ) {
//                        System.out.println("Unsorted  "+it.getName());

                    }

                    Map<Vertex, Integer> vertexIntegerMap = sortByValue(current.getNeighbors());
                    Set<Vertex> vertices = vertexIntegerMap.keySet();

                    for (Vertex it : vertices
                    ) {
//                        System.out.println("Sorted  "+it.getName());

                    }

                    for (Vertex dest : vertices) {
                        if (isVisited.containsKey(dest)) {
                            if (!isVisited.get(dest)) {
                                stack.push(dest);
                            }
                        } else {
                            stack.push(dest);
                        }
                    }
                }
            }
            return stringBuilder;
        }


        private static Map<Vertex, Integer> sortByValue(Map<Vertex, Integer> unsortMap) {

            List<Map.Entry<Vertex, Integer>> list =
                    new LinkedList<Map.Entry<Vertex, Integer>>(unsortMap.entrySet());

            Collections.sort(list, new Comparator<Map.Entry<Vertex, Integer>>() {
                public int compare(Map.Entry<Vertex, Integer> o1,
                                   Map.Entry<Vertex, Integer> o2) {
                    return (o2.getValue()).compareTo(o1.getValue());
                }
            });

            Map<Vertex, Integer> sortedMap = new LinkedHashMap<Vertex, Integer>();
            for (Map.Entry<Vertex, Integer> entry : list) {
                sortedMap.put(entry.getKey(), entry.getValue());
            }
            return sortedMap;
        }

        private static Map<Vertex, Integer> sortByValueBfs(Map<Vertex, Integer> unsortMap) {

            List<Map.Entry<Vertex, Integer>> list =
                    new LinkedList<Map.Entry<Vertex, Integer>>(unsortMap.entrySet());

            Collections.sort(list, new Comparator<Map.Entry<Vertex, Integer>>() {
                public int compare(Map.Entry<Vertex, Integer> o1,
                                   Map.Entry<Vertex, Integer> o2) {
                    return (o1.getValue()).compareTo(o2.getValue());
                }
            });

            Map<Vertex, Integer> sortedMap = new LinkedHashMap<Vertex, Integer>();
            for (Map.Entry<Vertex, Integer> entry : list) {
                sortedMap.put(entry.getKey(), entry.getValue());
            }
            return sortedMap;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

            if (jm.equals(JMenuItemMode.ADDVERTEX)) {
                System.out.println(e.getX() + " =X; Y= " + e.getY());
                ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                executor.submit(() -> {
//                    graph.add(controlPanel);
//                    controlPanel.setLocation(e.getX(), e.getY());

                    String result = JOptionPane.showInputDialog(graph, "Enter the Vertex ID (Should be 1 char):", "Vertex"
                            , JOptionPane.QUESTION_MESSAGE);
                    if (result != null && !result.equals(" ") && JOptionPane.OK_OPTION == 0 && result.length() == 1) {
                        Vertex vertex = new Vertex(result);
                        vertex.setName("Vertex " + result);
                        vertex.setBounds(e.getX() - 25, e.getY() - 25, 50, 50);
//                        vertex.setVisible(true);
                        vertexSet.add(vertex);
                        graph.getVertexListInGraph().clear();
                        graph.getVertexListInGraph().addAll(vertexSet);
                        remove(alg);
                        vertex.setX(e.getX() - 25);
                        vertex.setY(e.getY() - 25);

                        graph.add(vertex);
                        statusPanel.add(label);

                    } else if (result == null) {
                        return;
                    } else if (result == null || result.equals(" ") || JOptionPane.OK_OPTION != 0 || result.length() != 1) {
                        mouseClicked(e);
                    }
                    graph.updateUI();
                });
                ;
            }
            if (jm.equals(JMenuItemMode.ADDEDGE)) {

                System.out.println("ADDEDGE " + e.getX() + " =X;Y= " + e.getY());

                List<Vertex> filtered = vertexSet.stream().filter(s -> s.choosen == true).collect(Collectors.toList());

                if (filtered.size() == 1 && from == null) {
                    from = filtered.get(0);
                    filtered.get(0).choosen = false;
                    System.out.println(from);
                }

                if (filtered.size() == 1 && to == null && from != null) {

                    List<Vertex> destinationVertrix = vertexSet.stream().filter(s -> s.choosen == true).collect(Collectors.toList());
                    System.out.println(destinationVertrix);

                    if (destinationVertrix.size() != 0) {
                        destinationVertrix.get(0).choosen = false;
                        to = destinationVertrix.get(0);
                    }
                }

                if (filtered.size() == 1 && to != null && from != null) {


                    String sult = JOptionPane.showInputDialog(graph, "Enter Weight:", "Input"
                            , JOptionPane.QUESTION_MESSAGE);
                    to.add(controlPanel);
                    controlPanel.setLocation(e.getX(), e.getY());

                    if (JOptionPane.OK_CANCEL_OPTION == 2 && to != null && from != null) {
                        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

                        if (sult == null) {
                            from.setBackground(Color.BLACK);
                            to.setBackground(Color.BLACK);
                            from = null;
                            to = null;
                        } else {
                            while (!pattern.matcher(sult).matches()) {
                                sult = JOptionPane.showInputDialog(controlPanel, "Enter Weight:", "Input"
                                        , JOptionPane.QUESTION_MESSAGE);
                                if (sult == null) {
                                    from.setBackground(Color.BLACK);
                                    to.setBackground(Color.BLACK);
                                    from = null;
                                    to = null;
                                    return;
                                }
                            }

                            Edge edge1 = new Edge();
                            Edge edge2 = new Edge();
                            edge1.setWeight(Integer.parseInt(sult));
                            edge2.setWeight(Integer.parseInt(sult));

                            edge1.setX1(from.getX() + 25);
                            edge1.setX2(to.getX() + 25);
                            edge1.setY1(from.getY() + 25);
                            edge1.setY2(to.getY() + 25);

                            edge2.setX1(from.getX() + 25);
                            edge2.setX2(to.getX() + 25);
                            edge2.setY1(from.getY() + 25);
                            edge2.setY2(to.getY() + 25);


                            graph.getEdgeList().add(edge1);
                            graph.getEdgeList().add(edge2);


                            int edgeX = Math.abs((from.getX() - to.getX()) / 2);
                            int edgeY = Math.abs((from.getY() - to.getY()) / 2);


                            if (from.getX() > to.getX()) {
                                edgeX = edgeX + to.getX();
                            } else {
                                edgeX = edgeX + from.getX();
                            }

                            if (from.getY() > to.getY()) {
                                edgeY = edgeY + to.getY();
                            } else {
                                edgeY = edgeY + from.getY();
                            }

                            System.out.println(edgeX + " =X " + edgeY + " =Y" +
                                    from.getX() + " =FROMX" + from.getY() + " =FROMY " + to.getX() + " =TO X " + to.getY() + " =TO Y ");

                            System.out.println(edgeX + " =X; Y= " + edgeY);
                            edge1.setBounds(edgeX, edgeY, 50, 50);
                            edge1.setVisible(true);
                            edge2.setBounds(edgeX, edgeY, 50, 50);
                            edge2.setVisible(true);

                            JLabel weight = new JLabel(sult);

                            weight.setFont(new Font("Courier New", Font.BOLD, 30));
                            weight.setBounds((to.getX() - from.getX()) / 2 + from.getX(),
                                    (to.getY() - from.getY()) / 2 + from.getY(), 100, 100);
                            weight.setForeground(Color.WHITE);

                            edge1.setWeightLabel(weight);
                            edge2.setWeightLabel(weight);


                            String[] vertexFrom = from.getName().split("\\s+");
                            System.out.println(from.getName());
                            System.out.println(vertexFrom[1]);

                            String[] vertexTo = to.getName().split("\\s+");
                            System.out.println(to.getName());
                            System.out.println("Edge <" + vertexTo[1] + " -> " + vertexFrom[1] + ">");
                            edge1.setName("Edge <" + vertexTo[1] + " -> " + vertexFrom[1] + ">");
                            edge2.setName("Edge <" + vertexFrom[1] + " -> " + vertexTo[1] + ">");
                            graph.add(edge1);
                            graph.add(edge2);
                            edge1.setReverse(edge2);
                            edge2.setReverse(edge1);
                            from.getEdges().add(edge1);
                            from.getEdges().add(edge2);

                            to.getEdges().add(edge1);
                            to.getEdges().add(edge2);

                            edge1.setvId1(from);
                            edge1.setvId2(to);

                            from.connect(to, Integer.parseInt(sult));
                            to.connect(from, Integer.parseInt(sult));

                            weight.setName("EdgeLabel <" + vertexFrom[1] + " -> " + vertexTo[1] + ">");
                            weight.setVisible(true);
                            graph.add(weight);
                            graph.getWeightLaibles().add(weight);

                            graph.updateUI();

                            System.out.println(edge1.getName());
                            System.out.println(edge1.getWeight());
                            System.out.println(edge2.getName());
                            System.out.println(edge2.getWeight());
                            System.out.println(weight.getName());

                            from = null;
                            to = null;
                        }
                    }
                }
            }

            if (jm.equals(JMenuItemMode.REMOVEVERTEX)) {
                List<Vertex> filtered = vertexSet.stream().filter(s -> s.choosen == true).collect(Collectors.toList());


                if (filtered.size() == 1) {
                    vertexSet.remove(filtered.get(0));
                    graph.remove(filtered.get(0));


                    List<JLabel> listOfWeights = filtered.get(0).getEdges().stream().map(s -> s.getWeightLabel()).collect(Collectors.toList());
                    for (JLabel it : listOfWeights
                    ) {
                        graph.remove(it);
                        graph.getWeightLaibles().remove(it);

                    }

                    System.out.println(graph.getEdgeList());
                    graph.getEdgeList().removeAll(filtered.get(0).getEdges());

                    for (Edge edge : filtered.get(0).getEdges()
                    ) {
                        graph.remove(edge);

                    }


                    for (Map.Entry<Vertex, Integer> entry : filtered.get(0).getNeighbors().entrySet()
                    ) {
                        entry.getKey().getNeighbors().remove(filtered.get(0));

                    }

                    filtered.get(0).getEdges().clear();
                    filtered.remove(filtered.get(0));
                    System.out.println(graph.getEdgeList());

                }
                graph.repaint();
                graph.getVertexListInGraph().clear();
                graph.getVertexListInGraph().addAll(vertexSet);
            }

            if (jm.equals(JMenuItemMode.REMOVEEDGE)) {
                System.out.println("REMOVEEDGE");
                int x = e.getX();
                int y = e.getY();
                System.out.println(x);
                System.out.println(y);
                Component componentAt = graph.getComponentAt(e.getPoint());

                if (componentAt instanceof Edge edge) {


                    graph.remove(edge);
                    graph.remove(edge.getReverse());
                    JLabel weight = edge.getWeightLabel();
                    JLabel weight1 = edge.getReverse().getWeightLabel();
                    graph.remove(weight);
                    graph.remove(weight1);
                    graph.getEdgeList().remove(edge);
                    graph.getEdgeList().remove(edge.getReverse());
                    graph.getWeightLaibles().remove(weight);
                    graph.getWeightLaibles().remove(weight1);
                }


                List<Edge> edgeForRemove = new ArrayList<>();

//                for (Edge edge : graph.getEdgeList()
//                ) {
//
//                    System.out.println(edge.getName());
//                    System.out.println("Edge x1 = " + edge.getX1() + "; x2 = " + edge.getX2() + " y1 = " + edge.getY1() + " y2 = " + edge.getY2());
//                    double distTocheck = Math.sqrt(Math.pow(edge.getX1() - x, 2) + Math.pow(edge.getY1() - y, 2)) +
//                            Math.sqrt(Math.pow(edge.getX2() - x, 2) + Math.pow(edge.getY2() - y, 2));
//
//                    double dist = Math.sqrt(Math.pow(edge.getX1() - edge.getX2(), 2) + Math.pow(edge.getY1() - edge.getY2(), 2));
//                    double error = ((distTocheck - dist) / dist * 100.0);
//                    System.out.println("Error = " + error);
//                    System.out.println("distToCheck= " + distTocheck);
//                    System.out.println("dist = " + dist);
//
//                    if (error < 0.9) {
//
//                        edgeForRemove.add(edge);
//
//                    }
//                }
//                if (!edgeForRemove.isEmpty()) {
//                    System.out.println("removed");
//                    graph.getEdgeList().removeAll(edgeForRemove);
//                    for (Edge it : edgeForRemove
//                    ) {
//
//
//                        graph.remove(it);
//                        JLabel weight = it.getWeightLabel();
//                        graph.remove(weight);
//                        graph.getWeightLaibles().remove(weight);
//
//                    }
//                }
                graph.getEdgeList().stream().forEach(s -> System.out.println(s.getName()));
                graph.repaint();
            }

            if (jm.equals(JMenuItemMode.BFS)) {
                if (timer != null) {
                    timer.stop();
                }

                timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        List<Vertex> filtered = vertexSet.stream().filter(s -> s.choosen == true).collect(Collectors.toList());
                        if (filtered.size() == 1) {
                            System.out.println(filtered.get(0).toString());

                            System.out.println(search(filtered.get(0)));

                            for (Edge edge : filtered.get(0).getEdges()
                            ) {
                                if (edge.getvId2() != null) {
                                    String[] vertexTo = edge.getvId2().getName().split("\\s+");
                                    System.out.println(vertexTo[1]);
                                }
                            }
                        }
                        System.out.println("BFS : A -> B -> C -> D -> E -> F");
                        alg.setText(search(filtered.get(0)).toString());
                    }
                });
                alg.setText("Please wait...");
                displayPanel.add(alg);
                graph.updateUI();
                timer.setRepeats(false);
                timer.start();
                System.out.println("BBB");
            }

            if (jm.equals(JMenuItemMode.DFS)) {
                if (timer != null) {
                    timer.stop();
                }
                timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        List<Vertex> filtered = vertexSet.stream().filter(s -> s.choosen == true).collect(Collectors.toList());
                        if (filtered.size() == 1) {
                            System.out.println(filtered.get(0).toString());


                            System.out.println(dfsWithoutRecursion(filtered.get(0)));

                            for (Edge edge : filtered.get(0).getEdges()
                            ) {
                                if (edge.getvId2() != null) {
                                    String[] vertexTo = edge.getvId2().getName().split("\\s+");
                                    System.out.println(vertexTo[1]);
                                }
                            }
                        }

                        System.out.println("DFS : B -> C -> A -> D -> F -> E");
                        alg.setText(dfsWithoutRecursion(filtered.get(0)).toString());
                    }
                });

                alg.setText("Please wait...");
                displayPanel.add(alg);
                graph.updateUI();
                timer.setRepeats(false);
                timer.start();
                System.out.println("AAA");
//                add(alg);
            }

            if (jm.equals(JMenuItemMode.DIJKSTRA)) {

                System.out.println("DFGG");
                current.act();
            }
            if (jm.equals(JMenuItemMode.PRIMS)) {
                ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

                executor.submit(() -> System.out.println("YEZ"));
                executor.submit(() -> {
                    current.act();
                });
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

    }

    void init() {
//        label = new JLabel();
        jm = JMenuItemMode.ADDVERTEX;
        for (Vertex it : vertexSet
        ) {
            for (int i = 0; i < listOfMouseListeners.size(); i++) {


                it.removeMouseListener(listOfMouseListeners.get(i));
            }
            it.setEnabled(false);
            it.setBackground(Color.BLACK);
        }


        label.setText("Current Mode -> Add a Vertex");
        label.setFont(new Font("Courier New", Font.BOLD, 15));
        label.setBounds(400, 0, 300, 50);
        label.setForeground(Color.WHITE);

//        graph.updateUI();
        displayPanel.remove(alg);

        statusPanel.add(label);
        statusPanel.setVisible(true);
        repaint();
        graph.removeMouseListener(m);

        graph.addMouseListener(m);
    }

}