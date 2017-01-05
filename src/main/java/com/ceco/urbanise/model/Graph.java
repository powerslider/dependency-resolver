package com.ceco.urbanise.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 04-Jan-2017
 */
public class Graph<T> {

    private Map<T, Node<T>> adjacencyList;


    public Graph() {
        adjacencyList = new HashMap<>();
    }


    public boolean isEmpty() {
        return adjacencyList.size() == 0;
    }

    public Node<T> addIfAbsent(T nodeName) {
        Node<T> node;
        if (!hasNode(nodeName)) {
            node = new Node<>(nodeName);
            addNode(node);
        } else {
            node = getNode(nodeName);
        }
        return node;
    }

    public Collection<Node<T>> getAdjacencyList() {
        return adjacencyList.values();
    }

    private void addNode(Node<T> node) {
        adjacencyList.putIfAbsent(node.getName(), node);
    }

    private boolean hasNode(T node) {
        return adjacencyList.containsKey(node);
    }

    public Node<T> getNode(T nodeName) {
        return adjacencyList.get(nodeName);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        adjacencyList.forEach((k, v) -> {
            builder.append(v.toString());
        });
        return builder.toString();
    }
}
