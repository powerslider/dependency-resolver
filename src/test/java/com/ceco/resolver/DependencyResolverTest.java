package com.ceco.resolver;

import com.ceco.TestData;
import com.ceco.model.Graph;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 04-Jan-2017
 */
public class DependencyResolverTest {

    @Test
    @DisplayName("should throw exception when dependency graph list is empty")
    void testThrowsExceptionWhenDependencyGraphListIsEmpty() {
        Throwable exception = assertThrows(IllegalStateException.class,
                () -> DependencyResolver.<String>builder()
                        .createResolver()
        );

        assertEquals("No dependency graphs set. Use withDependencyGraph(...) to add new graphs to be resolved.", exception.getMessage());
    }

    @Test
    @DisplayName("should throw exception when dependency graph is null")
    void testThrowsExceptionWhenNoGraphSet() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> DependencyResolver.<String>builder()
                        .withDependencyGraph(null)
                        .createResolver()
        );

        assertEquals("Dependency graph cannot be null.", exception.getMessage());
    }

    @Test
    @DisplayName("should find correct dependencies of each node in graph")
    void testCorrectDepsInGraph() {
        Graph<String> dependencyGraph = TestData.graphExample1();
        Graph<String> expectedGraph = TestData.resolvedGraphExample1();

        @SuppressWarnings("unchecked")
        List<Graph<String>> resolvedGraphs = DependencyResolver.<String>builder()
                .withDependencyGraph(dependencyGraph)
                .createResolver()
                .resolve();

        assertEquals(expectedGraph, resolvedGraphs.get(0));
    }

    @Test
    @DisplayName("should find correct dependencies of each node in reversed graph")
    void testCorrectDepsInReversedGraph() {
        Graph<String> dependencyGraph = TestData.graphExample1().reverse();
        Graph<String> expectedGraph = TestData.resolvedReversedGraphExample1();

        @SuppressWarnings("unchecked")
        List<Graph<String>> resolvedGraphs = DependencyResolver.<String>builder()
                .withDependencyGraph(dependencyGraph)
                .createResolver()
                .resolve();

        assertEquals(expectedGraph, resolvedGraphs.get(0));
    }

    @Test
    @DisplayName("should find correct dependencies of each node in a graph and a reversed version of it")
    void testCorrectDepsInGraphAndReversedGraph() {
        Graph<String> dependencyGraph = TestData.graphExample1();
        Graph<String> reversedDependencyGraph = new Graph<>(dependencyGraph).reverse();

        Graph<String> expectedGraph = TestData.resolvedGraphExample1();
        Graph<String> expectedReversedGraph = TestData.resolvedReversedGraphExample1();

        @SuppressWarnings("unchecked")
        List<Graph<String>> resolvedGraphs = DependencyResolver.<String>builder()
                .withDependencyGraph(dependencyGraph)
                .withDependencyGraph(reversedDependencyGraph)
                .createResolver()
                .resolve();

        assertAll("resolved graphs",
                () -> assertEquals(expectedGraph, resolvedGraphs.get(0)),
                () -> assertEquals(expectedReversedGraph, resolvedGraphs.get(1))
        );
    }

    @Test
    @DisplayName("should find circular dependency")
    void testCircularDependency() {
        Graph<String> dependencyGraph = TestData.circularDependencyGraphExample();

        Throwable exception = assertThrows(IllegalStateException.class,
                () -> {
                    DependencyResolver.<String>builder()
                            .withDependencyGraph(dependencyGraph)
                            .createResolver()
                            .resolve();
                });

        assertEquals("Circular reference detected: B -> A", exception.getMessage());
    }
}
