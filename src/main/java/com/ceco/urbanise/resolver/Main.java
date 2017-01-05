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
        final Graph resolver = new DependencyResolver.Builder<String>()
                .withDependencyGraph(graph)
                .createResolver()
                .resolve();

        System.out.println(resolver);
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

