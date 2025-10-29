create table tbl_diary
(
    post_id               bigint primary key,
    diary_secret          secret default 'public',
    diary_like_count      int    default 0,
    diary_reply_count     int    default 0,
    diary_country_path_id bigint not null,
    constraint fk_diary_diary_country_path foreign key (diary_country_path_id)
        references tbl_diary_country_path (id),
    constraint fk_diary_post foreign key (post_id)
        references tbl_post (id)
);

ALTER TABLE tbl_diary
    DROP CONSTRAINT fk_diary_diary_country_path;


ALTER TABLE tbl_diary
    DROP COLUMN diary_country_path_id;

ALTER TABLE tbl_diary
    ALTER COLUMN post_id DROP IDENTITY;