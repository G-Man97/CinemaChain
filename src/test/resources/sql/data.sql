INSERT INTO cinemas (id, name)
VALUES (1, 'Mir'),
       (2, 'Galaxy');
SELECT SETVAL('cinemas_id_seq', (SELECT MAX(id) FROM cinemas));

INSERT INTO cinema_halls (id, number, size, cinema_id)
VALUES (1, 2, 'MIDDLE', 1),
       (2, 1, 'BIG',    2),
       (3, 3, 'MIDDLE', 2),
       (4, 1, 'SMALL',  1);
SELECT SETVAL('cinema_halls_id_seq', (SELECT MAX(id) FROM cinema_halls));

-- Фильмы "Оно" и "Мгла" уже в прокате, остальные два - ещё нет
INSERT INTO movies (id, title, premiere, rent_end, duration, pegi, description, countries, producer, genre, composer, poster, is_it_in_rent, actors)
VALUES (1, 'Ono',        CURRENT_DATE - 1, CURRENT_DATE + 8,  135, 'EIGHTEEN_PLUS', 'test', 'test', 'test', 'test', 'test', 'test', true,  'test'),
       (2, 'Mgla',       CURRENT_DATE - 1, CURRENT_DATE + 8,  126, 'EIGHTEEN_PLUS', 'test', 'test', 'test', 'test', 'test', 'test', true,  'test'),
       (3, 'Madagascar', CURRENT_DATE + 2, CURRENT_DATE + 10, 86,  'SEVEN_PLUS',    'test', 'test', 'test', 'test', 'test', 'test', false, 'test'),
       (4, 'Legenda',    CURRENT_DATE + 4, CURRENT_DATE + 14, 131, 'EIGHTEEN_PLUS', 'test', 'test', 'test', 'test', 'test', 'test', false, 'test');
SELECT SETVAL('movies_id_seq', (SELECT MAX(id) FROM movies));

-- Для фильма "Оно": два сеанса в кинотеатре "Мир" в зале 2 MIDDLE,
--                   третий сеанс в "ТРЦ Галактика" в зале 1 BIG
--
-- Для фильма "Мгла": сеанс в кинотеатре "Мир" в зале 1 SMALL
INSERT INTO film_sessions (id, begin_session, movie_id, cinema_hall_id, date)
VALUES (1, '10:00', 1, 1, CURRENT_DATE + 1),
       (2, '12:50', 1, 1, CURRENT_DATE + 1),
       (3, '09:30', 1, 2, CURRENT_DATE + 1),
       (4, '20:40', 2, 4, CURRENT_DATE + 1);
SELECT SETVAL('film_sessions_id_seq', (SELECT MAX(id) FROM film_sessions));

-- Сохраняем места в кинозалах для для 4-х сеансов выше
INSERT INTO seats_for_film_sessions (id, row, seat_no, status, film_session_id, ticket_cost)
    (SELECT id, row, seat_no, status, 1, 500 FROM hall_seats WHERE middle_hall_seat_exists = true);
SELECT SETVAL('seats_for_film_sessions_id_seq', (SELECT MAX(id) FROM seats_for_film_sessions));

INSERT INTO seats_for_film_sessions (row, seat_no, status, film_session_id, ticket_cost)
    (SELECT row, seat_no, status, 2, 450 FROM hall_seats WHERE middle_hall_seat_exists = true);
INSERT INTO seats_for_film_sessions (row, seat_no, status, film_session_id, ticket_cost)
    (SELECT row, seat_no, status, 3, 530 FROM hall_seats WHERE big_hall_seat_exists = true);
INSERT INTO seats_for_film_sessions (row, seat_no, status, film_session_id, ticket_cost)
    (SELECT row, seat_no, status, 4, 600 FROM hall_seats WHERE small_hall_seat_exists = true);