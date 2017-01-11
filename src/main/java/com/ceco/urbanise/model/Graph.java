package com.ceco.urbanise.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Model class representing a graph data structure using an adjacency list.
 * Operates with node objects {@link Node} representing graph nodes.
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 04-Jan-2017
 */
public class Graph<T extends Comparable<T>> {

    /**
     * Adjacency list of the graph, represented as a {@link Map} with
     * key -> node name and value -> {@link Node} object for fast retrieval
     * by node name.
     */
    private Map<T, Node<T>> adjacencyList;


    public Graph() {
        adjacencyList = new HashMap<>();
    }

    /**
     * Copy constructor when wanting to do a deep copy of your current object.
     *
     * @param otherGraph
     *      the graph object to copy
     */
    public Graph(Graph<T> otherGraph) {
        this.adjacencyList = otherGraph.adjacencyList;
    }

    /**
     * Get adjacency list of a graph.
     *
     * @return adjacency list of a graph.
     */
    public Collection<Node<T>> getAdjacencyList() {
        return adjacencyList.values();
    }

    /**
     * Add new node to the graph only if there is no such one already.
     *
     * @param nodeName
     *      name of the new node to add
     * @return the newly added node or get it from the graph if already there
     */
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

    /**
     * Get node by node name.
     *
     * @param nodeName
     *      name of the node to get
     * @return the node if available or null otherwise
     */
    public Node<T> getNode(T nodeName) {
        return adjacencyList.get(nodeName);
    }

    /**
     * Checks if a particular node has any edges.
     *
     * @param nodeName
     *      node name for node to be checked
     * @return true/false if node has or has not got edges
     */
    public boolean hasEdges(T nodeName) {
        return !getNode(nodeName).getEdges().isEmpty();
    }

    /**
     * Reverses the direction of the graph by switching the positions of the
     * nodes laying ot both sides of at edge.
     *
     * @return the same instance of {@link Graph} but with a reversed adjacency list
     */
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

    /**
     * Add a new node the the graph's adjacency list.
     * @param node
     *      node object to be added
     */
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
