package ru.agr.backend.looksliketests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.agr.backend.looksliketests.utils.SpringPostgresIntegrationTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringPostgresIntegrationTest
class LooksLikeTestsBackendApplicationTests {
    @Test
    void contextLoads() {
        log.info("LooksLikeTests backend successfully started!");
    }
}
