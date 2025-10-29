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

ALTER TABLE tbl_diary_country_path
    DROP CONSTRAINT fk_diary_country_path_country;


ALTER TABLE tbl_diary_country_path
    DROP COLUMN country_id;

ALTER TABLE tbl_diary_country_path RENAME TO tbl_diary_path;

