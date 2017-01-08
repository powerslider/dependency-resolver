package com.ceco.urbanise.resolver;

import com.ceco.urbanise.model.Graph;
import com.ceco.urbanise.model.Node;

import java.util.*;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * @since 04-Jan-2017
 */
public class DependencyResolver<T extends Comparable<T>> {

    private Graph<T> dependencyGraph;

    private DependencyResolver(Graph<T> graph) {
        this.dependencyGraph = graph;
    }

    public static class Builder<T extends Comparable<T>> {

        private Graph<T> dependencyGraph;

        public Builder withDependencyGraph(Graph<T> dependencyGraph) {
            this.dependencyGraph = dependencyGraph;
            return this;
        }

        public DependencyResolver<T> createResolver() {
            Optional.ofNullable(dependencyGraph)
                    .orElseThrow(() -> new IllegalStateException("Dependency graph cannot be null"));

            return new DependencyResolver<>(dependencyGraph);
        }

    }

    public Graph<T> resolve() {
        Graph<T> fullDepsGraph = new Graph<>();
        Deque<T> stack = new ArrayDeque<>();
        Deque<T> currentStack = null;
        for (Node<T> n : dependencyGraph.getAdjacencyList()) {
            if (!n.isVisited()) {
                topologicalSort(n, stack);
                currentStack = new ArrayDeque<>(stack);
            }

            T currentNodeName = currentStack.pop();
            while (fullDepsGraph.getNode(currentNodeName) != null) {
                currentNodeName = currentStack.pop();
            }

            List<T> list = new ArrayList<>(currentStack);
            Collections.sort(list);
            if (hasEdges(currentNodeName)) {
                Node<T> currentNode = fullDepsGraph.addIfAbsent(currentNodeName);
                for (T sortedNodeName : list) {
                    if (!sortedNodeName.equals(currentNodeName)) {
                        currentNode.addEdge(new Node<>(sortedNodeName));
                    }
                }
            }
        }
        return fullDepsGraph;
    }

    private boolean hasEdges(T currentNodeName) {
        return !dependencyGraph.getNode(currentNodeName).getEdges().isEmpty();
    }

    private void topologicalSort(Node<T> node, Deque<T> resolved) {
        node.setVisited(true);
        List<Node> edges = node.getEdges();
        edges.forEach((e) -> {
            if (!e.isVisited())
                topologicalSort(e, resolved);
        });
        resolved.push(node.getName());
    }

}
