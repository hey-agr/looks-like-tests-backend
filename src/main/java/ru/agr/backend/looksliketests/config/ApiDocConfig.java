package ru.agr.backend.looksliketests.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.agr.backend.looksliketests.config.security.SecurityConstants;
import ru.agr.backend.looksliketests.controller.ApiVersion;

import java.util.Collections;

/**
 * @author Arslan Rabadanov
 */
@Configuration
public class ApiDocConfig {
    private static final String API_KEY = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addServersItem(getServer())
                .components(new Components()
                        .addSecuritySchemes(API_KEY, getSecurityScheme()))
                .security(Collections.singletonList(new SecurityRequirement().addList(API_KEY)))
                .info(getInfo());
    }

    private SecurityScheme getSecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name(SecurityConstants.AUTHORIZATION_HEADER)
                .scheme("bearer")
                .bearerFormat("JWT");
    }

    private Info getInfo() {
        return new Info().title("LooksLikeTests API").version(ApiVersion.API_V1)
                .license(new License().name("Apache 2.0").url("NO URL"));
    }

    private Server getServer() {
        return new Server().url("/");
    }
}
