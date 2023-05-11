package ru.agr.backend.looksliketests.service.auth;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.agr.backend.looksliketests.config.security.SecurityUtils;
import ru.agr.backend.looksliketests.config.security.exception.UserNotFoundException;
import ru.agr.backend.looksliketests.db.entity.auth.User;
import ru.agr.backend.looksliketests.db.entity.auth.UserAuthority;
import ru.agr.backend.looksliketests.db.repository.auth.UserRepository;
import ru.agr.backend.looksliketests.db.repository.filter.UserSpecificationFilter;
import ru.agr.backend.looksliketests.db.repository.specification.UserSpecification;

import java.util.Optional;

import static java.util.Objects.nonNull;

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
   public Optional<User> findById(@NonNull Long id) {
      return userRepository.findOneWithAuthoritiesById(id);
   }

   public boolean checkIfUsersAuthorityExists(@NonNull Long id, @NonNull UserAuthority.AuthorityName authorityName) throws UserNotFoundException {
      var user = findById(id)
              .filter(u -> u.getAuthorities().stream().anyMatch(authority -> authority.getName() == authorityName))
              .orElseThrow(() -> new UserNotFoundException(id));
      return nonNull(user);
   }

   public Optional<User> findByUsername(@NonNull String username) {
      return userRepository.findOneWithAuthoritiesByUsername(username);
   }

   public Optional<User> findByEmail(@NonNull String email) {
      return userRepository.findOneWithAuthoritiesByEmail(email);
   }

   public User save(@NonNull User user) {
      return userRepository.save(user);
   }

   public Page<User> findAllFiltered(UserSpecificationFilter filter, Pageable pageable) {
      return userRepository.findAll(new UserSpecification(filter), pageable);
   }
}
