insert into doctor (first_name, last_name, timezone)
values ('Gregory', 'House', 'Europe/Kyiv'),
       ('Lisa', 'Cuddy', 'Europe/Kyiv'),
       ('Eric', 'Foreman', 'America/New_York'),
       ('Remy', 'Hadley', 'America/New_York');

insert into patient (first_name, last_name)
values ('Tom', 'Cruise'),
       ('Jackie', 'Chan'),
       ('Thomas', 'Hardy'),
       ('Tom', 'Holland'),
       ('Tom', 'Hanks'),
       ('Bobby Millie', 'Brown'),
       ('Sadie', 'Sink'),
       ('Christian', 'Bale'),
       ('Anne Jacqueline', 'Hathaway'),
       ('Marion', 'Cotillard'),
       ('Gary', 'Oldman'),
       ('Morgan', 'Freeman'),
       ('Cillian', 'Murphy'),
       ('Liam', 'Neeson'),
       ('Leonardo Wilhelm', 'DiCaprio'),
       ('William James', 'Dafoe'),
       ('Jake', 'Gyllenhaal'),
       ('Jamie', 'Foxx'),
       ('Heath Andrew', 'Ledger'),
       ('Ryan Thomas', 'Gosling'),
       ('Ruth', 'Negga');

insert into visit (patient_id, doctor_id, start_date_time, end_date_time)
values (1, 1, '2025-02-16 10:00:00.000000', '2025-02-16 10:30:00.000000'),
       (1, 2, '2025-02-16 11:00:00.000000', '2025-02-16 11:40:00.000000'),
       (1, 3, '2025-02-16 12:00:00.000000', '2025-02-16 12:50:00.000000'),
       (1, 4, '2025-02-16 13:00:00.000000', '2025-02-16 13:25:00.000000'),
       (2, 2, '2025-02-16 10:00:00.000000', '2025-02-16 10:30:00.000000'),
       (3, 3, '2025-02-16 10:00:00.000000', '2025-02-16 10:30:00.000000'),
       (4, 2, '2025-02-16 10:00:00.000000', '2025-02-16 10:30:00.000000');