package com.example.movies.dao;

import org.testcontainers.containers.PostgreSQLContainer;

public abstract class BaseDBTest {

    protected static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.0")
            .withDatabaseName("movies")
            .withUsername("movies_user")
            .withPassword("password")
            .withInitScript("init.sql");

    static {
        container.start();
    }

}
