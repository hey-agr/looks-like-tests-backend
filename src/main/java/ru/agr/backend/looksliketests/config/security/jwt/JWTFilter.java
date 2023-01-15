package ru.agr.backend.looksliketests.config.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import ru.agr.backend.looksliketests.config.security.SecurityConstants;

import java.io.IOException;

@Slf4j
public class JWTFilter extends GenericFilterBean {
   private final TokenProvider tokenProvider;

   public JWTFilter(TokenProvider tokenProvider) {
      this.tokenProvider = tokenProvider;
   }

   @Override
   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
      HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
      String jwt = resolveToken(httpServletRequest);
      String requestURI = httpServletRequest.getRequestURI();

      if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
         Authentication authentication = tokenProvider.getAuthentication(jwt);
         SecurityContextHolder.getContext().setAuthentication(authentication);
         log.debug("set Authentication to security context for '{}', uri: {}", authentication.getName(), requestURI);
      } else {
         log.debug("no valid JWT token found, uri: {}", requestURI);
      }

      filterChain.doFilter(servletRequest, servletResponse);
   }

   private String resolveToken(HttpServletRequest request) {
      String bearerToken = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
         return bearerToken.substring(7);
      }
      return null;
   }
}
