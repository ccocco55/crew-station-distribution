create table tbl_post_report
(
    report_id bigint primary key,
    post_id   bigint not null,
    constraint fk_post_report_post foreign key (post_id)
        references tbl_post (id),
    constraint fk_post_report_report foreign key (report_id)
        references tbl_report (id)
);

update tbl_report
set report_process_status = 'pending'
where report_process_status = 'resolved';

update tbl_post
set post_status = 'active'
where post_status = 'inactive';
