insert into users (id, birthdate, email, first_name, last_name, password, phone, role) values
('11111111-1111-1111-1111-111111111111', date '2000-01-15', 'client@cinema.test', 'Client', 'One', 'x', '0600000001', 'CLIENT'),
('22222222-2222-2222-2222-222222222222', date '1998-05-20', 'client2@cinema.test', 'Client', 'Two', 'x', '0600000002', 'CLIENT'),
('33333333-3333-3333-3333-333333333333', date '1990-03-11', 'employee@cinema.test', 'Employee', 'One', 'x', '0600000003', 'EMPLOYEE'),
('44444444-4444-4444-4444-444444444444', date '1985-07-30', 'manager@cinema.test', 'Manager', 'One', 'x', '0600000004', 'MANAGER');

insert into rooms (id, number, capacity) values
('10000000-0000-0000-0000-000000000001', 'R1', 50);

insert into seats (id, number, room_id) values
('50000000-0000-0000-0000-000000000001', 'A1', '10000000-0000-0000-0000-000000000001'),
('50000000-0000-0000-0000-000000000002', 'A2', '10000000-0000-0000-0000-000000000001');

insert into movies (id, description, duration, title) values
('20000000-0000-0000-0000-000000000001', 'Sci-fi epic', 8880000000000, 'Dune');

insert into movie_genres (movie_id, genre) values
('20000000-0000-0000-0000-000000000001', 'SCI_FI'),
('20000000-0000-0000-0000-000000000001', 'ACTION');

insert into projections (id, datetime, seat_price, movie_id, room_id) values
('30000000-0000-0000-0000-000000000001', timestamp with time zone '2026-08-10 20:00:00+00', 12.50, '20000000-0000-0000-0000-000000000001', '10000000-0000-0000-0000-000000000001');

insert into reservations (id, created_at, projection_id, user_id) values
('40000000-0000-0000-0000-000000000001', timestamp with time zone '2026-08-01 10:00:00+00', '30000000-0000-0000-0000-000000000001', '11111111-1111-1111-1111-111111111111');

insert into reservation_seat (reservation_id, seat_id) values
('40000000-0000-0000-0000-000000000001', '50000000-0000-0000-0000-000000000001'),
('40000000-0000-0000-0000-000000000001', '50000000-0000-0000-0000-000000000002');
