package ru.agr.backend.looksliketests.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Arslan Rabadanov
 */
@Configuration
@EnableJpaRepositories(basePackages = "ru.agr.backend.looksliketests.repository")
public class MainConfig {
}
