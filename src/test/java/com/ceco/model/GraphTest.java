package com.ceco.model;

import com.ceco.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Test class testing functionality from {@link Graph}
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 09-Jan-2017
 */
public class GraphTest {

    @Test
    @DisplayName("should reverse graph correctly")
    void testReverseGraph() {
        Graph<String> expectedGraph = TestData.reversedGraphExample1();
        Graph<String> actualGraph = TestData.graphExample1().reverse();
        assertEquals(expectedGraph.toString(), actualGraph.toString());
    }

    @Test
    @DisplayName("should not add the same node twice")
    void testNoDuplicateNodes() {
        Graph<String> actualGraph = new Graph<>();
        actualGraph.addIfAbsent("A");
        actualGraph.addIfAbsent("A");

        Graph<String> expectedGraph = new Graph<>();
        expectedGraph.addIfAbsent("A");

        assertEquals(expectedGraph, actualGraph);
    }

    @Test
    @DisplayName("should get correct node")
    void testGettingCorrectNode() {
        Graph<String> graph = new Graph<>();
        graph.addIfAbsent("A");

        Node<String> actualNode = graph.getNode("A");
        Node<String> expectedNode = new Node<>("A");

        assertEquals(expectedNode, actualNode);
    }
}
