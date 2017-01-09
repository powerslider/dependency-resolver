package com.ceco.urbanise.io;

import com.ceco.urbanise.model.Graph;
import com.ceco.urbanise.model.Node;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 09-Jan-2017
 */
public class InputReader {

    public static Graph<String> readGraphInputData(InputStream inputStream) {
        Graph<String> graph = new Graph<>();
        try (Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) break;

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
