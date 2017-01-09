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
                    .orElseThrow(() -> new IllegalArgumentException("Dependency graph cannot be null"));

            return new DependencyResolver<>(dependencyGraph);
        }
    }

    public Graph<T> resolve() {
        Graph<T> fullDepsGraph = new Graph<>();
        Deque<T> stack = new ArrayDeque<>();
        List<T> unresolved = new ArrayList<>();
        Deque<T> currentStack = null;
        for (Node<T> n : dependencyGraph.getAdjacencyList()) {
            if (!n.isVisited()) {
                topologicalSort(n, stack, unresolved);
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
//                System.out.println("Node<String> " + currentNodeName.toString().toLowerCase() + " = fullDepsGraph.addIfAbsent(\""  + currentNodeName + "\");");
                for (T sortedNodeName : list) {
                    if (!sortedNodeName.equals(currentNodeName)) {
//                        System.out.println(currentNodeName.toString().toLowerCase() + ".addEdge(new Node<>(\"" + sortedNodeName + "\"));");
                        currentNode.addEdge(new Node<>(sortedNodeName));
                    }
                }
                System.out.println();
            }
        }
        return fullDepsGraph;
    }

    private boolean hasEdges(T currentNodeName) {
        return !dependencyGraph.getNode(currentNodeName).getEdges().isEmpty();
    }

    private void topologicalSort(Node<T> node, Deque<T> resolved, List<T> unresolved) {
        unresolved.add(node.getName());
        node.setVisited(true);

        for (Node<T> edge : node.getEdges()) {
            if (!resolved.contains(edge.getName())) {
                if (unresolved.contains(edge.getName())) {
                    throw new IllegalStateException(String.format("Circular reference detected: %s -> %s",
                            node.getName(), edge.getName()));
                }
                topologicalSort(edge, resolved, unresolved);
            }
        }

        resolved.push(node.getName());
        unresolved.remove(node.getName());
    }

}
