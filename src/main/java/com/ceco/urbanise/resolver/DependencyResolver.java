package com.ceco.urbanise.resolver;

import com.ceco.urbanise.model.Graph;
import com.ceco.urbanise.model.Node;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class containing a dependency resolution algorithm which accepts {@link Graph} objects
 * and returns again {@link Graph} objects including all transitive dependencies
 * for each {@link Node} of the graph in their adjacency lists.
 *
 * @param <T>
 *     type of graph node names
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 04-Jan-2017
 */
public class DependencyResolver<T extends Comparable<T>> {

    private List<Graph<T>> dependencyGraphs;


    private DependencyResolver(List<Graph<T>> graphs) {
        this.dependencyGraphs = graphs;
    }

    public static <T extends Comparable<T>> Builder<T> builder() {
        return new Builder<>();
    }

    /**
     * Builder class for easy and fluent instantiation.
     *
     * @param <T>
     *      type of graph node names
     */
    public static class Builder<T extends Comparable<T>> {

        private List<Graph<T>> dependencyGraphs = new ArrayList<>();

        private Builder() {
            super();
        }

        public Builder withDependencyGraph(Graph<T> dependencyGraph) {
            this.dependencyGraphs.add(dependencyGraph);
            return this;
        }

        public DependencyResolver<T> createResolver() {
            if (dependencyGraphs.isEmpty()) {
                throw new IllegalStateException(
                        "No dependency graphs set. Use withDependencyGraph(...) to add new graphs to be resolved.");
            }

            dependencyGraphs.forEach(
                    g -> Optional.ofNullable(g)
                            .orElseThrow(() -> new IllegalArgumentException(
                                    "Dependency graph cannot be null.")));

            return new DependencyResolver<>(dependencyGraphs);
        }
    }

    /**
     * Apply {@link #resolveGraph} on all passed dependency graphs to the
     * resolver.
     *
     * @return a list of fully resolved graphs
     */
    public List<Graph<T>> resolve() {
        return dependencyGraphs.stream()
                .map(this::resolveGraph)
                .collect(Collectors.toList());
    }

    /**
     * Main method for dependency resolving a graph. Applies topological sorting and additional DFS traversal.
     *
     * @param dependencyGraph
     *      original dependency graph
     * @return a graph with full set of dependencies for each node
     */
    private Graph<T> resolveGraph(Graph<T> dependencyGraph) {
        Graph<T> fullDepsGraph = new Graph<>();
        Deque<T> resolvedStack = new ArrayDeque<>();
        List<T> unresolved = new ArrayList<>();
        Deque<T> currentResolvedStack = new ArrayDeque<>();

        for (Node<T> n : dependencyGraph.getAdjacencyList()) {
            if (!n.isVisited()) {
                topologicalSort(n, resolvedStack, unresolved);

                // on every new topological sort we need to update the resolved stack
                // in order to be consistent if new nodes show up
                currentResolvedStack = new ArrayDeque<>(resolvedStack);
            }

            if (currentResolvedStack.isEmpty()) break;

            setDependenciesOnCorrectNodes(dependencyGraph, fullDepsGraph, currentResolvedStack);
        }
        return fullDepsGraph;
    }

    private void setDependenciesOnCorrectNodes(Graph<T> dependencyGraph,
                                               Graph<T> fullDepsGraph,
                                               Deque<T> currentResolvedStack) {
        // pop nodes from the resolved stack if they were already added to the end graph
        // in order to avoid adding wrong excess dependencies
        T currentNodeName = currentResolvedStack.pop();
        while (fullDepsGraph.getNode(currentNodeName) != null) {
            currentNodeName = currentResolvedStack.pop();
        }

        // transform resolved stack into a list for allowing more complex operations on it
        List<T> resolvedList = new ArrayList<>(currentResolvedStack);

        // only add nodes with edges to the end graph
        if (dependencyGraph.hasEdges(currentNodeName)) {
            Node<T> currentNode = fullDepsGraph.addIfAbsent(currentNodeName);

            // initialize a list only of node edge names which we will need for sorting later
            List<T> currentNodeEdgeNames = new ArrayList<>();


            // add direct dependencies and remove added ones from the resolved list
            addDependencies(dependencyGraph,
                    resolvedList,
                    currentNodeEdgeNames,
                    currentNodeName);

            // add transitive dependencies and remove added ones from the resolved list
            // until the list is empty and all dependencies are set
            addTransitiveDependencies(dependencyGraph,
                    resolvedList,
                    currentNodeEdgeNames,
                    currentNodeName);

            // sort node dependencies node names
            Collections.sort(currentNodeEdgeNames);

            // generate adjacent nodes from the sorted edge names and
            // add them to the adjacency list of the current node
            currentNode.replaceEdgesWith(currentNodeEdgeNames);
        }
    }

    /**
     * Classic topological sorting using DFS with the addtitional notion of finding circular dependencies.
     *
     * @param node
     *      current node to be traversed
     * @param resolved
     *      stack of already visited nodes
     * @param unresolved
     *      list of unresolved nodes used for tracking circular dependencies
     */
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

    /**
     * Applies DFS (Depth First Search) in order to trace transitive dependencies and add them.
     *
     * @param dependencyGraph
     *      original dependency graph
     * @param resolvedList
     *      resolved list of ordered dependencies from topological sorting
     * @param currentNodeEdgeNames
     *      list of current node's edge name
     * @param nodeName
     *      current node name
     */
    private void addTransitiveDependencies(Graph<T> dependencyGraph,
                                           List<T> resolvedList,
                                           List<T> currentNodeEdgeNames,
                                           T nodeName) {
        if (resolvedList.isEmpty()) {
            return;
        }

        Node<T> depGraphNode = addDependencies(dependencyGraph,
                resolvedList,
                currentNodeEdgeNames,
                nodeName);

        if (depGraphNode == null) return;

        for (Node<T> e : depGraphNode.getEdges()) {
            addTransitiveDependencies(dependencyGraph,
                    resolvedList,
                    currentNodeEdgeNames,
                    e.getName());
        }
    }

    /**
     * Add direct dependencies to current node by comparing for adjacency nodes from the resolved list with
     * the current node.
     *
     * @param dependencyGraph
     *      original dependency graph
     * @param resolvedList
     *      resolved list of ordered dependencies from topological sorting
     * @param currentNodeEdgeNames
     *      list of current node's edge name
     * @param nodeName
     *      current node name
     * @return next node for exploration
     */
    private Node<T> addDependencies(Graph<T> dependencyGraph,
                                    List<T> resolvedList,
                                    List<T> currentNodeEdgeNames,
                                    T nodeName) {
        Node<T> node = dependencyGraph.getNode(nodeName);
        if (node != null) {
            for (Iterator<T> it = resolvedList.iterator(); it.hasNext(); ) {
                T currentNodeName = it.next();
                if (node.isAdjacentTo(currentNodeName)) {
                    currentNodeEdgeNames.add(currentNodeName);
                    node = dependencyGraph.getNode(currentNodeName);
                    it.remove();
                }
            }
        }
        return node;
    }
}
