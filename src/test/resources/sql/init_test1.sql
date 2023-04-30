INSERT INTO main.test(
    id, name, description, duration, min_correct_answers, need_verification, attempts)
VALUES (1, 'Option test 1', 'Test with options 1', 3600, 1, false, 3);

INSERT INTO main.question(
    id, name, type, test_id)
VALUES (1, 'How are you?', 'OPTIONS', 1);

INSERT INTO main.question(
    id, name, type, test_id)
VALUES (2, 'Where are you?', 'OPTIONS', 1);

INSERT INTO main.option(
    id, question_id, name, right_answer)
VALUES (1, 1, 'I am fine', true);

INSERT INTO main.option(
    id, question_id, name, right_answer)
VALUES (2, 1, 'Not really well', false);

INSERT INTO main.option(
    id, question_id, name, right_answer)
VALUES (3, 2, 'I am here', true);

INSERT INTO main.option(
    id, question_id, name, right_answer)
VALUES (4, 2, 'Somewhere', false);

----------------------------------------------------------------------------------------------

INSERT INTO main.test(
    id, name, description, duration, min_correct_answers, need_verification, attempts)
VALUES (2, 'Option multiply test 1', 'Test with multiply options 1', 3600, 1, false, 4);

INSERT INTO main.question(
    id, name, type, test_id)
VALUES (3, 'Select the right answers: What is the weather today?', 'OPTIONS_MULTIPLY', 2);

INSERT INTO main.option(
    id, question_id, name, right_answer)
VALUES (5, 3, 'Great!', true);

INSERT INTO main.option(
    id, question_id, name, right_answer)
VALUES (6, 3, 'Sunny', true);

INSERT INTO main.option(
    id, question_id, name, right_answer)
VALUES (7, 3, 'Really bad', false);

---------------------------------------------------------------------------------------------------

INSERT INTO main.test(
    id, name, description, duration, min_correct_answers, need_verification, attempts)
VALUES (3, 'Writing test 1', 'Writing test 1', 3600, 1, true, 5);

INSERT INTO main.question(
    id, name, type, test_id)
VALUES (4, 'Could you tell me about your hobbies?', 'WRITING', 3);