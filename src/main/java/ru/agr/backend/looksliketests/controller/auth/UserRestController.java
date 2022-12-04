package ru.agr.backend.looksliketests.controller.auth;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.agr.backend.looksliketests.config.security.exception.UserNotFoundException;
import ru.agr.backend.looksliketests.controller.ApiVersion;
import ru.agr.backend.looksliketests.controller.auth.dto.UserCreateDto;
import ru.agr.backend.looksliketests.controller.auth.dto.UserResource;
import ru.agr.backend.looksliketests.controller.auth.dto.UserUpdateDto;
import ru.agr.backend.looksliketests.controller.auth.mapper.UserMapper;
import ru.agr.backend.looksliketests.controller.auth.mapper.UserMergerMapper;
import ru.agr.backend.looksliketests.service.auth.UserService;


@RequiredArgsConstructor
@RestController
@RequestMapping(ApiVersion.API_V1+"/user")
public class UserRestController {

   private final UserService userService;
   private final UserMapper userMapper;
   private final UserMergerMapper userMergerMapper;


   @GetMapping
   public ResponseEntity<UserResource> getCurrent() {
      final var user = userService.getUserWithAuthorities()
              .orElseThrow(() -> new UserNotFoundException("User doesn't exist"));
      return ResponseEntity.ok(userMapper.toUserResource(user));
   }

   @PostMapping
   public ResponseEntity<UserResource> register(@RequestBody @Valid UserCreateDto userCreateDto) {
      var savedUser = userService.save(userMapper.toEntity(userCreateDto));
      return ResponseEntity.ok(
              userMapper.toUserResource(savedUser)
      );
   }

   @PatchMapping("/{id}")
   public ResponseEntity<UserResource> update(@PathVariable Long id,
                                      @RequestBody @Valid UserUpdateDto userUpdateDto) {
      var user = userService.findById(id)
              .orElseThrow(() -> new UserNotFoundException("User doesn't exist"));
      var updatedUser = userService.save(userMergerMapper.toEntity(userUpdateDto, user));
      return ResponseEntity.ok(userMapper.toUserResource(updatedUser));
   }
}
