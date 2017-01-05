package com.ceco.urbanise.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * @since 04-Jan-2017
 */
public class Node<T> {

    private final T name;

    private List<Node> edges;

    private boolean isVisited;


    public Node(T name) {
        this.name = name;
        this.edges = new ArrayList<>();
    }


    public void addEdge(Node node) {
        this.edges.add(node);
    }

    public void addEdges(List<Node> nodes) {
        this.edges.addAll(nodes);
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public T getName() {
        return name;
    }

    public List<Node> getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name)
                .append(" ");

        Iterator<Node> it = edges.iterator();
        if (!it.hasNext()) {
            builder.append("\n");
            return builder.toString();
        }

        while (true) {
            Node node = it.next();
            builder.append(node.name);
            if (!it.hasNext()) {
                builder.append("\n");
                break;
            }
            builder.append(" ");
        }

        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        // if the same instance -> is equal
        if (this == o) return true;

        // if not the same type -> not equal
        if (!(o instanceof Node)) return false;

        Node<?> node = (Node<?>) o;

        if (name != null ? !name.equals(node.name) : node.name != null) return false;
        return edges != null ? edges.equals(node.edges) : node.edges == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (edges != null ? edges.hashCode() : 0);
        return result;
    }
}
