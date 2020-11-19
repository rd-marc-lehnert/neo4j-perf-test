package com.rewedigital.neo4j.perftest;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.UUID;

@SpringBootApplication
public class PerfTestApplication {

    @Autowired
    TestRun testRun;

    public static void main(String[] args) {
        SpringApplication.run(PerfTestApplication.class, args);
    }

    @PostConstruct
    public void test() {
        testRun.run();
    }

}
