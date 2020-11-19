package com.rewedigital.neo4j.perftest;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TestRun {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final GraphDatabaseService graphDatabaseService;

    public TestRun(GraphDatabaseService graphDatabaseService) {
        this.graphDatabaseService = graphDatabaseService;
    }

    public void run() {
        long start = System.currentTimeMillis();
        Label label = Label.label("root");
        try (final Transaction tx = graphDatabaseService.beginTx()) {
            for (int i = 1; i <= 100; i++) {
                graphDatabaseService.createNode(label);
            }
            tx.success();
        }
        for (int i = 1; i <= 10; i++) {
            long txStart = System.currentTimeMillis();
            try (final Transaction tx = graphDatabaseService.beginTx()) {
                ResourceIterator<Node> nodes = graphDatabaseService.findNodes(label);
                nodes.forEachRemaining(node -> {
                    for (int j = 1; j <= 100; j++) {
                        node.setProperty(UUID.randomUUID().toString(), "1");
                    }
                });
                tx.success();
            }
            long txEnd = System.currentTimeMillis();
            log.info("Finished tx {} in {} ms", i, (txEnd-txStart));
        }
        long end = System.currentTimeMillis();
        log.info("Took {} ms.", (end-start));
    }
}
