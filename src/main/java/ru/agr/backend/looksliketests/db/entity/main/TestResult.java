package ru.agr.backend.looksliketests.db.entity.main;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    @Column(name = "pending_answers_count")
    private Long pendingAnswersCount;

    @Column(name = "wrong_answers_count")
    private Long wrongAnswersCount;

    @Column(name = "expired")
    private Boolean expired;

    @Column(name = "test_result_status")
    @Enumerated(EnumType.STRING)
    private TestResultStatus testResultStatus;
}
