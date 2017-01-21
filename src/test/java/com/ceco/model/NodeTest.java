package com.ceco.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class testing functionality from {@link Node}
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 09-Jan-2017
 */
public class NodeTest {

    @Test
    @DisplayName("should get correct node name")
    void testCorrectNodeName() {
        Node<String> n = new Node<>("A");
        assertEquals("A", n.getName());
    }

    @Test
    @DisplayName("should node be visited")
    void testNodeIsVisited() {
        Node<String> n = new Node<>("A");
        n.setVisited(true);
        assertTrue(n.isVisited());

        n.setVisited(false);
        assertFalse(n.isVisited());
    }

    @Test
    @DisplayName("should get correct node edges")
    void testCorrectNodeEdges() {
        Node<String> n = new Node<>("A");
        n.addEdge(new Node<>("B"));
        n.addEdge(new Node<>("C"));
        n.addEdge(new Node<>("D"));

        List<Node> expectedEdges = new ArrayList<>();
        expectedEdges.add(new Node<>("B"));
        expectedEdges.add(new Node<>("C"));
        expectedEdges.add(new Node<>("D"));

        assertEquals(expectedEdges, n.getEdges());
    }

    @Test
    @DisplayName("should node print correctly")
    void testNodePrintsCorrectly() {
        Node<String> n = new Node<>("A");
        n.addEdge(new Node<>("B"));
        n.addEdge(new Node<>("C"));
        n.addEdge(new Node<>("D"));

        assertEquals("A B C D\n", n.toString());
    }

    @Test
    @DisplayName("should be linked to correct node")
    void testNodeLinkedCorrectly() {
        Node<String> n = new Node<>("A");
        n.addEdge(new Node<>("B"));
        n.addEdge(new Node<>("C"));
        n.addEdge(new Node<>("D"));

        assertAll("node edges",
                () -> assertTrue(n.isAdjacentTo("B")),
                () -> assertTrue(n.isAdjacentTo("C")),
                () -> assertTrue(n.isAdjacentTo("D")),
                () -> assertFalse(n.isAdjacentTo("E"))
        );
    }

    @Test
    @DisplayName("should node edges be replaced correctly from an edge names list")
    void testNodeEdgesReplacedCorrectly() {
        Node<String> n = new Node<>("A");
        n.addEdge(new Node<>("B"));
        n.addEdge(new Node<>("C"));
        n.addEdge(new Node<>("D"));

        List<Node<String>> origEdgeNames = Arrays.asList(
                new Node<>("B"),
                new Node<>("C"),
                new Node<>("D")
        );
        assertEquals(origEdgeNames, n.getEdges());

        List<String> newEdgeNames = Arrays.asList("E", "F", "G");
        n.replaceEdgesWith(newEdgeNames);

        List<Node<String>> newEdges = Arrays.asList(
                new Node<>("E"),
                new Node<>("F"),
                new Node<>("G")
        );
        assertEquals(newEdges, n.getEdges());
    }
}
