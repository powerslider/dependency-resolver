package com.ceco.urbanise.resolver;

import com.ceco.urbanise.io.InputReader;
import com.ceco.urbanise.model.Graph;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * @since 04-Jan-2017
 */
public class Main {

    public static void main(String args[]) {

        Graph<String> graph = InputReader.readGraphInputData(System.in);
        final Graph fullDepsGraph = new DependencyResolver.Builder<String>()
                .withDependencyGraph(graph)
                .createResolver()
                .resolve();

        System.out.println("Graph dependencies:");
        System.out.println(fullDepsGraph);
        System.out.println();

        Graph<String> revGraph = graph.reverse();
        final Graph fullDepsReversedGraph = new DependencyResolver.Builder<String>()
                .withDependencyGraph(revGraph)
                .createResolver()
                .resolve();

        System.out.println("Reversed graph dependencies:");
        System.out.println(fullDepsReversedGraph);
    }
}

