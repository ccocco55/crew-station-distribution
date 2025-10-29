create table tbl_inquiry_file
(
    file_id   bigint primary key,
    inquiry_id bigint not null,
    constraint fk_inquiry_file_inquiry foreign key (inquiry_id)
        references tbl_inquiry (id),
    constraint fk_inquiry_file_file foreign key (file_id)
        references tbl_file (id)
);
