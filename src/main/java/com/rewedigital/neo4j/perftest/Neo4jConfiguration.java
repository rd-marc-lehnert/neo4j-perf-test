package com.rewedigital.neo4j.perftest;

import org.apache.commons.io.FileUtils;
import org.neo4j.configuration.GraphDatabaseSettings;
import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;

import static org.neo4j.configuration.GraphDatabaseSettings.DEFAULT_DATABASE_NAME;

@Configuration
public class Neo4jConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(Neo4jConfiguration.class);

    private DatabaseManagementService managementService;

    @PostConstruct
    private void initGraphDatabase() throws IOException {
        final File databaseDir = new File("neo4j.db").getCanonicalFile();
        managementService = new DatabaseManagementServiceBuilder(databaseDir)
                .setConfig(GraphDatabaseSettings.pagecache_memory, "256m")
                .setConfig(GraphDatabaseSettings.store_internal_log_level, Level.valueOf("INFO"))
                .setConfig(GraphDatabaseSettings.logical_log_rotation_threshold, 1048576L)
                .setConfig(GraphDatabaseSettings.keep_logical_logs, "1 files")
                .setConfig(GraphDatabaseSettings.check_point_interval_tx, 10)
                .build();
        LOG.info("Successfully initialized graph database at '{}'.", databaseDir.getAbsolutePath());
    }

    @Bean
    public GraphDatabaseService graphDatabaseService() {
        return managementService.database(DEFAULT_DATABASE_NAME);
    }

    @PreDestroy
    private void gracefulShutdown() {
        LOG.info("Shutting down graph database ...");
        managementService.shutdown();
        deleteDatabaseDirIfAppropriate();
        LOG.info("Successfully shut down graph database.");
    }

    private void deleteDatabaseDirIfAppropriate() {
        final File databaseDir = new File("neo4j.db");
        try {
            FileUtils.deleteDirectory(databaseDir);
            LOG.warn("Successfully removed Neo4j directory '{}'.", databaseDir.getAbsolutePath());
        } catch (final IOException e) {
            LOG.warn("Failed to remove Neo4j directory '{}'.", databaseDir.getAbsolutePath());
        }
    }
}
