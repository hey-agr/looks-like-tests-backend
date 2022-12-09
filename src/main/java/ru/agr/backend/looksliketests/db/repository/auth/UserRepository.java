package ru.agr.backend.looksliketests.db.repository.auth;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.agr.backend.looksliketests.db.entity.auth.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

   @EntityGraph(attributePaths = "authorities")
   Optional<User> findOneWithAuthoritiesByUsername(String username);

   @EntityGraph(attributePaths = "authorities")
   Optional<User> findOneWithAuthoritiesByEmail(String email);

   @EntityGraph(attributePaths = "authorities")
   Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

   @EntityGraph(attributePaths = "authorities")
   Optional<User> findOneWithAuthoritiesById(Long id);

}
