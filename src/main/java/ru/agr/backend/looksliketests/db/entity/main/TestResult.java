package ru.agr.backend.looksliketests.db.entity.main;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Arslan Rabadanov
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "test_result", schema = "main")
public class TestResult implements Serializable {
    @Id
    @Column(name = "test_progress_id")
    private Long testProgressId;

    @Column(name = "question_count")
    private Long questionCount;

    @Column(name = "right_answers_count")
    private Long rightAnswersCount;

    @Column(name = "expired")
    private Boolean expired;

    @Column(name = "test_result_status")
    private TestResultStatus testResultStatus;
}
