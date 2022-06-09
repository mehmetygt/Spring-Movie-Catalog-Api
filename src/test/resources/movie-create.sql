INSERT INTO movie(id, created_date, status, name, genre, release_year, director)
VALUES (1001, now(), 'ACTIVE', 'test film 1001', 'COMEDY', 2001, 'test yönetmen 1001'),
       (1002, now(), 'ACTIVE', 'test film 1002', 'COMEDY', 2002, 'test yönetmen 1002');

INSERT INTO actor(id, created_date, name, birth_date)
VALUES (2001, now(), 'test actor 2001', '2001-01-12 11:00:00'),
       (2002, now(), 'test actor 2002', '2002-01-12 12:00:00'),
       (2003, now(), 'test actor 2003', '2003-01-12 13:00:00');

INSERT INTO matching(id, created_date, movie_id, actor_id)
VALUES (3001, now(), 1001, 2001),
       (3002, now(), 1002, 2002),
       (3003, now(), 1001, 2003),
       (3004, now(), 1002, 2001);