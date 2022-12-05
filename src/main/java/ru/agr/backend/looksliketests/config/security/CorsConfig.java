package ru.agr.backend.looksliketests.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.agr.backend.looksliketests.controller.ApiVersion;

import javax.validation.constraints.NotNull;
import java.util.List;

@Configuration
public class CorsConfig {

   @Bean
   public CorsFilter corsFilter() {
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowCredentials(true);
      config.setAllowedOriginPatterns(List.of("*"));
      config.addAllowedHeader("*");
      config.addAllowedMethod("*");

      source.registerCorsConfiguration(ApiVersion.API_V1+"/**", config);
      return new CorsFilter(source);
   }

}
