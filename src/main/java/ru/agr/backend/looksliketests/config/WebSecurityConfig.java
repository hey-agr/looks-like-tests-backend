package ru.agr.backend.looksliketests.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import ru.agr.backend.looksliketests.config.security.JwtAccessDeniedHandler;
import ru.agr.backend.looksliketests.config.security.JwtAuthenticationEntryPoint;
import ru.agr.backend.looksliketests.config.security.jwt.JWTConfigurer;
import ru.agr.backend.looksliketests.config.security.jwt.TokenProvider;
import ru.agr.backend.looksliketests.controller.ApiVersion;

import static org.springframework.security.config.Customizer.withDefaults;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
public class WebSecurityConfig {
   private final TokenProvider tokenProvider;
   private final CorsFilter corsFilter;
   private final JwtAuthenticationEntryPoint authenticationErrorHandler;
   private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

   private static final String[] SWAGGER_LIST = {
           "/v3/api-docs/**",
           "/v2/api-docs/**",
           "/swagger-ui/**",
           "/swagger-ui.html",
           "/actuator/**",
           "/api-docs"
   };
   private static final String[] POST_WHITE_LIST = {
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
      return (web) -> web.ignoring()
              .requestMatchers(HttpMethod.OPTIONS, "/**")
              .requestMatchers(SWAGGER_LIST);
   }

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      return http.authorizeHttpRequests(this::authorizeHttpRequestsConfiguration)
              .httpBasic(withDefaults())
              .build();
   }

   private void authorizeHttpRequestsConfiguration(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authz) {
      try {
         authz.and().csrf().disable()
                 .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                 .exceptionHandling()
                 .authenticationEntryPoint(authenticationErrorHandler)
                 .accessDeniedHandler(jwtAccessDeniedHandler)
                 // create no session
                 .and()
                 .sessionManagement()
                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 .and()
                 .authorizeHttpRequests()
                 .requestMatchers(SWAGGER_LIST).permitAll()
                 .requestMatchers(HttpMethod.POST, POST_WHITE_LIST).permitAll()
                 .anyRequest().authenticated()
                 .and()
                 .apply(securityConfigurerAdapter());
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   private JWTConfigurer securityConfigurerAdapter() {
      return new JWTConfigurer(tokenProvider);
   }
}
