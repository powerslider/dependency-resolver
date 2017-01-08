package com.ceco.urbanise.resolver;

import com.ceco.urbanise.model.Graph;
import com.ceco.urbanise.model.Node;

import java.util.Scanner;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * @since 04-Jan-2017
 */
public class Main {

    public static void main(String args[]) {
        Graph<String> graph = readGraphInputData();

        final Graph fullDepsGraph = new DependencyResolver.Builder<String>()
                .withDependencyGraph(graph)
                .createResolver()
                .resolve();

//        Graph<String> revGraph = graph.reverse();
//        final Graph fullDepsReversedGraph = new DependencyResolver.Builder<String>()
//                .withDependencyGraph(revGraph)
//                .createResolver()
//                .resolve();

        System.out.println(graph);
        System.out.println();
        System.out.println(fullDepsGraph);
        System.out.println();
//        System.out.println(revGraph);
//        System.out.println();
//        System.out.println(fullDepsReversedGraph);
    }

    private static Graph<String> readGraphInputData() {
        Graph<String> graph = new Graph<>();
        try (Scanner scanner = new Scanner(System.in)) {
            String line;
            while (!(line = scanner.nextLine()).isEmpty()) {
                String[] nodeNames = line.split("\\s");
                Node<String> node = graph.addIfAbsent(nodeNames[0]);
                for (int i = 1; i < nodeNames.length; i++) {
                    Node<String> adjacentNode = graph.addIfAbsent(nodeNames[i]);
                    node.addEdge(adjacentNode);
                }
            }
        }
        return graph;
    }
}

