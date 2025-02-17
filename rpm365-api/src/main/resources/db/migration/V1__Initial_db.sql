create table doctor
(
    id         bigint       not null auto_increment,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    timezone   varchar(255) not null,
    primary key (id)
) engine=InnoDB;

create table patient
(
    id         bigint       not null auto_increment,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    primary key (id)
) engine=InnoDB;

create table visit
(
    id              bigint not null auto_increment,
    doctor_id       bigint not null,
    patient_id      bigint not null,
    start_date_time datetime(6) not null,
    end_date_time   datetime(6) not null,
    primary key (id)
) engine=InnoDB;

alter table visit
    add constraint visit_doctor_fk
        foreign key (doctor_id) references doctor (id);

alter table visit
    add constraint visit_patient_fk
        foreign key (patient_id) references patient (id);