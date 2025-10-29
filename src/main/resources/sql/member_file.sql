create table tbl_member_file
(
    file_id   bigint primary key,
    member_id bigint not null,
    constraint fk_member_file_member foreign key (member_id)
        references tbl_member (id),
    constraint fk_member_file_file foreign key (file_id)
        references tbl_file (id)
);
