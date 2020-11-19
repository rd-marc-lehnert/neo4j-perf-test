package com.rewedigital.neo4j.perftest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class PerfTestApplication {

    @Autowired
    TestRun testRun;

    private final static Logger log = LoggerFactory.getLogger(PerfTestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PerfTestApplication.class, args);
    }

    @PostConstruct
    public void test() {
        testRun.run();
    }

}
