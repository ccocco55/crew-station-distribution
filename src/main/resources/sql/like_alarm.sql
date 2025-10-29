create table tbl_like_alarm
(
    id               bigint generated always as identity primary key,
    alarm_status     read_status default 'unread',
    like_id          bigint not null,
    created_datetime timestamp   default now(),
    updated_datetime timestamp   default now(),
    constraint fk_like_alarm_like foreign key (like_id)
        references tbl_like (id)
);