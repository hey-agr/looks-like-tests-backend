package ru.agr.backend.looksliketests.config.security.jwt;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class JwtAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
   @Override
   public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
      return Mono.error(new AuthenticationServiceException(ex.getMessage()));
   }
}
