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

    private Graph<T> fullDepsGraph;

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
        fullDepsGraph = new Graph<>();
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
//            Collections.sort(list);
            if (hasEdges(currentNodeName)) {
                Node<T> currentNode = fullDepsGraph.addIfAbsent(currentNodeName);
                T origCurrentNodeName = currentNodeName;
                for (Iterator<T> it = resolvedList.iterator(); it.hasNext();) {
                    T sortedNodeName = it.next();
                    if (dependencyGraph.getNode(currentNodeName).dependsOn(sortedNodeName)) {
                        currentNode.addEdge(new Node<>(sortedNodeName));
                        currentNodeName = sortedNodeName;
                        it.remove();
                    }
                }

                a(resolvedList, currentNode, origCurrentNodeName);
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

    private void a(List<T> resolvedList, Node<T> currentNode, T nodeName) {
        if (resolvedList.isEmpty()) {
            return;
        }

        Node<T> depGraphNode = dependencyGraph.getNode(nodeName);
        depGraphNode = addDependencies(resolvedList, currentNode, depGraphNode);

        for (Node<T> e : depGraphNode.getEdges()) {
            a(resolvedList, currentNode, e.getName());
        }
    }

    private Node<T> addDependencies(List<T> resolvedList, Node<T> currentNode, Node<T> depGraphNode) {
        for (Iterator<T> it = resolvedList.iterator(); it.hasNext();) {
            T currentNodeName = it.next();
            if (depGraphNode.dependsOn(currentNodeName)) {
                currentNode.addEdge(new Node<>(currentNodeName));
                depGraphNode = dependencyGraph.getNode(currentNodeName);
                it.remove();
            }
        }
        return depGraphNode;
    }

}
