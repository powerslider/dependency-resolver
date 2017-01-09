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

    public Collection<Node<T>> getAdjacencyList() {
        return adjacencyList.values();
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

    public Node<T> getNode(T nodeName) {
        return adjacencyList.get(nodeName);
    }

    public Graph<T> reverse() {
        Collection<Node<T>> adjList = getAdjacencyList();
        adjacencyList = new HashMap<>();
        for (Node<T> n : adjList) {
            for (Node<T> e : n.getEdges()) {
                Node<T> revNode = addIfAbsent(e.getName());
                revNode.addEdge(new Node<>(n.getName()));
            }
        }
        return this;
    }

    private void addNode(Node<T> node) {
        adjacencyList.putIfAbsent(node.getName(), node);
    }

    private boolean hasNode(T node) {
        return adjacencyList.containsKey(node);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        getAdjacencyList().forEach((n) -> builder.append(n.toString()));
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Graph)) return false;

        Graph<?> graph = (Graph<?>) o;

        return adjacencyList != null ? adjacencyList.equals(graph.adjacencyList) : graph.adjacencyList == null;
    }

    @Override
    public int hashCode() {
        return adjacencyList != null ? adjacencyList.hashCode() : 0;
    }
}
