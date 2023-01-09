package ru.agr.backend.looksliketests.controller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.agr.backend.looksliketests.controller.ApiVersion;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(ApiVersion.API_V1 + "/tests/results")
public class TestsResultController {

}
