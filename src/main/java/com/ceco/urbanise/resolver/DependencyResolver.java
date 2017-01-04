package com.ceco.urbanise.resolver;

import com.ceco.urbanise.model.Node;

import java.util.List;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * @since 04-Jan-2017
 */
public class DependencyResolver {

    public void resolve(Node node, List<Node> resolved) {
        for (Node edge : node.edges) {
            if (!resolved.contains(edge)) {
                resolve(edge, resolved);
            }
        }
        resolved.add(node);
    }

}
