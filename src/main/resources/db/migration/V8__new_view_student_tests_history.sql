DROP VIEW IF EXISTS main.student_tests_history;
CREATE VIEW main.student_tests_history AS
SELECT
	test.id as test_id,
	test.name,
	test.description,
	tp.id as test_progress_id,
	tp.user_id,
	tp.date_started,
	tp.date_finished,
	COALESCE(tr.right_answers_count, 0) as question_count,
	COALESCE(tr.right_answers_count, 0) as right_answers_count,
	COALESCE(tr.wrong_answers_count, 0) as wrong_answers_count,
	COALESCE(tr.pending_answers_count, 0) as pending_answers_count,
	tr.test_result_status
FROM
	main.student_to_test_assignation assgn
		INNER JOIN main.test
			ON test.id = assgn.test_id
		INNER JOIN main.test_progress tp
			ON assgn.test_id = tp.test_id
			AND assgn.student_id = tp.user_id
		LEFT JOIN main.test_result tr
			ON tp.id = tr.test_progress_id;