package com.ceco.urbanise.resolver;

import com.ceco.urbanise.io.InputReader;
import com.ceco.urbanise.model.Graph;

import java.util.List;

/**
 * Main execution class of Dependency Resolver awaiting user input from the console.
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * @since 04-Jan-2017
 */
public class Main {

    public static void main(String args[]) {
        Graph<String> graph = InputReader.readGraphInputData(System.in);
        Graph<String> revGraph = new Graph<>(graph).reverse();

        @SuppressWarnings("unchecked")
        final List<Graph<String>> fullDepsGraphs = DependencyResolver.<String>builder()
                .withDependencyGraph(graph)
                .withDependencyGraph(revGraph)
                .createResolver()
                .resolve();

        System.out.println("Graph dependencies:");
        System.out.println(fullDepsGraphs.get(0));
        System.out.println("Reversed graph dependencies:");
        System.out.println(fullDepsGraphs.get(1));
    }
}

