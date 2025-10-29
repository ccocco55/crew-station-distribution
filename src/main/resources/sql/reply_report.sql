create table tbl_reply_report
(
    report_id bigint primary key,
    reply_id  bigint not null,
    constraint fk_reply_report_reply foreign key (reply_id)
        references tbl_reply (id),
    constraint fk_reply_report_report foreign key (report_id)
        references tbl_report (id)
);