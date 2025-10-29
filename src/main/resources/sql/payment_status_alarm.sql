create table tbl_payment_status_alarm
(
    id                bigint generated always as identity primary key,
    alarm_status      read_status default 'unread',
    payment_status_id bigint not null,
    created_datetime  timestamp   default now(),
    updated_datetime  timestamp   default now(),
    constraint fk_payment_status_alarm_payment_status foreign key (payment_status_id)
        references tbl_payment_status (id)
);