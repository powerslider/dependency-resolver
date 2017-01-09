package com.ceco.urbanise.resolver;

import com.ceco.urbanise.TestData;
import com.ceco.urbanise.model.Graph;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * @since 04-Jan-2017
 */
public class DependencyResolverTest {

    @Test
    @DisplayName("should throw exception when no dependency graph is passed to resolver")
    void testThrowsExceptionWhenNoGraphSet() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new DependencyResolver.Builder<>().createResolver());

        assertEquals("Dependency graph cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("should find correct dependencies of each node in graph")
    void testCorrectDepsInGraph() {
        Graph<String> dependencyGraph = TestData.graphExample1();
        Graph<String> expectedGraph = TestData.resolvedGraphExample1();

        Graph resolvedGraph = new DependencyResolver.Builder<String>()
                .withDependencyGraph(dependencyGraph)
                .createResolver()
                .resolve();

        assertEquals(expectedGraph, resolvedGraph);
    }

    @Test
    @DisplayName("should find circular dependency")
    void testCircularDependency() {
        Graph<String> dependencyGraph = TestData.graphExample1().reverse();

        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    new DependencyResolver.Builder<String>()
                            .withDependencyGraph(dependencyGraph)
                            .createResolver()
                            .resolve();
                });

        assertEquals("Circular reference detected: D -> A", exception.getMessage());
    }
}
