package ru.agr.backend.looksliketests.config.security.jwt;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class JwtAccessDeniedHandler implements ServerAccessDeniedHandler {
   @Override
   public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
      return Mono.error(new AccessDeniedException(denied.getMessage()));
   }
}
