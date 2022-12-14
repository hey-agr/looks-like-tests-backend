CREATE SCHEMA IF NOT EXISTS main;

CREATE TABLE IF NOT EXISTS main.test
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 ),
    name text,
    description text,
    duration bigint,
    min_correct_answers bigint,
    need_verification boolean,
    attempts bigint,
    CONSTRAINT test_id_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS main.question
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 ),
    name text  NOT NULL,
    type text,
    test_id bigint NOT NULL,
    CONSTRAINT question_id_pkey PRIMARY KEY (id),
    CONSTRAINT question_test_id_test_fk FOREIGN KEY (test_id)
        REFERENCES main.test (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS main.option
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 ),
    question_id bigint NOT NULL,
    name text NOT NULL,
    right_answer boolean default false,
    CONSTRAINT option_id_pkey PRIMARY KEY (id),
    CONSTRAINT option_question_id_question_fk FOREIGN KEY (question_id)
        REFERENCES main.question (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS main.question_image
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 ),
    url text NOT NULL,
    question_id bigint NOT NULL,
    CONSTRAINT question_image_id_pkey PRIMARY KEY (id),
    CONSTRAINT question_image_question_id_question_fk FOREIGN KEY (question_id)
        REFERENCES main.question (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);