create table tbl_diary_diary_path
(
    id bigint generated always as identity primary key,
    diary_path_id bigint ,
    post_id bigint,
    constraint fk_diary_diary_path_diary_path foreign key (diary_path_id)
        references tbl_diary_path (id),
    constraint fk_diary_diary_path_diary foreign key (post_id)
        references tbl_diary (post_id)
);