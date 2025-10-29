create table tbl_banner_file
(
    file_id   bigint primary key,
    banner_id bigint not null,
    constraint fk_banner_file_banner foreign key (banner_id)
        references tbl_banner (id),
    constraint fk_banner_file_file foreign key (file_id)
        references tbl_file (id)
);

insert into tbl_banner_file (file_id, banner_id)
values (5,5);