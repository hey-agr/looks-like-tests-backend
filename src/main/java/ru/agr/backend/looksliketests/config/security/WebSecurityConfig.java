package ru.agr.backend.looksliketests.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import ru.agr.backend.looksliketests.config.security.jwt.JWTFilter;
import ru.agr.backend.looksliketests.config.security.jwt.JwtAccessDeniedHandler;
import ru.agr.backend.looksliketests.config.security.jwt.JwtAuthenticationEntryPoint;
import ru.agr.backend.looksliketests.config.security.jwt.TokenProvider;
import ru.agr.backend.looksliketests.controller.ApiVersion;

import static org.springframework.security.config.Customizer.withDefaults;


@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Configuration
public class WebSecurityConfig {
   private final TokenProvider tokenProvider;
   private final CorsFilter corsFilter;
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
      TokenProvider tokenProvider,
      CorsFilter corsFilter,
      JwtAuthenticationEntryPoint authenticationErrorHandler,
      JwtAccessDeniedHandler jwtAccessDeniedHandler
   ) {
      this.tokenProvider = tokenProvider;
      this.corsFilter = corsFilter;
      this.authenticationErrorHandler = authenticationErrorHandler;
      this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public WebSecurityCustomizer webSecurityCustomizer() {
      return web -> web.ignoring()
              .requestMatchers(ACTUATOR_WHITELIST)
              .requestMatchers(SWAGGER_WHITELIST)
              .requestMatchers(HttpMethod.OPTIONS, "/**");
   }

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      return http.csrf(AbstractHttpConfigurer::disable)
              .addFilterBefore(new JWTFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
              .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
              .exceptionHandling(
                      exceptionHandler -> exceptionHandler.authenticationEntryPoint(authenticationErrorHandler)
                              .accessDeniedHandler(jwtAccessDeniedHandler)
              )
              .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .authorizeHttpRequests(WebSecurityConfig::authorizeRequests)
              .httpBasic(withDefaults())
              .build();
   }

   private static AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizeRequests(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry requests) {
      return requests.requestMatchers(HttpMethod.POST, POST_WHITELIST).permitAll()
              .requestMatchers(ACTUATOR_WHITELIST).permitAll()
              .requestMatchers(SWAGGER_WHITELIST).permitAll()
              .anyRequest().authenticated();
   }
}
