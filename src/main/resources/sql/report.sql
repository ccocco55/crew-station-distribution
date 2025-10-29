create table tbl_report
(
    id                    bigint generated always as identity primary key,
    report_content        varchar(255) not null,
    report_process_status process_status default 'pending',
    member_id             bigint       not null,
    created_datetime      timestamp      default now(),
    updated_datetime      timestamp      default now(),
    constraint fk_report_member foreign key (member_id)
        references tbl_member (id)
);

