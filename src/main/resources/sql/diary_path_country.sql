create table tbl_diary_path_country
(
    id bigint generated always as identity primary key,
    diary_path_id bigint unique ,
    country_id bigint,
    constraint fk_diary_path_country_diary_path foreign key (diary_path_id)
        references tbl_diary_path (id),
    constraint fk_diary_path_country_country foreign key (country_id)
        references tbl_country (id)
);
