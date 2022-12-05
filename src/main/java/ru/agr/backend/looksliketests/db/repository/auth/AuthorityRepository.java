package ru.agr.backend.looksliketests.db.repository.auth;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.agr.backend.looksliketests.db.entity.auth.UserAuthority;

@Repository
public interface AuthorityRepository extends CrudRepository<UserAuthority, Long> {
}
