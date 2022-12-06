package ru.agr.backend.looksliketests.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.agr.backend.looksliketests.config.security.SecurityConstants;
import ru.agr.backend.looksliketests.config.security.jwt.TokenProvider;
import ru.agr.backend.looksliketests.controller.ApiVersion;
import ru.agr.backend.looksliketests.controller.auth.dto.JWTToken;
import ru.agr.backend.looksliketests.controller.auth.dto.LoginDto;
import ru.agr.backend.looksliketests.db.entity.auth.AuthenticationService;

import javax.validation.Valid;



@RestController
@RequiredArgsConstructor
@RequestMapping(ApiVersion.API_V1)
public class AuthenticationRestController {
   private final TokenProvider tokenProvider;
   private final AuthenticationService authenticationService;

   @PostMapping("/authenticate")
   public Mono<ResponseEntity<JWTToken>> authorize(@Valid @RequestBody LoginDto loginDto) {

      authenticationService.authenticate(loginDto.getUsername(), loginDto.getPassword());
      UsernamePasswordAuthenticationToken authenticationToken =
         new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);

      boolean rememberMe = loginDto.getRememberMe() != null && loginDto.getRememberMe();
      String jwt = tokenProvider.createToken(authenticationToken, rememberMe);

      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.add(SecurityConstants.AUTHORIZATION_HEADER, "Bearer " + jwt);

      return Mono.just(new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK));
   }
}
