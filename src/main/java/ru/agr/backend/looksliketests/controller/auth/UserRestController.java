package ru.agr.backend.looksliketests.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.agr.backend.looksliketests.config.security.UserNotFoundException;
import ru.agr.backend.looksliketests.controller.ApiVersion;
import ru.agr.backend.looksliketests.controller.auth.dto.UserCreateDto;
import ru.agr.backend.looksliketests.controller.auth.dto.UserUpdateDto;
import ru.agr.backend.looksliketests.entity.auth.User;
import ru.agr.backend.looksliketests.service.auth.UserService;
import ru.agr.backend.looksliketests.service.mapper.UserMapper;
import ru.agr.backend.looksliketests.service.mapper.UserMergerMapper;


@RequiredArgsConstructor
@RestController
@RequestMapping(ApiVersion.API_V1+"/user")
public class UserRestController {

   private final UserService userService;
   private final UserMapper userMapper;
   private final UserMergerMapper userMergerMapper;


   @GetMapping
   public ResponseEntity<User> getCurrent() {
      final var user = userService.getUserWithAuthorities()
              .orElseThrow(() -> new UserNotFoundException("User doesn't exist"));
      return ResponseEntity.ok(user);
   }

   @PostMapping
   public ResponseEntity<User> register(@RequestBody @Valid UserCreateDto userCreateDto) {
      return ResponseEntity.ok(
              userService.save(userMapper.toEntity(userCreateDto))
      );
   }

   @PatchMapping("/{id}")
   public ResponseEntity<User> update(@PathVariable Long id,
                                      @RequestBody @Valid UserUpdateDto userUpdateDto) {
      var user = userService.findById(id)
              .orElseThrow(() -> new UserNotFoundException("User doesn't exist"));
      return ResponseEntity.ok(
              userService.save(
                      userMergerMapper.toEntity(userUpdateDto, user)
              )
      );
   }
}
