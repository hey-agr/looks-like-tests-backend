package ru.agr.backend.looksliketests.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import ru.agr.backend.looksliketests.controller.ApiVersion;

import java.util.List;

@Configuration
public class CorsConfig {
   @Bean
   public CorsWebFilter corsWebFilter() {
      CorsConfiguration corsConfig = new CorsConfiguration();
      corsConfig.setAllowCredentials(true);
      corsConfig.setAllowedOriginPatterns(List.of("*"));
      corsConfig.addAllowedHeader("*");
      corsConfig.addAllowedMethod("*");

      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration(ApiVersion.API_V1+"/**", corsConfig);

      return new CorsWebFilter(source);
   }
}
