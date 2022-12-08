package ru.agr.backend.looksliketests.db.entity.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "test", schema = "main")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "min_correct_answers")
    private Integer minCorrectAnswers;

    @Column(name = "need_verification")
    private Boolean needVerification;

    @Column(name = "attempts")
    private Integer attempts;

    @OneToMany(mappedBy = "test", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Question> questions;
}
