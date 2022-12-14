package ru.agr.backend.looksliketests.config.security;

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
import ru.agr.backend.looksliketests.config.security.jwt.JWTConfigurer;
import ru.agr.backend.looksliketests.config.security.jwt.JwtAccessDeniedHandler;
import ru.agr.backend.looksliketests.config.security.jwt.JwtAuthenticationEntryPoint;
import ru.agr.backend.looksliketests.config.security.jwt.TokenProvider;
import ru.agr.backend.looksliketests.controller.ApiVersion;
import ru.agr.backend.looksliketests.db.entity.auth.UserAuthority;

import static org.springframework.security.config.Customizer.withDefaults;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
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
      return (web) -> web.ignoring()
              .mvcMatchers(ACTUATOR_WHITELIST)
              .mvcMatchers(SWAGGER_WHITELIST)
              .mvcMatchers(HttpMethod.OPTIONS, "/**");
   }

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      return http.authorizeHttpRequests(this::authorizeHttpRequestsConfiguration)
              .httpBasic(withDefaults())
              .build();
   }

   private void authorizeHttpRequestsConfiguration(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authz) {
      try {
         var adminAuthority = UserAuthority.AuthorityName.ADMIN.name();

         authz.and().csrf().disable()
                 .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                 .exceptionHandling()
                 .authenticationEntryPoint(authenticationErrorHandler)
                 .accessDeniedHandler(jwtAccessDeniedHandler)
                 .and()
                 .sessionManagement()
                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 .and()
                 .authorizeHttpRequests()
                 .mvcMatchers(HttpMethod.POST, POST_WHITELIST).permitAll()
                 .mvcMatchers(ACTUATOR_WHITELIST).permitAll()
                 .mvcMatchers(SWAGGER_WHITELIST).permitAll()
                 //.mvcMatchers(HttpMethod.POST, ApiVersion.API_V1+"/tests").hasAnyAuthority(adminAuthority)
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
