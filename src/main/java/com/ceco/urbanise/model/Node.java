package com.ceco.urbanise.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * @since 04-Jan-2017
 */
public class Node {

    public final String name;
    public List<Node> edges;

    public Node(String name) {
        this.name = name;
        this.edges = new ArrayList<>();
    }

    public void addEdge(Node node) {
        this.edges.add(node);
    }
}
