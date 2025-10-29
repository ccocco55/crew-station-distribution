create table tbl_accompany_path
(
    id                 bigint generated always as identity primary key,
    country_start_date varchar(255) not null,
    country_end_date   varchar(255) not null,
    accompany_id       bigint       not null,
    country_id         bigint       not null,
    constraint fk_accompany_path_accompany foreign key (accompany_id)
        references tbl_accompany (post_id),
    constraint fk_accompany_path_country foreign key (country_id)
        references tbl_country (id)
);