CREATE TABLE main.test_result
(
    test_progress_id bigint NOT NULL,
    question_count bigint NOT NULL,
    right_answers_count bigint NOT NULL,
    wrong_answers_count bigint NOT NULL,
    pending_answers_count bigint NOT NULL,
    expired boolean NOT NULL,
    test_result_status text NOT NULL,
    CONSTRAINT test_result_id_pkey PRIMARY KEY (test_progress_id),
    CONSTRAINT test_result_test_progress_id_test_progress_fk FOREIGN KEY (test_progress_id)
        REFERENCES main.test_progress (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);