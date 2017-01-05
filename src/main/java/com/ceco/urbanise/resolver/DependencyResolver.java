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
            return new DependencyResolver<>(dependencyGraph);
        }

    }

    public Graph<T> resolve() {
        List<T> polledNodes = new ArrayList<>();
        Graph<T> fullDepsGraph = new Graph<>();
        Deque<T> stack = new ArrayDeque<>();
        for (Node n : dependencyGraph.getAdjacencyList()) {
            if (!n.isVisited()) {
                topologicalSort(n, stack);
            }

            T currentNodeName = stack.pollFirst();
            polledNodes.add(currentNodeName);
            List<T> list = new ArrayList<>(stack);
            Collections.sort(list);
            Node<T> currentNode = fullDepsGraph.addIfAbsent(currentNodeName);
            list.forEach((sortedNodeName) -> {
                currentNode.addEdge(new Node<>(sortedNodeName));
            });
        }
        return fullDepsGraph;
    }

    private void topologicalSort(Node<T> node, Deque<T> stack) {
        node.setVisited(true);
        List<Node> edges = node.getEdges();
        edges.forEach((e) -> {
            if (!e.isVisited())
                topologicalSort(e, stack);
        });
        stack.push(node.getName());
    }

}
