package ru.agr.backend.looksliketests.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.agr.backend.looksliketests.controller.ApiVersion;
import ru.agr.backend.looksliketests.controller.auth.dto.UsersFilter;
import ru.agr.backend.looksliketests.controller.auth.mapper.UserFilterMapper;
import ru.agr.backend.looksliketests.controller.auth.mapper.UserMapper;
import ru.agr.backend.looksliketests.controller.resources.UsersResource;
import ru.agr.backend.looksliketests.service.auth.UserService;

import static java.util.Objects.nonNull;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiVersion.API_V1+"/users")
@Validated
public class UsersController {
    private final UserFilterMapper userFilterMapper;
    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UsersResource> getFiltered(UsersFilter usersFilter, Pageable pageable) {
        var specificationFilter = nonNull(usersFilter)
                ? userFilterMapper.toSpecificationFilter(usersFilter)
                : null;
        var usersPage = userService.findAllFiltered(specificationFilter, pageable);
        return ResponseEntity.ok(UsersResource.builder()
                .users(usersPage.getContent().stream().map(userMapper::toUserResource).toList())
                .build());
    }
}
