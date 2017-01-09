package com.ceco.urbanise.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
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
}
