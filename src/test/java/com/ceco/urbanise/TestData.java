package com.ceco.urbanise;

import com.ceco.urbanise.model.Graph;
import com.ceco.urbanise.model.Node;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 09-Jan-2017
 */
public class TestData {

    public static Graph<String> graphExample1() {
        Graph<String> graph = new Graph<>();

        Node<String> a = graph.addIfAbsent("A");
        Node<String> b = graph.addIfAbsent("B");
        Node<String> c = graph.addIfAbsent("C");
        Node<String> d = graph.addIfAbsent("D");
        Node<String> e = graph.addIfAbsent("E");
        Node<String> f = graph.addIfAbsent("F");
        Node<String> g = graph.addIfAbsent("G");
        Node<String> h = graph.addIfAbsent("H");

        a.addEdge(b);
        a.addEdge(c);
        b.addEdge(c);
        b.addEdge(e);
        c.addEdge(g);
        d.addEdge(a);
        d.addEdge(f);
        e.addEdge(f);
        f.addEdge(h);

        return graph;
    }

    /**
     * Z W Y
     * W X
     * T U Z
     * Y U
     *
     * @return a graph with the specified nodes and edges
     */
    public static Graph<String> graphExample2() {
        Graph<String> graph = new Graph<>();

        Node<String> z = graph.addIfAbsent("Z");
        Node<String> w = graph.addIfAbsent("W");
        Node<String> y = graph.addIfAbsent("Y");
        Node<String> x = graph.addIfAbsent("X");
        Node<String> t = graph.addIfAbsent("T");
        Node<String> u = graph.addIfAbsent("U");

        z.addEdge(w);
        z.addEdge(y);
        w.addEdge(x);
        t.addEdge(u);
        t.addEdge(z);
        y.addEdge(u);

        return graph;
    }

    /**
     * A
     * B
     * C
     * D
     * E
     * F
     * G
     * H
     * I
     * J
     * K
     * L
     * M
     * N
     * O
     * P
     * Q
     * R
     * S
     * T
     * U
     * V
     * W
     * X
     * Y
     * Z
     *
     * @return a graph with the specified nodes and edges
     */
    public static Graph<String> graphExample3() {
        Graph<String> graph = new Graph<>();

        graph.addIfAbsent("A");
        graph.addIfAbsent("B");
        graph.addIfAbsent("C");
        graph.addIfAbsent("D");
        graph.addIfAbsent("E");
        graph.addIfAbsent("F");
        graph.addIfAbsent("G");
        graph.addIfAbsent("H");
        graph.addIfAbsent("I");
        graph.addIfAbsent("J");
        graph.addIfAbsent("K");
        graph.addIfAbsent("L");
        graph.addIfAbsent("M");
        graph.addIfAbsent("N");
        graph.addIfAbsent("O");
        graph.addIfAbsent("P");
        graph.addIfAbsent("Q");
        graph.addIfAbsent("R");
        graph.addIfAbsent("S");
        graph.addIfAbsent("T");
        graph.addIfAbsent("U");
        graph.addIfAbsent("V");
        graph.addIfAbsent("W");
        graph.addIfAbsent("X");
        graph.addIfAbsent("Y");
        graph.addIfAbsent("Z");

        return graph;
    }

    /**
     * A B C E F G H
     * B C E F G H
     * C G
     * D A B C E F G H
     * E F H
     * F H
     *
     * @return a graph with the specified nodes and edges
     */
    public static Graph<String> resolvedGraphExample1() {
        Graph<String> resolvedGraph = new Graph<>();

        Node<String> a = resolvedGraph.addIfAbsent("A");
        a.addEdge(new Node<>("B"));
        a.addEdge(new Node<>("C"));
        a.addEdge(new Node<>("E"));
        a.addEdge(new Node<>("F"));
        a.addEdge(new Node<>("G"));
        a.addEdge(new Node<>("H"));

        Node<String> b = resolvedGraph.addIfAbsent("B");
        b.addEdge(new Node<>("C"));
        b.addEdge(new Node<>("E"));
        b.addEdge(new Node<>("F"));
        b.addEdge(new Node<>("G"));
        b.addEdge(new Node<>("H"));

        Node<String> e = resolvedGraph.addIfAbsent("E");
        e.addEdge(new Node<>("F"));
        e.addEdge(new Node<>("H"));

        Node<String> d = resolvedGraph.addIfAbsent("D");
        d.addEdge(new Node<>("A"));
        d.addEdge(new Node<>("B"));
        d.addEdge(new Node<>("C"));
        d.addEdge(new Node<>("E"));
        d.addEdge(new Node<>("F"));
        d.addEdge(new Node<>("G"));
        d.addEdge(new Node<>("H"));

        Node<String> f = resolvedGraph.addIfAbsent("F");
        f.addEdge(new Node<>("H"));

        Node<String> c = resolvedGraph.addIfAbsent("C");
        c.addEdge(new Node<>("G"));

        return resolvedGraph;

    }

    /**
     * A D
     * B A
     * C A B
     * E B
     * F D E
     * G C
     * H F
     *
     * @return a graph with the specified nodes and edges
     */
    public static Graph<String> reversedGraphExample1() {
        Graph<String> revGraph = new Graph<>();

        Node<String> a = revGraph.addIfAbsent("A");
        Node<String> b = revGraph.addIfAbsent("B");
        Node<String> c = revGraph.addIfAbsent("C");
        Node<String> d = new Node<>("D");
        Node<String> e = revGraph.addIfAbsent("E");
        Node<String> f = revGraph.addIfAbsent("F");
        Node<String> g = revGraph.addIfAbsent("G");
        Node<String> h = revGraph.addIfAbsent("H");

        a.addEdge(d);

        b.addEdge(a);

        c.addEdge(a);
        c.addEdge(b);

        e.addEdge(b);

        f.addEdge(d);
        f.addEdge(e);

        g.addEdge(c);

        h.addEdge(f);

        return revGraph;
    }
}
