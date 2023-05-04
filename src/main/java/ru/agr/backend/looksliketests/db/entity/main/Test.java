package ru.agr.backend.looksliketests.db.entity.main;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@FieldNameConstants
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
    private Long duration;

    @Column(name = "min_correct_answers")
    private Long minCorrectAnswers;

    @Column(name = "need_verification")
    private Boolean needVerification;

    @Column(name = "attempts")
    private Long attempts;

    @ToString.Exclude
    @OneToMany(mappedBy = "test", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Question> questions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Test test = (Test) o;
        return id != null && Objects.equals(id, test.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
