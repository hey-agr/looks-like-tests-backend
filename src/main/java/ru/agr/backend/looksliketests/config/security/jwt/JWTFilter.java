package ru.agr.backend.looksliketests.config.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import ru.agr.backend.looksliketests.config.security.SecurityConstants;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTFilter implements WebFilter {
   private final TokenProvider tokenProvider;

   @Override
   public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
      var request = exchange.getRequest();
      var jwt = resolveToken(request);
      var requestURI = request.getURI().toString();

      if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
         Authentication authentication = tokenProvider.getAuthentication(jwt);
         SecurityContextHolder.getContext().setAuthentication(authentication);
         log.debug("set Authentication to security context for '{}', uri: {}", authentication.getName(), requestURI);
      } else {
         log.debug("no valid JWT token found, uri: {}", requestURI);
      }

      return chain.filter(exchange);
   }

   private String resolveToken(ServerHttpRequest request) {
      String bearerToken = request.getHeaders().getFirst(SecurityConstants.AUTHORIZATION_HEADER);
      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
         return bearerToken.substring(7);
      }
      return null;
   }
}
