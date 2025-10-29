create table tbl_diary_country_path
(
    id                 bigint generated always as identity primary key,
    country_start_date varchar(255) not null,
    country_end_date   varchar(255) not null,
    member_id          bigint       not null,
    country_id         bigint       not null,
    constraint fk_diary_country_path_member foreign key (member_id)
        references tbl_member (id),
    constraint fk_diary_country_path_country foreign key (country_id)
        references tbl_country (id)
);