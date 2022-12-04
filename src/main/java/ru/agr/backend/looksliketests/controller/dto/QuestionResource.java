package ru.agr.backend.looksliketests.controller.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Jacksonized
@Builder
@Value
public class QuestionResource {
    private final Long id;
    private final String name;
    private final String type;
    private final List<OptionResource> answers;
}

//        "questions": [
//        {
//        "id": 1,
//        "name": "Вам предстоит изучить и ответить на ряд вопрсосов и знании криетриев оценки и подсчета статистики",
//        "answers": [
//        {
//        "id": 1,
//        "name": "Отношение числа дней"
//        },
//        {
//        "id": 2,
//        "name": "Отношение числа ставок"
//        },
//        {
//        "id": 3,
//        "name": "И то, и другое"
//        },
//        {
//        "id": 4,
//        "name": "Ни то, ни другое"
//        }
//        ],
//        "type": "options",
//        "rightAnswerId": 1
//        },
//        {
//        "id": 2,
//        "name": "Вам нравится жить?",
//        "type": "writing"
//        }
//        ]
//        }