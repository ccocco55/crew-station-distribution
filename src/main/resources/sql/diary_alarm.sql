create table tbl_diary_alarm
(
    id               bigint generated always as identity primary key,
    alarm_status     read_status default 'unread',
    diary_id         bigint not null,
    created_datetime timestamp   default now(),
    updated_datetime timestamp   default now(),
    constraint fk_diary_alarm_like foreign key (diary_id)
        references tbl_diary (post_id)
);