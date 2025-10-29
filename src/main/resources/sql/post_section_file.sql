create table tbl_post_section_file
(
    file_id         bigint primary key,
    post_section_id bigint not null,
    image_type      image_type default 'sub',
    constraint fk_post_section_file_post_section foreign key (post_section_id)
        references tbl_post_section (id),
    constraint fk_post_section_file_file foreign key (file_id)
        references tbl_file (id)
);

ALTER TABLE tbl_post_section_file
    ALTER COLUMN file_id DROP IDENTITY;