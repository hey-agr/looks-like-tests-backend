DROP VIEW IF EXISTS main.student_assigned_tests;
CREATE VIEW main.student_assigned_tests AS
WITH
    assigned_tests AS (
        SELECT
            assgn.id,
            test.id as test_id,
            test.name,
            test.description,
            test.min_correct_answers,
            0 as questions_count,
            test.attempts,
            test.duration,
            test.need_verification,
            assgn.student_id
        FROM
            main.student_to_test_assignation assgn
                INNER JOIN main.test
                           ON test.id = assgn.test_id
    ),
     test_questions AS (
         SELECT
             COUNT(q.id) as questions_count,
             q.test_id
         FROM
             main.question as q
         GROUP BY
             q.test_id
     ),
     test_progresses AS (
         SELECT
             tp.test_id,
             tp.user_id,
             COUNT(*) as attempts_count,
             SUM(CASE WHEN tr.test_result_status = 'IN_PROGRESS' THEN 1 ELSE 0 END) AS in_progress_count,
             SUM(CASE WHEN tr.test_result_status = 'PASSED' THEN 1 ELSE 0 END) AS passed_count,
             SUM(CASE WHEN tr.test_result_status = 'PENDING' THEN 1 ELSE 0 END) AS pending_count,
             SUM(CASE WHEN tr.test_result_status = 'FAILED' THEN 1 ELSE 0 END) AS failed_count
         FROM
             main.test_progress tp
                 INNER JOIN main.test_result tr
                            ON tp.id = tr.test_progress_id
         GROUP BY
             tp.test_id,
             tp.user_id
     )

SELECT
    assgntest.id,
    assgntest.test_id,
    assgntest.student_id,
    assgntest.name,
    assgntest.description,
    assgntest.min_correct_answers,
    COALESCE(tq.questions_count, 0) as questions_count,
    assgntest.attempts,
    assgntest.duration,
    assgntest.need_verification,
    COALESCE(tp.attempts_count, 0) as attempts_count,
    COALESCE(tp.in_progress_count, 0) as in_progress_count,
    COALESCE(tp.passed_count, 0) as passed_count,
    COALESCE(tp.pending_count, 0) as pending_count,
    COALESCE(tp.failed_count, 0) as failed_count
FROM
    assigned_tests assgntest
        LEFT JOIN test_questions tq
                  ON tq.test_id = assgntest.test_id
        LEFT JOIN test_progresses tp
                  ON assgntest.test_id = tp.test_id
                      AND assgntest.student_id = tp.user_id