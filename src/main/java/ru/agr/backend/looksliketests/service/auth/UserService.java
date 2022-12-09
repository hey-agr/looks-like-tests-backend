package ru.agr.backend.looksliketests.service.auth;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.agr.backend.looksliketests.config.security.SecurityUtils;
import ru.agr.backend.looksliketests.db.entity.auth.User;
import ru.agr.backend.looksliketests.db.repository.auth.UserRepository;

import java.util.Optional;

@Service
@Transactional
public class UserService {

   private final UserRepository userRepository;

   public UserService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Transactional(readOnly = true)
   public Optional<User> getUserWithAuthorities() {
      return SecurityUtils.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
   }

   @Transactional(readOnly = true)
   public Optional<User> findById(Long id) {
      return userRepository.findOneWithAuthoritiesById(id);
   }

   public Optional<User> findByUsername(@NonNull String username) {
      return userRepository.findOneWithAuthoritiesByUsername(username);
   }

   public Optional<User> findByEmail(@NonNull String email) {
      return userRepository.findOneWithAuthoritiesByEmail(email);
   }

   public User save(User user) {
      return userRepository.save(user);
   }
}
