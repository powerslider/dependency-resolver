package com.ceco.urbanise.resolver;

import com.ceco.urbanise.model.Graph;
import com.ceco.urbanise.model.Node;
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
    @DisplayName("")
    void test() {
        Graph<String> graph = new Graph<>();

        Node<String> a = graph.addIfAbsent("A");
        Node<String> b = graph.addIfAbsent("B");
        Node<String> c = graph.addIfAbsent("C");
        Node<String> d = graph.addIfAbsent("D");
        Node<String> e = graph.addIfAbsent("E");
        Node<String> f = graph.addIfAbsent("F");
        Node<String> g = graph.addIfAbsent("G");
        Node<String> h = graph.addIfAbsent("H");

        a.addEdge(b);
        a.addEdge(c);
        b.addEdge(c);
        b.addEdge(e);
        c.addEdge(g);
        d.addEdge(a);
        d.addEdge(f);
        e.addEdge(f);
        f.addEdge(h);

        Graph resolvedGraph = new DependencyResolver.Builder<String>()
                .withDependencyGraph(graph)
                .createResolver()
                .resolve();

    }

    @Test
    void exceptionTesting() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("a message");
        });
        assertEquals("a message", exception.getMessage());
    }
}
