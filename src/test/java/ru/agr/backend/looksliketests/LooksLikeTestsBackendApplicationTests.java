package ru.agr.backend.looksliketests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ru.agr.backend.looksliketests.utils.SpringPostgresIntegrationTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringPostgresIntegrationTest
class LooksLikeTestsBackendApplicationTests {
    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        log.info("LooksLikeTests backend successfully started!");
        assertNotNull(context.getId());
    }
}
