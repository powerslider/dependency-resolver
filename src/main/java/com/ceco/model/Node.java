package com.ceco.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Model class representing a node (vertex) in a graph data structure.
 *
 * @param <T>
 *     type of graph node names
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 04-Jan-2017
 */
public class Node<T extends Comparable<T>> {

    /**
     * Name of the node.
     */
    private final T name;

    /**
     * Кееps a list with all adjacent nodes of the current
     * node where each adjacent node together with the current
     * node form and edge.
     */
    private List<Node<T>> edges;

    /**
     * Same as {@link Node#edges} but keeps only the names
     * of the adjacent nodes which helps for some operations
     * on the graph.
     */
    private List<T> edgeNames;

    /**
     * Flag, marking if a node is visited or not.
     */
    private boolean isVisited;


    public Node(T name) {
        this.name = name;
        this.edges = new ArrayList<>();
        this.edgeNames = new ArrayList<>();
    }


    /**
     * Adds a new adjacent node to the current node.
     *
     * @param node
     *      adjacent node to be added
     */
    public void addEdge(Node<T> node) {
        this.edges.add(node);
        this.edgeNames.add(node.getName());
    }

    /**
     * Replaces all edges of the current node by creating new ones with
     * new specified names.
     *
     * @param edgeNames
     *      specified names for creating new adjacent nodes
     */
    public void replaceEdgesWith(List<T> edgeNames) {
        this.edges = edgeNames.stream()
                .map((Function<T, Node<T>>) Node::new)
                .collect(Collectors.toList());
    }

    /**
     * Determines if a node is adjacent to another node.
     *
     * @param adjacentNodeName
     *      node name to check if it is adjacent to the current node
     * @return true/false if the specified node is adjacent to the current node
     */
    public boolean isAdjacentTo(T adjacentNodeName) {
        return edgeNames.stream()
                .anyMatch((eName) -> eName.equals(adjacentNodeName));
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

    public List<Node<T>> getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name)
                .append(" ");

        Iterator<Node<T>> it = edges.iterator();
        if (!it.hasNext()) {
            builder.append("\n");
            return builder.toString();
        }

        while (true) {
            Node<T> node = it.next();
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
