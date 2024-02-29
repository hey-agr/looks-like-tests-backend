package ru.agr.backend.looksliketests.service.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;
import ru.agr.backend.looksliketests.config.security.exception.UserNotActivatedException;
import ru.agr.backend.looksliketests.db.entity.auth.User;
import ru.agr.backend.looksliketests.db.entity.auth.UserAuthority;
import ru.agr.backend.looksliketests.db.repository.auth.UserRepository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserModelDetailsServiceTest {

    public static final String CREATE_SPRING_SECURITY_USER_METHOD_NAME = "createSpringSecurityUser";
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserModelDetailsService service;

    @Test
    void loadUserByUsername() {
        var givenUsername = "SoMeUserName";
        var givenPassword = "SuperSecretPassword";

        var givenAuthorities = List.of(
                UserAuthority.builder().name(UserAuthority.AuthorityName.STUDENT).build()
        );

        var givenUser = User.builder()
                .username(givenUsername)
                .password(givenPassword)
                .activated(true)
                .authorities(givenAuthorities)
                .build();

        var givenUsernameLowerCase = givenUsername.toLowerCase(Locale.ENGLISH);
        when(userRepository.findOneWithAuthoritiesByUsername(givenUsernameLowerCase))
                .thenReturn(Optional.of(givenUser));

        var expectedAuthorities = List.of(
                new SimpleGrantedAuthority(UserAuthority.AuthorityName.STUDENT.name()));

        var expectedUser = new org.springframework.security.core.userdetails.User(
                givenUsername,
                givenPassword,
                expectedAuthorities
        );

        assertEquals(expectedUser,
                service.loadUserByUsername(givenUsername));
    }

    @Test
    void loadUserByUsernameNotFound() {
        var givenUsername = "SoMeUserName";

        when(userRepository.findOneWithAuthoritiesByUsername(givenUsername.toLowerCase(Locale.ENGLISH)))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername(givenUsername));
    }

    @Test
    void createSpringSecurityUser() {
        var givenUsername = "SoMeUserName";
        var givenPassword = "SuperSecretPassword";

        var givenAuthorities = List.of(
                UserAuthority.builder().name(UserAuthority.AuthorityName.ADMIN).build()
        );

        var givenUser = User.builder()
                .username(givenUsername)
                .password(givenPassword)
                .activated(true)
                .authorities(givenAuthorities)
                .build();

        var expectedAuthorities = List.of(
                new SimpleGrantedAuthority(UserAuthority.AuthorityName.ADMIN.name()));

        var expectedUser = new org.springframework.security.core.userdetails.User(
                givenUsername,
                givenPassword,
                expectedAuthorities
        );

        var givenUsernameLowerCase = givenUsername.toLowerCase(Locale.ENGLISH);

        assertEquals(expectedUser,
                ReflectionTestUtils.invokeMethod(service, CREATE_SPRING_SECURITY_USER_METHOD_NAME, givenUsernameLowerCase, givenUser));
    }

    @Test
    void createSpringSecurityUserUserNotActivatedException() {
        var givenUsername = "SoMeUserName";

        var givenUser = User.builder()
                .username(givenUsername)
                .activated(false)
                .build();

        var givenUsernameLowerCase = givenUsername.toLowerCase(Locale.ENGLISH);

        assertThrows(UserNotActivatedException.class,
                () -> ReflectionTestUtils.invokeMethod(service, CREATE_SPRING_SECURITY_USER_METHOD_NAME, givenUsernameLowerCase, givenUser));
    }
}