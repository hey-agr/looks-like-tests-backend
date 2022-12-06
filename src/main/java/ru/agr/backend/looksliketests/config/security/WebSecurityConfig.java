package ru.agr.backend.looksliketests.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.reactive.CorsWebFilter;
import ru.agr.backend.looksliketests.config.security.jwt.JWTFilter;
import ru.agr.backend.looksliketests.config.security.jwt.JwtAccessDeniedHandler;
import ru.agr.backend.looksliketests.config.security.jwt.JwtAuthenticationEntryPoint;
import ru.agr.backend.looksliketests.controller.ApiVersion;
import ru.agr.backend.looksliketests.db.entity.auth.UserAuthority;

import static org.springframework.security.config.Customizer.withDefaults;


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
public class WebSecurityConfig {
   private final CorsWebFilter corsFilter;
   private final JWTFilter jwtFilter;
   private final JwtAuthenticationEntryPoint authenticationErrorHandler;
   private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

   private static final String[] SWAGGER_WHITELIST = {
           "/v3/api-docs/**",
           "/swagger-ui/**",
           "/v2/api-docs/**",
           "/swagger-resources/**"
   };

   private static final String[] ACTUATOR_WHITELIST = {
           "/actuator/**"
   };

   private static final String[] POST_WHITELIST = {
           ApiVersion.API_V1 + "/authenticate",
           ApiVersion.API_V1 + "/user",
   };

   public WebSecurityConfig(
      CorsWebFilter corsFilter,
      JWTFilter jwtFilter,
      JwtAuthenticationEntryPoint authenticationErrorHandler,
      JwtAccessDeniedHandler jwtAccessDeniedHandler) {
      this.corsFilter = corsFilter;
      this.jwtFilter = jwtFilter;
      this.authenticationErrorHandler = authenticationErrorHandler;
      this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public WebSecurityCustomizer webSecurityCustomizer() {
      return (web) -> web.ignoring()
              .mvcMatchers(ACTUATOR_WHITELIST)
              .mvcMatchers(SWAGGER_WHITELIST)
              .mvcMatchers(HttpMethod.OPTIONS, "/**");
   }

   @Bean
   public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
      return http.authorizeExchange(this::authorizeHttpRequestsConfiguration)
              .httpBasic(withDefaults())
              .build();
   }

   private void authorizeHttpRequestsConfiguration(ServerHttpSecurity.AuthorizeExchangeSpec authz) {
      try {
         authz.and().csrf().disable()
                 .addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                 .addFilterBefore(corsFilter, SecurityWebFiltersOrder.CORS)
                 .exceptionHandling()
                 .authenticationEntryPoint(authenticationErrorHandler)
                 .accessDeniedHandler(jwtAccessDeniedHandler)
                 .and()
                 .authorizeExchange()
                 .pathMatchers(HttpMethod.POST, POST_WHITELIST).permitAll()
                 .pathMatchers(ACTUATOR_WHITELIST).permitAll()
                 .pathMatchers(SWAGGER_WHITELIST).permitAll()
                 .pathMatchers(HttpMethod.POST, ApiVersion.API_V1+"/tests")
                 .hasAnyAuthority(UserAuthority.AuthorityName.TEACHER.name(), UserAuthority.AuthorityName.ADMIN.name())
                 .anyExchange().authenticated();
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }
}
