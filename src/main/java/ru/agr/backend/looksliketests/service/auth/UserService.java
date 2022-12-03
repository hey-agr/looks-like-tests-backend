package ru.agr.backend.looksliketests.service.auth;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.agr.backend.looksliketests.config.security.SecurityUtils;
import ru.agr.backend.looksliketests.entity.auth.User;
import ru.agr.backend.looksliketests.repository.auth.UserRepository;

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
      return userRepository.findById(id);
   }

   public User save(User user) {
      return userRepository.save(user);
   }
}