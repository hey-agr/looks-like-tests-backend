INSERT INTO auth."user"(id, username, password, first_name, last_name, middle_name, email, phone, activated)
VALUES (1, 'teacher', '$2a$10$B3rjKBXQaKaUnXPwp8plKOYfD6aWZ9eOz6t7o1tw2AD86UcI.IAbi', 'Ivan','Ivanov','Ivanovich','teacher@mail.ru','+777777777', true);

INSERT INTO auth.user_authority(user_id, name)
VALUES (1, 'TEACHER');

INSERT INTO auth."user"(id, username, password, first_name, last_name, middle_name, email, phone, activated)
VALUES (2, 'student', '$2a$10$yiXuhFyM4iTb4d5rRzpO6OwzeX6lgJjfV0lwjpSfreidkPbgPTrxS', 'Petr','Petrov','Petrovich','student@mail.ru','+888888888',true);

INSERT INTO auth.user_authority(user_id, name)
VALUES (2, 'STUDENT');