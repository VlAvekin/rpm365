delete from visit;
delete from doctor;
delete from patient;

insert into doctor (first_name, last_name, timezone)
values ('Gregory', 'House', 'Europe/Kyiv');

insert into patient (first_name, last_name)
values ('Tom', 'Cruise'),
       ('Jackie', 'Chan');

insert into visit (patient_id, doctor_id, start_date_time, end_date_time)
values (1, 1, '2025-02-16 12:00:00.000000+02:00', '2025-02-16 12:30:00.000000+02:00');