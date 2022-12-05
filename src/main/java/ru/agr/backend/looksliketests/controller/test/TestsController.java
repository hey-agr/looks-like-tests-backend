package ru.agr.backend.looksliketests.controller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.agr.backend.looksliketests.controller.ApiVersion;
import ru.agr.backend.looksliketests.controller.exception.ResourceNotFoundException;
import ru.agr.backend.looksliketests.controller.test.dto.CreateTestDto;
import ru.agr.backend.looksliketests.controller.test.dto.TestResource;
import ru.agr.backend.looksliketests.controller.test.dto.TestsResource;
import ru.agr.backend.looksliketests.controller.test.mapper.TestMapper;
import ru.agr.backend.looksliketests.controller.test.service.TestResourceService;
import ru.agr.backend.looksliketests.service.TestService;

import javax.validation.Valid;


/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(ApiVersion.API_V1 + "/tests")
public class TestsController {
    private final TestService testService;
    private final TestMapper testMapper;
    private final TestResourceService testResourceService;

    @GetMapping("/{testId}")
    public ResponseEntity<TestResource> getById(@PathVariable Long testId) throws ResourceNotFoundException {
        final var test = testService.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found test with id="+testId));
        return ResponseEntity.ok(testResourceService.prepareTestResource(test));
    }

    @GetMapping
    public ResponseEntity<TestsResource> getAll(Pageable pageable) {
        var testsPage = testService.findPageable(pageable);
        return ResponseEntity.ok(testResourceService.prepareTestsResource(testsPage, pageable));
    }

    @PostMapping
    public ResponseEntity<TestResource> createTest(@RequestBody @Valid CreateTestDto createTestDto) {
        final var savedTest = testService.save(testMapper.toEntity(createTestDto));
        try {
            return getById(savedTest.getId());
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
