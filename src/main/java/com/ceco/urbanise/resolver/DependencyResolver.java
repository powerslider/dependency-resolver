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
        Deque<T> resolvedStack = new ArrayDeque<>();
        List<T> unresolved = new ArrayList<>();
        Deque<T> currentResolvedStack = null;

        for (Node<T> n : dependencyGraph.getAdjacencyList()) {
            if (!n.isVisited()) {
                topologicalSort(n, resolvedStack, unresolved);
                currentResolvedStack = new ArrayDeque<>(resolvedStack);
            }

            T currentNodeName = currentResolvedStack.pop();
            while (fullDepsGraph.getNode(currentNodeName) != null) {
                currentNodeName = currentResolvedStack.pop();
            }

            List<T> resolvedList = new ArrayList<>(currentResolvedStack);
            if (hasEdges(currentNodeName)) {
                Node<T> currentNode = fullDepsGraph.addIfAbsent(currentNodeName);
                List<T> currentNodeEdgeNames = new ArrayList<>();
                T origCurrentNodeName = currentNodeName;

                for (Iterator<T> it = resolvedList.iterator(); it.hasNext(); ) {
                    T depNodeName = it.next();
                    if (dependencyGraph.getNode(currentNodeName).dependsOn(depNodeName)) {
                        currentNodeEdgeNames.add(depNodeName);
                        currentNodeName = depNodeName;
                        it.remove();
                    }
                }

                traceDependencies(resolvedList, currentNodeEdgeNames, origCurrentNodeName);
                Collections.sort(currentNodeEdgeNames);
                currentNode.replaceEdgesWith(currentNodeEdgeNames);
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

    private void traceDependencies(List<T> resolvedList, List<T> currentNodeEdgeNames, T nodeName) {
        if (resolvedList.isEmpty()) {
            return;
        }

        Node<T> depGraphNode = dependencyGraph.getNode(nodeName);
        if (depGraphNode == null) return;

        depGraphNode = addDependencies(resolvedList, currentNodeEdgeNames, depGraphNode);

        for (Node<T> e : depGraphNode.getEdges()) {
            traceDependencies(resolvedList, currentNodeEdgeNames, e.getName());
        }
    }

    private Node<T> addDependencies(List<T> resolvedList, List<T> currentNodeEdgeNames, Node<T> depGraphNode) {
        for (Iterator<T> it = resolvedList.iterator(); it.hasNext(); ) {
            T currentNodeName = it.next();
            if (depGraphNode.dependsOn(currentNodeName)) {
                currentNodeEdgeNames.add(currentNodeName);
                depGraphNode = dependencyGraph.getNode(currentNodeName);
                it.remove();
            }
        }
        return depGraphNode;
    }

}
