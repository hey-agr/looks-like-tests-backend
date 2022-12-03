package ru.agr.backend.looksliketests.repository.auth;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.agr.backend.looksliketests.entity.auth.Authority;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, String> {
}
