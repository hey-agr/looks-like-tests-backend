package ru.agr.backend.looksliketests.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.agr.backend.looksliketests.config.security.exception.UserNotFoundException;
import ru.agr.backend.looksliketests.controller.ApiVersion;
import ru.agr.backend.looksliketests.controller.auth.dto.UserCreateDto;
import ru.agr.backend.looksliketests.controller.auth.dto.UserResource;
import ru.agr.backend.looksliketests.controller.auth.dto.UserUpdateDto;
import ru.agr.backend.looksliketests.controller.auth.exception.DuplicateEmailException;
import ru.agr.backend.looksliketests.controller.auth.exception.DuplicateUsernameException;
import ru.agr.backend.looksliketests.controller.auth.exception.DuplicationException;
import ru.agr.backend.looksliketests.controller.auth.mapper.UserMapper;
import ru.agr.backend.looksliketests.controller.auth.mapper.UserMergerMapper;
import ru.agr.backend.looksliketests.service.auth.UserService;

import javax.validation.Valid;

import static java.util.Objects.nonNull;


@RequiredArgsConstructor
@RestController
@RequestMapping(ApiVersion.API_V1+"/user")
@Validated
public class UserRestController {

   private final UserService userService;
   private final UserMapper userMapper;
   private final UserMergerMapper userMergerMapper;


   @GetMapping
   public Mono<UserResource> getCurrent() {
      final var user = userService.getUserWithAuthorities()
              .orElseThrow(() -> new UserNotFoundException("User doesn't exist"));
      return Mono.just(userMapper.toUserResource(user));
   }

   @PostMapping
   public Mono<UserResource> register(@RequestBody @Valid UserCreateDto userCreateDto) throws DuplicationException {
      if (userService.findByUsername(userCreateDto.username()).isPresent()) {
         throw new DuplicateUsernameException("User with login: '"+userCreateDto.username()+"' already exists");
      }
      if (nonNull(userCreateDto.email()) && userService.findByEmail(userCreateDto.email()).isPresent()) {
         throw new DuplicateEmailException("User with email: '"+userCreateDto.email()+"' already exists");
      }
      // user active
      var userToSave = userMapper.toEntity(userCreateDto);
      userToSave.setActivated(true);
      var savedUser = userService.save(userToSave);
      return Mono.just(userMapper.toUserResource(savedUser));
   }

   @PatchMapping("/{id}")
   public Mono<UserResource> update(@PathVariable Long id,
                                              @RequestBody @Valid UserUpdateDto userUpdateDto) throws DuplicationException {
      var user = userService.findById(id)
              .orElseThrow(() -> new UserNotFoundException("User doesn't exist"));
      if (nonNull(userUpdateDto.getEmail())) {
         var userByEmail = userService.findByEmail(userUpdateDto.getEmail()).orElse(null);
         if (nonNull(userByEmail) && !userByEmail.getId().equals(id)) {
            throw new DuplicateEmailException("User with email: '"+userUpdateDto.getEmail()+"' already exists");
         }
      }
      var updatedUser = userService.save(userMergerMapper.toEntity(userUpdateDto, user));
      return Mono.just(userMapper.toUserResource(updatedUser));
   }
}
