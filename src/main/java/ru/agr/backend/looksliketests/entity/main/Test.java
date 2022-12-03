package ru.agr.backend.looksliketests.entity.main;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "test", schema = "main")
public class Test {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "duration")
    private String duration;

    @Column(name = "min_correct_answers")
    private Integer minCorrectAnswers;

    @Column(name = "need_verification")
    private Boolean needVerification;

    @Column(name = "attempts")
    private Integer attempts;
}
