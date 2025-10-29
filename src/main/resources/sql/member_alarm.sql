create table tbl_member_alarm
(
    id               bigint generated always as identity primary key,
    alarm_status     read_status default 'unread',
    member_id        bigint not null,
    created_datetime timestamp   default now(),
    updated_datetime timestamp   default now(),
    constraint fk_member_alarm_member foreign key (member_id)
        references tbl_member (id)
);