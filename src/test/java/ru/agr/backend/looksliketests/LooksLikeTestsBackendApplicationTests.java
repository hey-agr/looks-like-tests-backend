package ru.agr.backend.looksliketests;

import org.junit.jupiter.api.Test;
import ru.agr.backend.looksliketests.utils.SpringPostgresIntegrationTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringPostgresIntegrationTest
class LooksLikeTestsBackendApplicationTests {
    @Test
    void contextLoads() {
        assertTrue(true, "LooksLikeTests backend successfully started!");
    }
}
