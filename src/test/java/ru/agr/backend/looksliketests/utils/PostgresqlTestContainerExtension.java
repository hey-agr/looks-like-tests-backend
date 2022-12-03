package ru.agr.backend.looksliketests.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
public class PostgresqlTestContainerExtension
        implements BeforeAllCallback, AfterAllCallback {
    private static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:13"));

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        POSTGRES.start();
        log.info("Started PostgreSQL test container, url: {} username: {} password: {}",
                POSTGRES.getJdbcUrl(),
                POSTGRES.getUsername(),
                POSTGRES.getPassword());
        System.setProperty("TEST_DB_URL", POSTGRES.getJdbcUrl());
        System.setProperty("TEST_DB_USERNAME", POSTGRES.getUsername());
        System.setProperty("TEST_DB_PASSWORD", POSTGRES.getPassword());
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        if (POSTGRES.isRunning()) {
            log.info("Stopping PostgreSQL test container, url: {} username: {} password {}",
                    POSTGRES.getJdbcUrl(),
                    POSTGRES.getUsername(),
                    POSTGRES.getPassword());
            POSTGRES.stop();
        }
    }
}
