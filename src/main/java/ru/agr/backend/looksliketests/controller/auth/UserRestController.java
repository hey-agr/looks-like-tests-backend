package ru.agr.backend.looksliketests.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.agr.backend.looksliketests.config.security.exception.UserNotFoundException;
import ru.agr.backend.looksliketests.controller.ApiVersion;
import ru.agr.backend.looksliketests.controller.auth.dto.UserCreateDto;
import ru.agr.backend.looksliketests.controller.auth.dto.UserUpdateDto;
import ru.agr.backend.looksliketests.controller.auth.exception.DuplicateEmailException;
import ru.agr.backend.looksliketests.controller.auth.exception.DuplicateUsernameException;
import ru.agr.backend.looksliketests.controller.auth.exception.DuplicationException;
import ru.agr.backend.looksliketests.controller.auth.mapper.UserMapper;
import ru.agr.backend.looksliketests.controller.auth.mapper.UserMergerMapper;
import ru.agr.backend.looksliketests.controller.resources.UserResource;
import ru.agr.backend.looksliketests.service.auth.UserService;

import static java.util.Objects.nonNull;


@RequiredArgsConstructor
@RestController
@RequestMapping(ApiVersion.API_V1+"/user")
@Validated
public class UserRestController {
   private static final String USER_LOGIN_EXISTS_ERROR_MESSAGE = "User with login: '%s' already exists";
   private static final String USER_EMAIL_EXISTS_ERROR_MESSAGE = "User with email: '%s' already exists";

   private final UserService userService;
   private final UserMapper userMapper;
   private final UserMergerMapper userMergerMapper;


   @GetMapping
   public ResponseEntity<UserResource> getCurrent() {
      final var user = userService.getUserWithAuthorities()
              .orElseThrow(UserNotFoundException::new);
      return ResponseEntity.ok(userMapper.toUserResource(user));
   }

   @PostMapping
   public ResponseEntity<UserResource> register(@RequestBody @Valid UserCreateDto userCreateDto) throws DuplicationException {
      if (userService.findByUsername(userCreateDto.username()).isPresent()) {
         throw new DuplicateUsernameException(String.format(USER_LOGIN_EXISTS_ERROR_MESSAGE, userCreateDto.username()));
      }
      if (nonNull(userCreateDto.email()) && userService.findByEmail(userCreateDto.email()).isPresent()) {
         throw new DuplicateEmailException(String.format(USER_EMAIL_EXISTS_ERROR_MESSAGE, userCreateDto.email()));
      }
      var userToSave = userMapper.toEntity(userCreateDto);
      // user is always active, in future there will be email for activaion of user's account
      userToSave.setActivated(true);
      var savedUser = userService.save(userToSave);
      return ResponseEntity.ok(
              userMapper.toUserResource(savedUser)
      );
   }

   @PatchMapping
   public ResponseEntity<UserResource> patchCurrentUser(@RequestBody @Valid UserUpdateDto userUpdateDto) throws DuplicationException {
      var user = userService.getUserWithAuthorities()
              .orElseThrow(UserNotFoundException::new);
      if (nonNull(userUpdateDto.getEmail())) {
         var userByEmail = userService.findByEmail(userUpdateDto.getEmail()).orElse(null);
         if (nonNull(userByEmail) && !userByEmail.getId().equals(user.getId())) {
            throw new DuplicateEmailException(String.format(USER_EMAIL_EXISTS_ERROR_MESSAGE, userUpdateDto.getEmail()));
         }
      }
      var updatedUser = userService.save(userMergerMapper.toEntity(userUpdateDto, user));
      return ResponseEntity.ok(userMapper.toUserResource(updatedUser));
   }
}
