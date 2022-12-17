package ru.agr.backend.looksliketests.controller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.agr.backend.looksliketests.controller.ApiVersion;
import ru.agr.backend.looksliketests.controller.exception.ResourceNotFoundException;
import ru.agr.backend.looksliketests.controller.resources.TestAnswerResource;
import ru.agr.backend.looksliketests.controller.test.dto.CreateTestAnswerDto;
import ru.agr.backend.looksliketests.controller.test.service.TestAnswerResourceService;
import ru.agr.backend.looksliketests.db.entity.main.TestAnswer;
import ru.agr.backend.looksliketests.service.TestAnswerService;
import ru.agr.backend.looksliketests.service.TestProgressService;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(ApiVersion.API_V1 + "/tests/answers")
public class TestsAnswerController {
    private final TestAnswerService testAnswerService;
    private final TestProgressService testProgressService;
    private final TestAnswerResourceService testAnswerResourceService;

    @PostMapping("/{testProgressId}")
    public ResponseEntity<TestAnswerResource> create(@PathVariable Long testProgressId,
                                                     @RequestBody CreateTestAnswerDto createTestAnswerDto) throws ResourceNotFoundException {
        final var testProgress = testProgressService.findById(testProgressId)
                .orElseThrow(() -> new ResourceNotFoundException("Test progress with id =" +testProgressId+" is not found!"));
        final var question = testProgress.getTest().getQuestions().stream()
                .filter(currentQuestion -> currentQuestion.getId().equals(createTestAnswerDto.getQuestionId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Question with id =" +createTestAnswerDto.getQuestionId()+" is not found!"));
        final var option = question.getOptions().stream()
                .filter(currentOption -> currentOption.getId().equals(createTestAnswerDto.getOptionId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Option with id =" +createTestAnswerDto.getOptionId()+" is not found!"));
        var answer = testAnswerService.save(TestAnswer.builder()
                .testProgress(testProgress)
                .question(question)
                .option(option)
                .textAnswer(createTestAnswerDto.getTextAnswer())
                .build());
        return ResponseEntity.ok(testAnswerResourceService.toResource(answer));
    }
}
