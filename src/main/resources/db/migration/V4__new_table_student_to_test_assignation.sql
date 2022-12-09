CREATE TABLE main.student_to_test_assignation
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 ),
    student_id bigint NOT NULL,
    test_id bigint NOT NULL,
    date_created timestamp with time zone NOT NULL DEFAULT NOW(),
    CONSTRAINT student_to_test_assignation_id_pkey PRIMARY KEY (id),
    CONSTRAINT student_to_test_assignation_student_id_fk FOREIGN KEY (student_id)
        REFERENCES auth."user" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT student_to_test_assignation_test_id_fk FOREIGN KEY (test_id)
        REFERENCES main.test (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

ALTER TABLE IF EXISTS main.student_to_teacher_assignation
    ADD UNIQUE (student_id, teacher_id);

ALTER TABLE IF EXISTS main.student_to_test_assignation
    ADD UNIQUE (student_id, test_id);
