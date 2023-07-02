package ru.agr.backend.looksliketests.utils;


import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ActiveProfiles;
import ru.agr.backend.looksliketests.config.security.CorsConfig;
import ru.agr.backend.looksliketests.config.security.WebSecurityConfig;
import ru.agr.backend.looksliketests.config.security.jwt.JwtAccessDeniedHandler;
import ru.agr.backend.looksliketests.config.security.jwt.JwtAuthenticationEntryPoint;
import ru.agr.backend.looksliketests.config.security.jwt.TokenProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ActiveProfiles("test")
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({WebSecurityConfig.class, TokenProvider.class, CorsConfig.class, JwtAuthenticationEntryPoint.class, JwtAccessDeniedHandler.class})
@WebMvcTest
public @interface SpringBootControllerTest {
    @AliasFor(annotation = WebMvcTest.class, attribute = "controllers")
    Class<?>[] value() default {};

    @AliasFor(annotation = WebMvcTest.class, attribute = "controllers")
    Class<?>[] controllers() default {};
}
